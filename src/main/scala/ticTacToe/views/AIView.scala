package ticTacToe.views

import ticTacToe.models.Coordinate

class AIView extends PlayerView {

  def getMode:GameMode = {
    DemoMode
  }

  def tryAgain : Any = {
    StopMessage
  }

  def read (list: List[Coordinate]):Coordinate = {
    val random = scala.util.Random
    list(random.nextInt(list.size))
  }

  def stop {
    IOManager.write("AI view stopped\n")
    context.stop(self)
  }
}
