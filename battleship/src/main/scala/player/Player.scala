package player

import scala.collection.immutable
import elements._
import game._

case class Player(num: Int, fleet: List[Ship], ownBoardHit: List[Cell], opponentBoardHit: List[Cell]){

    def getNum(): Int = this.num
    def getFleet(): List[Ship] = this.fleet
    def getName(): String = "Player" + this.num


    def addShips(fleet: List[Ship], numShip: Int, shipSize: Int): List[Ship] = {

            if (numShip <= 5) {

                //Get X position for the initial cell of the ship
                GameUtils.promptShipInitCell("posX", numShip)
                val posX: Int = GameUtils.getUserInput().toInt

                //Get Y position for the initial cell of the ship
                GameUtils.promptShipInitCell("posY", numShip)
                val posY: Int = GameUtils.getUserInput().toInt

                //Get orientation of the ship
                GameUtils.promptShipOrientation()
                val orientation: String = GameUtils.getUserInput()

                val listCells: List[Cell] = List[Cell]()
                //Create the ship
                val shipCells: List[Cell] = Ship.createShip(posX, posY, orientation, shipSize, listCells)

                val newShip: Ship = new Ship(numShip, shipSize, shipCells)

                val newFleet: List[Ship] = newShip :: fleet

                if (numShip == 3) addShips(newFleet, numShip+1, shipSize)

                else addShips(newFleet, numShip+1, shipSize-1)


        }

        else fleet


    } //end addShips

    //Get all the cells of a fleet
    def getFleetCells(fleet: List[Ship]): List[Cell] = fleet.flatMap(x => x.getCells())

    //Render the board containing our fleet
    def renderOwnBoard(x: Int, y: Int, boardSize: Int, fleet: List[Ship], ownBoardHit: List[Cell]): Unit = {

        // Numbers on bottom
        if (x < boardSize+1 && y == boardSize+1){
            print("  " + x + " ")
            renderOwnBoard(x+1, y, boardSize, fleet, ownBoardHit)
        }

        //Numbers on right
        else if(x > boardSize && y < boardSize+1){
            println("| " + y)
            renderOwnBoard(1, y+1, boardSize, fleet, ownBoardHit)
        }

        //New row
        else if(x == boardSize+1 && y == boardSize+1){
            println()
            renderOwnBoard(1, y+1, boardSize, fleet, ownBoardHit)
        }

        else if(x <= boardSize && y < boardSize+1){
            val currentCell: Cell = new Cell(x, y)
            val fleetCells: List[Cell] = getFleetCells(fleet)
            if (fleetCells.contains(currentCell)) {
                print("|_X_")
            }
            else print("|_ _")

            renderOwnBoard(x+1, y, boardSize, fleet, ownBoardHit)

            /*
            //Check if the cell is in a ship. True: print the ship's number. False: print a blank cell.
            var isInShip: Boolean = false
            fleet.foreach{ship=>
                if (Ship.checkIfInShip(cell, ship)){
                    val numShip: Int = ship.getNum()
                    isInShip = true
                    println("| " + numShip)
                }
            }
            if (isInShip == false){
                println("|  ")
            }
*/

            }
        }//endRenderOwnBoard


    //Render the opponent's board
    def renderOpponentBoard(x: Int, y: Int, opponentBoardHit: List[Cell]): Unit = {

    }

}
