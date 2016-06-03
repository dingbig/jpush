package jpush

import java.io.{DataOutputStream, File, FileInputStream}
import java.net.Socket

/**
  * Created by dingb on 2016/6/3.
  */
object Client  extends  App{

  def send_file(ip: String, file: File, top: String): Unit = {
    val fis = new FileInputStream(file)
    val s = new Socket(ip, Server.port)
    val dos = new DataOutputStream(s.getOutputStream)
    val remotename = top + File.separator + file.getName
    printf("sending %d bytes %s to %s\n", file.length(), file.getAbsolutePath, remotename)
    dos.writeUTF(remotename)
    dos.writeLong(file.length())
    val buf = new Array[Byte](1024)
    var done = false
    while(!done) {
      val r = fis.read(buf)
      if(r <= 0) done = true
      else dos.write(buf, 0, r)
    }
    dos.close()
    fis.close()
    s.close()

  }

  def send(ip: String, file: File, top: String): Unit = {
    if(file.isDirectory) file.listFiles().foreach(send(ip, _, "" + File.separator + file.getName))
    else send_file(ip, file, top)

  }

  override def main(args: Array[String]) {
    if(args.length < 1)  usage()
    else args.drop(1).map(new File(_)).foreach(send(args(0), _, ""))

  }

  def usage(): Unit = {
    println("usage: jpsh.Client <ip> [file]...")
    System.exit(-1)
  }
}
