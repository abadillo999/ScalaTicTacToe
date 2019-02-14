package ticTacToe.views

import ticTacToe.models.Coordinate

abstract class CoordinateView {

  def read (list: List[Coordinate]): Coordinate

}
