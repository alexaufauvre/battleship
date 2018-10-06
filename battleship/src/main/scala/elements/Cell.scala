package elements

import scala.collection.immutable
import player._
import game._

case class Cell(posX: Int, posY: Int, touched: Boolean = false){

    def getPosX(): Int = this.posX
    def getPosY(): Int = this.posY
    def isTouched(): Boolean = this.touched


    //Checks if a cell belongs to a ship
    def checkIfInShip(ship: Ship): Boolean = ship.getCells().contains(this)

    def cellInBoard(boardSize: Int): Boolean = this.posX <= boardSize && 0 < this.posX && this.posY <= boardSize && 0 < this.posY



}

object Cell{
    /**
     * @params: shotCell is the cell on which the player has shot. testCell is the cell to test if it's hit or not
     */
    def hitCell(shotCell: Cell, testCell: Cell): Cell = {
        if(shotCell == testCell){

            val updatedCell: Cell = testCell.copy(touched=true)
            updatedCell
        }
        else testCell
    }
}
