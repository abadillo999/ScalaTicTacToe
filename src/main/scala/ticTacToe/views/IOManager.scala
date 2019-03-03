package ticTacToe.views

object IOManager {

  def readString(title:String):String = {
    print(s"$title: ")
    scala.io.StdIn.readLine()
  }

  def readInt(title:String):Int = {
    readString(title).toInt
  }

  def write(string:String):Unit =
    print(string)

  def write(value:Int):Unit =
    print(value)
}
