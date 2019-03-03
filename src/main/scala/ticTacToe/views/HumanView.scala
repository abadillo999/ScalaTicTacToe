package ticTacToe.views

import ticTacToe.models.Coordinate

class HumanView extends PlayerView {

  def getMode:GameMode = {
    val mode = IOManager.readString("Game mode? [single-dual-demo]")
    if (mode=="single"){
      SingleMode
    } else if(mode=="dual"){
      DualMode
    } else if(mode=="demo"){
      DemoMode
    } else {
      this.getMode
    }
  }
  def tryAgain : Any = {
    val mode = IOManager.readString("Try again? [yes-no]")
    if (mode == "yes") {
      this.getMode
    } else if (mode == "no") {
      StopMessage
    } else {
      this.tryAgain
    }
  }

  def read (list: List[Coordinate]) :Coordinate = {
    val row = IOManager.readInt("Row? [1-3]")
    val column = IOManager.readInt("Column? [1-3]")
    val coordinate = new Coordinate(row-1, column-1)
    if (!list.contains(coordinate)){
      this.read(list)
    }else{
      coordinate
    }
  }

  def stop: Unit = {
    IOManager.write("Hooman view stopped\n")
    context.stop(self)
  }

}
