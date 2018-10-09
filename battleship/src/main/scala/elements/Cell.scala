package elements

import scala.collection.immutable
import player._
import game._

case class Cell(posX: Int, posY: Int, touched: Boolean = false){

    /** Get the X value of the cell
     *
     *  @return the X value of the cell
     */
    def getPosX(): Int = this.posX

    /** Get the Y value of the cell
     *
     *  @return the Y value of the cell
     */
    def getPosY(): Int = this.posY

    /** Checks if the cell has been hit
     *
     *  @return true if the cell has been hit
     */
    def isTouched(): Boolean = this.touched

    /** Checks if the cell belongs to a ship
     *
     *  @param ship the ship to test
     *  @return true if the ship contains the cell
     */
    def checkIfInShip(ship: Ship): Boolean = ship.getCells().contains(this)

    /** Checks if the cell belongs to the board
     *
     *  @param boardSize the size of the board
     *  @return true if the cell belongs to the board
     */
    def cellInBoard(boardSize: Int): Boolean = this.posX <= boardSize && 0 < this.posX && this.posY <= boardSize && 0 < this.posY

    /** Get the cells which are right next to the cell (up, down, left, right).
     *
     *  @param boardSize the size of the board
     *  @return true if the cell belongs to the board
     */
    def getCellsNeighbours(boardCells: List[Cell]): List[Cell] = {
        val neighboursVertical: List[Cell] = boardCells.filter((cell) => cell.getPosX() == this.getPosX()+1 ||  cell.getPosX() == this.getPosX()-1)
        val neighboursHorizontal: List[Cell] = boardCells.filter((cell) => cell.getPosY() == this.getPosY()+1 ||  cell.getPosY() == this.getPosX()-1)
        neighboursVertical ::: neighboursHorizontal

    }

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
