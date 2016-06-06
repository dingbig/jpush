package jpush.utils

import java.io.File

/**
  * Created by dingb on 2016/6/6.
  */
object FileHtmlUtil {

  implicit def fileToHtmlElementConvertor(file: File) = new FileToHtmlElementConvertor(file)

  class FileToHtmlElementConvertor(f: File) {
    def toHtmlElement() = {

      if(f.isDirectory)  <div><a href={"/FileExplorer?goto=" + f.getAbsolutePath}><b>{f.getAbsolutePath}</b></a></div>
      else <div><a href={"/FileExplorer?goto=" + f.getAbsolutePath}>{f.getAbsolutePath + " " + f.length()}</a></div>
    }
  }
}
