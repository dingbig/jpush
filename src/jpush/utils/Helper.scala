package jpush.utils

/**
  * Created by dingb on 2016/6/5.
  */
object Helper {

  def async(body : => Unit) = {
    val t = new Thread(new Runnable {
      override def run(): Unit = {
        body
      }
    })
    t.start()
  }

}
