package ticTacToe.views

import ticTacToe.models.Coordinate

class HumanView extends CoordinateView {

  def read (list: List[Coordinate]) :Coordinate = {
    val row = GestorIO.readInt("Fila? [1-3]")
    val column = GestorIO.readInt("Columna? [1-3]")
    new Coordinate(row-1, column-1)
  }

}
