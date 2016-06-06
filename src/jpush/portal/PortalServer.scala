package jpush.portal

import jpush.portal.servlets.{FileExplorerServlet, HomeServlet}
import jpush.utils.Helper._
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.servlet.ServletHandler


/**
  * Created by dingb on 2016/6/5.
  */
class PortalServer(httpdPort: Int) {

  def classes = List(
    classOf[FileExplorerServlet]
  )

  def start = async {
    val server = new Server(httpdPort)
    val sh = new ServletHandler
    classes.foreach(cls => sh.addServletWithMapping(cls, "/" + cls.getName.split('.').last.dropRight("Servlet".length)))


    sh.addServletWithMapping(classOf[HomeServlet], "/")

    val hs = new HandlerList
    hs.addHandler(sh)
    server.setHandler(hs)
    server.start()
    server.join()



  }

}
