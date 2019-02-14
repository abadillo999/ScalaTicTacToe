package ticTacToe.views

import ticTacToe.models.Game

object TurnView {

  def write(game: Game) = {
    val color = ColorView.getChar(game.take)
    GestorIO.write(s"Is the turn of $color\n")
  }

}
