package ticTacToe.controllers

import akka.actor._
import ticTacToe.models.{Coordinate, Game}
import ticTacToe.views._

class LogicController(playerList: List[ActorRef],game: Game) extends Actor{
  var game_ = game
  val playerList_ = playerList

  playerList_.foreach(_ ! ShowBoard(game.getBoard))
  playerList_(game_.getTurn) !  TurnToPut(game_.getFree)

  def receive: Receive = {
    case PutCoordinate(coordinate:Coordinate) =>
      game_ = game_.put(coordinate)
      this.applyLogic
    case MoveCoordinate(origin:Coordinate, destination:Coordinate) =>
      game_ = game_.move(origin, destination)
      this.applyLogic
  }

  def applyLogic={
    playerList_.foreach(_ ! ShowBoard(game_.getBoard))
    if (!game_.isTicTacToe){
      if (!game_.isComplete) {
        GestorIO.write(game_.getTurn)
        playerList_(game_.getTurn) ! TurnToPut(game_.getFree)
      } else {
        playerList_(game_.getTurn) ! TurnToMove(game_.getChecked, game_.getFree)
      }
    } else {
      //this.notifyResult(playerList_)
      context.stop(self)
    }
  }
  def notifyResult(list: List[ActorRef]):Any= {
    list.zipWithIndex match {
      case Nil => Nil
      case (player, position) :: _ if position == game_.getTurn =>
        player ! LooseMessage
        notifyResult(list.tail)
      case (player, _) :: _ =>
        player ! WinMessage
        notifyResult(list.tail)
    }
  }
}
