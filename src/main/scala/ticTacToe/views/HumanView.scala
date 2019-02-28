package ticTacToe.views

import ticTacToe.models.Coordinate
import ticTacToe.models.Board

class HumanView extends PlayerView {

  def receive = {
    case StartMessage =>
      sender ! getMode
    case RetryMessage =>
      sender ! tryAgain
    case ShowBoard(board:Board)  =>
      BoardView.write(board)
    case TurnToPut(list: List[Coordinate]) =>
      sender ! PutCoordinate(read(list))
    case TurnToMove(origins: List[Coordinate], destinations: List[Coordinate]) =>
      sender ! MoveCoordinate(read(origins), read(destinations))
    case StopMessage =>
      println("Hooman view stopped")
      context.stop(self)
  }

  def read (list: List[Coordinate]) :Coordinate = {
    val actorName = self.path.name
    GestorIO.write("Player: "+actorName+"\n")
    val row = GestorIO.readInt("Row? [1-3]")
    val column = GestorIO.readInt("Column? [1-3]")
    new Coordinate(row-1, column-1)

  }

  def getMode:GameMode = {
    val mode = GestorIO.readString("Game mode? [single-dual-demo]")
    if (mode=="single"){
      SingleMode
    } else if(mode=="dual"){
      DualMode
    } else if(mode=="demo"){
      DemoMode
    } else {
      this.getMode
    }
  }
  def tryAgain : Any = {
    val mode = GestorIO.readString("Again? [yes-no]")
    if (mode == "yes") {
      this.getMode
    } else if (mode == "no") {
      StopMessage
    } else {
      this.tryAgain
    }
  }
}
