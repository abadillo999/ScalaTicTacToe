package ticTacToe.views

import ticTacToe.models.Coordinate

class AIView extends CoordinateView {
  def read (list: List[Coordinate]):Coordinate = {
    val random = scala.util.Random
    list(random.nextInt(list.size))
  }
}
