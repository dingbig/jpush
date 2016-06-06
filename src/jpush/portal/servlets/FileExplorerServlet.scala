package jpush.portal.servlets

import java.io.{File, FileInputStream}
import javax.print.DocFlavor.URL
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import org.eclipse.jetty.util.StringUtil

import jpush.utils.FileHtmlUtil._

/**
  * Created by dingb on 2016/6/6.
  */
class FileExplorerServlet extends HttpServlet {

  def genDirHtml(dir: File) = {
    <html>
      <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />

        <title></title></head>
      <body>
        {
        val subs = dir.listFiles()

        val list = subs.filter(_.isDirectory).map(_.toHtmlElement) ++  subs.filter(_.isFile).map(_.toHtmlElement)

        list
        }
      </body>
    </html>

  }

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val goto = req.getParameter("goto")

    if(StringUtil.isBlank(goto)) {
      resp.getWriter.println("goto missing")
    } else {
      val file = new File(goto)
      if(file.isDirectory) {
        resp.setStatus(HttpServletResponse.SC_OK)
        resp.setCharacterEncoding("utf-8")
        resp.getWriter.println(genDirHtml(file))
      } else {
        resp.setStatus(HttpServletResponse.SC_OK)
        resp.setContentLength(file.length.asInstanceOf[Int])
        resp.setContentType("ocet/stream")
        resp.addHeader("Content-Disposition", "attachment; filename=" + file.getName)
        val is = new FileInputStream(file)
        var done = false
        while(!done) {
          val buf = new Array[Byte](1024)
          val r = is.read(buf)
          if(r <=0) {
            done = true
          } else {
            resp.getOutputStream.write(buf, 0, r)
          }
        }
        is.close()


      }
    }
  }


}
