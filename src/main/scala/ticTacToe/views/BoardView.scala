package ticTacToe.views

import ticTacToe.models.{Coordinate, Game, Board}

object BoardView {

  def write(board: Board) = {
    var result = ""
    for (i <- List(0, 1, 2)) {
      for (j <- List(0, 1, 2)) {
        val color = board.getColor(new Coordinate(i, j))
        result += ColorView.getChar(color)
      }
      result += "\n"
    }
    println(result)
  }
}
