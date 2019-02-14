package ticTacToe

import ticTacToe.models.Game
import ticTacToe.views.{GameView, CoordinateView, GestorIO}

object Main {


  def main(args: Array[String]): Unit = {
    do {
      var game = new Game
      val coordinateView = GameView.writeMode()
      GameView.write(game)
      do {
        if (!game.isComplete) {
          game = game.put(coordinateView.read(game.getFree()))
        } else {

          game = game.move(coordinateView.read(game.getChecked()), coordinateView.read(game.getFree()))
        }
        GameView.write(game)
      } while (!game.isTicTacToe)
      GestorIO.write("... you loose\n")
    } while (GameView.tryAgain())
  }
}
