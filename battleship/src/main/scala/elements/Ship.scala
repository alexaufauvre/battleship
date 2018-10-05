package elements

import scala.collection.immutable
import player._
import game._

case class Ship(num: Int, size: Int, cells: List[Cell], sunk: Boolean = false){

    def getNum(): Int = this.num
    def getSize(): Int = this.size
    def getCells(): List[Cell] = this.cells
    def isSunk(): Boolean = this.sunk

}

object Ship{

    def createShip(posX: Int, posY: Int, orientation: String, size: Int, listCells: List[Cell]): List[Cell] = {

        if (size==0){
            listCells
        }
        else{

        orientation match{
            case "L" => {
                val newCell: Cell = new Cell(posX, posY)
                val newListCells: List[Cell] = newCell :: listCells
                createShip(posX-1, posY, "L", size-1, newListCells)

            }
            case "R" => {
                val newCell: Cell = new Cell(posX, posY)
                val newListCells: List[Cell] = newCell :: listCells
                createShip(posX+1, posY, "R", size-1, newListCells)

            }
            case "U" => {
                val newCell: Cell = new Cell(posX, posY)
                val newListCells: List[Cell] = newCell :: listCells
                createShip(posX, posY-1, "U", size-1, newListCells)

            }
            case "D" => {
                val newCell: Cell = new Cell(posX, posY)
                val newListCells: List[Cell] = newCell :: listCells
                createShip(posX, posY+1, "D", size-1, newListCells)

            }
            case _ => {
                println("Please chose a correct orientation\n")
                createShip(posX, posY, orientation, size, listCells)
            }
        }
    }
    }

    def checkIfInShip(cell: Cell, ship: Ship): Boolean = ship.getCells().contains(cell)

}
