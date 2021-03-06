package ticTacToe.models

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class Board(board: List[List[Int]] = List(
  List(-1, -1, -1),
  List(-1, -1, -1),
  List(-1, -1, -1))) {

  private val board_ = board

  def getColor(coordinate: Coordinate): Int = {

    def getColor(row: List[Int], column: Int): Int =
      row.zipWithIndex match {
        case (color, position) :: tail if position == column => color
        case (_, position) :: tail =>
          getColor(row.tail, column - 1)
      }

    def unzipWithoutIndex(list: List[(List[Int], Int)]): List[List[Int]] =
      list match {
        case Nil => Nil
        case (item, _) :: tail => item :: unzipWithoutIndex(tail)
      }

    board_.zipWithIndex match {
      case (row, position) :: tail if position == coordinate.getRow =>
        getColor(row, coordinate.getColumn)
      case (_, _) :: tail =>
        new Board(
          unzipWithoutIndex(tail)).getColor(new Coordinate(coordinate.getRow - 1, coordinate.getColumn))
    }
  }

  def put(coordinate: Coordinate, player: Int): Board = {

    new Board(
      board_.zipWithIndex.map {
        case (row, position) =>
          if (position != coordinate.getRow)
            row
          else
            row.zipWithIndex.map {
              case (color, position) =>
                if (position == coordinate.getColumn)
                  player
                else
                  color
            }
      })
  }

  def move(from: Coordinate, to: Coordinate): Board =
    this.put(from, -1).put(to, this.getColor(from))


  def getCoordinates(player: Int): List[Coordinate] = {

    def getCoordinates2(player: Int, row: List[Int], fila: Int, columna: Int): List[Coordinate] = {
      row match {
        case Nil => Nil
        case head :: tail => if (head == player)
          new Coordinate(fila, columna) :: getCoordinates2(player, tail, fila, columna + 1)
        else
          getCoordinates2(player, tail, fila, columna + 1)
      }
    }

    def getCoordinates(player: Int, rows: List[List[Int]], fila: Int): List[Coordinate] = {
      rows match {
        case Nil => Nil
        case head :: tail =>
          getCoordinates2(player, head, fila, 0) ++ getCoordinates(player, tail, fila + 1)
      }
    }

    getCoordinates(player, board_, 0)
  }

  def getFree(): List[Coordinate] = this.getCoordinates(-1)

  def isComplete: Boolean = this.getFree().length==3

  def isTicTacToe: Boolean = {

    def isTicTacToe(player: Int): Boolean = {

      def equals(strings: List[String]): Boolean =
        strings match {
          case Nil => true
          case _ :: Nil => true
          case first :: second :: tail if (first == second) => equals(second :: tail)
          case _ :: _ => false
        }

      def getDirections(coordinates: List[Coordinate]): List[String] = {
        coordinates match {
          case Nil => Nil
          case head :: Nil => Nil
          case first :: second :: tail => first.getDirection(second) :: getDirections(second :: tail)
        }
      }

      val coordinates = this.getCoordinates(player)
      val directions = getDirections(coordinates)
      coordinates.length == 3 && equals(directions) && !directions.contains("")
    }
    val firstTicTacToe = Future(isTicTacToe(0))
    val secondTicTacToe = Future(isTicTacToe(1))

    Await.result(firstTicTacToe, 1 seconds) || Await.result(secondTicTacToe, 1 seconds)
  }

  override def equals(that: Any): Boolean =
    that match {
      case that: Board =>
        board_ == that.board_
      case _ => false
    }

  override def hashCode(): Int = {
    val state = Seq(board_)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

}
