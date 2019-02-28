package ticTacToe

import ticTacToe.models.Game
import ticTacToe.controllers.ViewController
import ticTacToe.views.{GestorIO, HumanView}
import akka.actor.{Actor, ActorRef, ActorSystem, Props, Terminated}


object Main {


  def main(args: Array[String]): Unit = {
    val system = ActorSystem("system")
    val player = system.actorOf(Props[HumanView], name = "player1")
    val controller = system.actorOf(Props(new ViewController(player)))
    system.actorOf(Props(classOf[Terminator], controller), "terminator")

  }


  class Terminator(ref: ActorRef) extends Actor {
    context watch ref
    def receive = {
      case Terminated(_) =>
        context.system.terminate()
    }
  }
}
