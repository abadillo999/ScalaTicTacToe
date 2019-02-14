package ticTacToe.views

import ticTacToe.models.Game

object GameView {

  def write(game:Game) = {
    BoardView.write(game)
    TurnView.write(game)
  }

  def writeMode():CoordinateView = {
    val mode = GestorIO.readString("Mode? [play-demo]")
    if (mode=="play"){
      new HumanView()
    } else if(mode=="demo"){
      new AIView()
    } else {
      this.writeMode()
    }
  }
  def tryAgain() : Boolean = {
    val mode = GestorIO.readString("Again? [yes-no]")
    if (mode == "yes") {
      true
    } else if (mode == "no") {
      false
    } else {
      this.tryAgain()
    }

  }
}
