package elements

import scala.collection.immutable
import player._
import game._

case class Cell(posX: Int, posY: Int, touched: Boolean = false){

    def getPosX(): Int = this.posX
    def getPosY(): Int = this.posY
    def isTouched(): Boolean = this.touched

    // def hitCell(posX: Int, posY: Int): Cell = {
    //
    // }



}
