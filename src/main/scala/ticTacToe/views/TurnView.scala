package ticTacToe.views

import ticTacToe.models.Game

object TurnView {

  def write(game: Game) = {
    val color = ColorView.getChar(game.take)
    IOManager.write(s"Is the turn of $color\n")
  }

}
