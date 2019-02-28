package ticTacToe.views

import akka.actor.Actor
import ticTacToe.models.Coordinate

abstract class PlayerView extends Actor{

  override def receive: Receive

  def read (list: List[Coordinate]): Coordinate

}
