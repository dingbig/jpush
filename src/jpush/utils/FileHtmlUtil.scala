package jpush.utils

import java.io.File

/**
  * Created by dingb on 2016/6/6.
  */
object FileHtmlUtil {

  implicit def fileToHtmlElementConvertor(file: File) = new FileToHtmlElementConvertor(file)

  class FileToHtmlElementConvertor(f: File) {
    def toHtmlElement() = {

      if(f.isDirectory) {
        val canEnter = (try {f.listFiles(); true} catch {case _ => false}) && (f.list()!=null)
        if(canEnter)       <div><a href={"/FileExplorer?goto=" + f.getAbsolutePath}><b><span style="color: blue;">{f.getAbsolutePath}</span></b></a></div>
        else     <div><a href={"/FileExplorer?goto=" + f.getAbsolutePath}><b><span style="color: red;">{f.getAbsolutePath}</span></b></a></div>
      }
      else {
        if(f.canExecute) <div><a href={"/FileExplorer?goto=" + f.getAbsolutePath}><span style="color: green;">{f.getAbsolutePath + " " + f.length()}</span></a></div>
        else <div><a href={"/FileExplorer?goto=" + f.getAbsolutePath}>{f.getAbsolutePath + " " + f.length()}</a></div>
      }
    }
  }
}
