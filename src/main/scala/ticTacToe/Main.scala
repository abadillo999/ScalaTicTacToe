package ticTacToe

import ticTacToe.controllers.ViewController
import ticTacToe.views.{IOManager, HumanView}
import akka.actor._
import akka._


object Main {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("system")
    val player = system.actorOf(Props[HumanView], name = "mainPlayer")
    val controller = system.actorOf(Props(new ViewController(player)))
    system.actorOf(Props(classOf[Terminator], controller), "viewTerminator")
  }

  class Terminator(ref: ActorRef) extends Actor {
    context watch ref
    def receive = {
      case Terminated(_) =>
        IOManager.write("Application stopped\n")
        context.system.terminate()
    }
  }
}
