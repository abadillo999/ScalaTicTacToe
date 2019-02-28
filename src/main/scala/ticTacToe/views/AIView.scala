package ticTacToe.views

import ticTacToe.models.{Board, Coordinate}

class AIView extends PlayerView {

  def receive = {
    case ShowBoard(board:Board)  =>
      BoardView.write(board)
    case TurnToPut(list: List[Coordinate]) =>
      sender ! PutCoordinate(read(list))
    case TurnToMove(origins: List[Coordinate], destinations: List[Coordinate]) =>
      sender ! MoveCoordinate(read(origins), read(destinations))
    case StopMessage =>
      println("AI view stopped")
      context.stop(self)
  }

  def read (list: List[Coordinate]):Coordinate = {
    val actorName = self.path.name
    GestorIO.write("Player: "+actorName+"\n")
    val random = scala.util.Random
    list(random.nextInt(list.size))
  }
}
