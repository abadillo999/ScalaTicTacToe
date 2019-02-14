package ticTacToe.models

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
    def checkHorizontal(board: List[List[Int]]): Boolean={
      board match {
        case Nil => false
        case head::tail => equalList(head) || checkHorizontal(tail)
      }
    }

    def checkVertical(board: List[List[Int]]): Boolean = {
      board match {
        case Nil => false
        case _ if (board.head.isEmpty) =>  false
        case _ => equalList(getHeads(board)) || checkVertical(getTails(board))
      }
    }

    def checkDiagonal(board: List[List[Int]], player: Int = -2): Boolean = {
      (board, player) match {
        case (Nil,_) => true
        case (_,-2) => checkDiagonal(getTails(board).tail, board.head.head)
        case (_,-1) => false
        case (_,_) => if (player==board.head.head)
          checkDiagonal(getTails(board.tail), board.head.head)
        else
          false
      }
    }
    def checkInverseDiagonal(board: List[List[Int]]): Boolean = {
      checkDiagonal(board.reverse)
    }


    def equalList(list: List[Int]): Boolean = {
      list match {
        case Nil => true
        case -1 :: _ => false
        case head :: Nil => true
        case head :: tail => if (head == tail.head) {
          equalList(tail) && true}
        else
          false
      }
    }

    def getHeads(board: List[List[Int]]): List[Int] ={
      var bor = board
      board match {
        case Nil => Nil
        case head :: Nil => head.head::Nil
        case head :: tail =>
          if (board.head.isEmpty) Nil
          else head.head :: getHeads(board.tail)
      }
    }

    def getTails(board: List[List[Int]]): List[List[Int]] ={
      board match {
        case Nil => Nil
        case head :: tail => head.tail :: getTails(tail)
      }
    }

    def invertRows(list: List[List[Int]]): List[List[Int]] = {
      board match {
        case Nil => Nil
        case head :: Nil =>  board
        case head :: tail => head :: invertRows(tail)
      }
    }

    checkHorizontal(board_) || checkVertical(board_) || checkDiagonal(board_) || checkInverseDiagonal(board_)

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
