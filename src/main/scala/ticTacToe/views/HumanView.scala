package ticTacToe.views

import ticTacToe.models.Coordinate

class HumanView extends CoordinateView {

  def read (list: List[Coordinate]) :Coordinate = {
    val row = GestorIO.readInt("Row? [1-3]")
    val column = GestorIO.readInt("Column? [1-3]")
    new Coordinate(row-1, column-1)
  }

}
