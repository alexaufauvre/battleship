package elements

import scala.collection.immutable
import player._
import game._

case class Ship(num: Int, size: Int, cells: List[Cell]){

    /** Get the number of the ship
     *
     *  @return the number of the ship
     */
    def getNum(): Int = this.num

    /** Get the size of the ship
     *
     *  @return the size of the ship
     */
    def getSize(): Int = this.size

    /** Get the cells belonging to the ship
     *
     *  @return the cells belonging to the ship
     */
    def getCells(): List[Cell] = this.cells


    /** Checks if the ship is inside the board
     *
     *  @param boardSize the size of the board
     *  @return true if the ship is inside the board
     */
    def shipInBoard(boardSize: Int): Boolean = this.getCells().filter((cell) => cell.cellInBoard(boardSize)).length == this.getSize()

    /** Checks if there's already a ship in this position
     *
     *  @param fleet the fleet on which we want to check
     *  @return true if there's no ship already in this position (if the ship can be placed)
     */
    def positionAvailable(fleet: List[Ship]): Boolean = this.getCells().filter((cell) => Player.getFleetCells(fleet).contains(cell)).length == 0


}

object Ship{

    /** Retrieve the list of cells to create the ship corresponding to the user input
     *
     *  @param posX the X of the initial cell
     *  @param posY the Y of the initial cell
     *  @param orientation the orientation of the ship
     *  @param size the size of the ship
     *  @param listCells the list of cells that belongs to the ship
     *  @return the final list of cells that belongs to the ship
     */
    def fillShip(posX: Int, posY: Int, orientation: String, size: Int, listCells: List[Cell]): List[Cell] = {

        if (size==0){
            listCells
        }
        else{

        orientation match{
            case "L" => {
                val newCell: Cell = new Cell(posX, posY)
                val newListCells: List[Cell] = newCell :: listCells
                fillShip(posX-1, posY, "L", size-1, newListCells)

            }
            case "R" => {
                val newCell: Cell = new Cell(posX, posY)
                val newListCells: List[Cell] = newCell :: listCells
                fillShip(posX+1, posY, "R", size-1, newListCells)

            }
            case "U" => {
                val newCell: Cell = new Cell(posX, posY)
                val newListCells: List[Cell] = newCell :: listCells
                fillShip(posX, posY-1, "U", size-1, newListCells)

            }
            case "D" => {
                val newCell: Cell = new Cell(posX, posY)
                val newListCells: List[Cell] = newCell :: listCells
                fillShip(posX, posY+1, "D", size-1, newListCells)

            }
            case _ => {
                println("Please chose a correct orientation\n")
                //Get orientation of the ship
                GameUtils.promptShipOrientation()
                val newOrientation: String = GameUtils.getUserInput()
                fillShip(posX, posY, newOrientation, size, listCells)
            }
        }
    }
    }

    /**  Check if the ship is sunk.(All his cells are touched)
     *
     *  @param ship the ship to test
     *  @return true if the ship is sunk
     */
    def isSunk(ship: Ship): Boolean = {
        val shipCells: List[Cell] = ship.getCells()
        if (shipCells.filter(_.isTouched() == false).length == 0){
            true
        }
        else false
    }


}
