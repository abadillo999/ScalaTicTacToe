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
      val player2_ = subSystem.actorOf(Props[AIView], name = "player1")
      this.newGame(mainPlayer_, player2_)
    case DualMode =>
      val player2_ = subSystem.actorOf(Props[HumanView], name = "player1")
      this.newGame(mainPlayer_, player2_)
    case DemoMode =>
      val player1_ = subSystem.actorOf(Props[AIView], name = "player1")
      val player2_ = subSystem.actorOf(Props[AIView], name = "player2")
      this.newGame(player1_, player2_)
    case StopMessage =>
      IOManager.write("TicTacToe stopped\n")
      context.stop(self)
  }

  def newGame (player1: ActorRef, player2: ActorRef)= {
    val subSystem = ActorSystem("subSystem")
    val logicController = subSystem.actorOf(Props(new LogicController(List(player1, player2), new Game())))
    subSystem.actorOf(Props(new logicTerminator(logicController, this.self)), "logicTerminator")
  }

  class logicTerminator(logicController: ActorRef, viewController: ActorRef) extends Actor {
    context watch logicController
    def receive = {
      case Terminated(_) =>
        mainPlayer_ ! RetryMessage
      case message =>
        viewController ! message
        context.stop(self)
    }
  }

}
