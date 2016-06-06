package jpush

import java.io.{DataInputStream, File, FileOutputStream}
import java.net.ServerSocket

import jpush.utils.Helper
import Helper._
import jpush.portal.PortalServer

/**
  * Created by dingb on 2016/6/3.
  */
object Server extends  App {
  def port = 8899
  var ftproots: List[File] = null;
  override def main(args: Array[String]) {
    val roots = args.toList match {
      case Nil => List(new File(System.getProperty("user.dir")))
      case _ =>  args.map(new File(_)).toList
    }
    ftproots = roots


    printf("dst root is %s\n", roots.mkString(","))

    val ps = new PortalServer(8898)
    ps.start


    val ss = new ServerSocket(port)
    def accept: Unit = {
      val s = ss.accept()
      printf("%s in \n", s.getRemoteSocketAddress);
      async {
        val dis = new DataInputStream(s.getInputStream)
        val fn = dis.readUTF
        val size = dis.readLong
        printf("loading %s %d\n", fn, size);
        if(size > 0) {
          roots.foreach(new File(_, fn).getParentFile.mkdirs())
          val oses = roots.map(new File(_, fn)).map(new FileOutputStream(_))
          val buf = new Array[Byte](1024)
          var done = false
          while(!done) {
            val r = dis.read(buf)
            if(r <= 0) done = true
            else oses.foreach(_.write(buf, 0, r))
          }
          oses.foreach(_.close)
        }
      }
      accept
    }
    accept
  }

}



