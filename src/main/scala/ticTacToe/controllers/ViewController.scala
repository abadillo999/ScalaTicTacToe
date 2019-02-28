package ticTacToe.controllers
import ticTacToe.views._
import akka.actor._
import ticTacToe.models.Game


class ViewController(mainPlayer: ActorRef) extends Actor {
  val mainPlayer_ = mainPlayer
  val subSystem = ActorSystem("subSystem")

  override def preStart= {

    mainPlayer_ ! StartMessage
  }

  def receive = {
    case SingleMode =>
      val player2_ = subSystem.actorOf(Props[AIView], name = "player2")
      this.newGame(mainPlayer_, player2_)
    case DualMode =>
      val player2_ = subSystem.actorOf(Props[HumanView], name = "player2")
      this.newGame(mainPlayer_, player2_)
    case DemoMode =>
      val player1_ = subSystem.actorOf(Props[AIView], name = "player2")
      val player2_ = subSystem.actorOf(Props[AIView], name = "player3")
      this.newGame(player1_, player2_)
    case StopMessage =>
      println("TicTacToe stopped")
      context.stop(self)
  }

  def newGame (player1: ActorRef, player2: ActorRef)= {
    val subSystem = ActorSystem("subSystem")
    val controller = subSystem.actorOf(Props(classOf[LogicController], List(player1, player2), new Game()))
    //subSystem.actorOf(Props(classOf[Terminator], subSystem), "logicTerminator")
  }

  class Terminator(ref: ActorRef) extends Actor {
    context watch ref
    def receive = {
      case Terminated(_) =>
        context.system.terminate()
    }
  }

}
