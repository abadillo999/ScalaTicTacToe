package ticTacToe.views

import akka.actor.Actor
import ticTacToe.models.{Board, Coordinate}

abstract class PlayerView extends Actor{

  override def receive = {
    case StartMessage =>
      sender ! getMode
    case RetryMessage =>
      sender ! tryAgain
    case ShowBoard(board:Board)  =>
      BoardView.write(board)
    case TurnToPut(list: List[Coordinate]) =>
      IOManager.write("Player turn: "+self.path.name+"\n")
      sender ! PutCoordinate(read(list))
    case TurnToMove(origins: List[Coordinate], destinations: List[Coordinate]) =>
      IOManager.write("Player: "+self.path.name+"\n")
      IOManager.write("Choose from to\n")
      sender ! MoveCoordinate(read(origins), read(destinations))
    case WinMessage =>
      notifyWin
    case LooseMessage =>
      notifyLoose
    case StopMessage =>
      stop
  }

  def getMode: GameMode

  def tryAgain: Any

  def read (list: List[Coordinate]): Coordinate

  def notifyWin ={
    IOManager.write(self.path.name+", you win!!\n")
  }
  def notifyLoose={
    IOManager.write(self.path.name+", you loose...\n")
  }
  def stop
}
