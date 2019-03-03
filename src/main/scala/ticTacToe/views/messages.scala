package ticTacToe.views
import ticTacToe.models.{Board, Coordinate}

//Players to controller
case class PutCoordinate(coordinate: Coordinate)
case class MoveCoordinate(origin: Coordinate, destination: Coordinate)

case object StopMessage

//Controller to players
case object StartMessage
case object RetryMessage

case class ShowBoard(board: Board)

case class TurnToPut(list: List[Coordinate])
case class TurnToMove(origins: List[Coordinate], destinations: List[Coordinate])

case object LooseMessage
case object WinMessage

// Game modes
sealed trait GameMode
case object SingleMode extends GameMode
case object DualMode extends GameMode
case object DemoMode extends GameMode
