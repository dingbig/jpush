package jpush.portal.servlets

import java.io.File
import java.net.{Inet6Address, InetAddress, NetworkInterface}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import jpush.Server
import jpush.utils.FileHtmlUtil._

import scala.collection.JavaConversions._

/**
  * Created by dingb on 2016/6/6.
  */
class HomeServlet extends HttpServlet{
  def loadPage = {
    val mePath = getClass.getProtectionDomain.getCodeSource.getLocation.getFile
    <html>
      <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" />
        <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
        <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

        <title></title></head>
      <body>
        <div><a href={"/FileExplorer?goto=" + mePath}>Download Me!</a></div>
        <div><span>FTP Roots</span>
        {Server.ftproots.map(_.toHtmlElement)}
        </div>



        <div><span>java -cp jpush.jar jpush.Server</span></div>
        {ips}

        {
        File.listRoots().map(_.toHtmlElement)
        }
        <div><span>Global text</span></div>
        {
        <form>
        <textarea  style="margin: 0px; height: 255px; width: 717px;"></textarea>
        </form>
        }
        <script>

        </script>
      </body>
    </html>
  }
  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {

  }

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.setContentType("text/html")
    resp.setCharacterEncoding("utf-8")


    resp.getWriter.println(loadPage)

  }

  def ips = {
    NetworkInterface.getNetworkInterfaces.toList.filterNot(_.isLoopback) .flatMap {
      ni =>
        ni.getInetAddresses.toList.filterNot(_.isInstanceOf[Inet6Address]).map(i => i.getHostAddress)
    }
  }.map {
    addr => <div><span>{"java -cp jpush.jar Client %s [files or dirs]".format(addr)}</span></div>
  }


}
