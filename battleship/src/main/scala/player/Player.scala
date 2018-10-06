package player

import scala.collection.immutable
import elements._
import game._

case class Player(num: Int, fleet: List[Ship], hit: List[Cell], miss: List[Cell]){

    def getNum(): Int = this.num
    def getFleet(): List[Ship] = this.fleet
    def getName(): String = "Player" + this.num
    def getHit(): List[Cell] = this.hit
    def getMiss(): List[Cell] = this.miss


    def addShips(fleet: List[Ship], numShip: Int, shipSize: Int, boardSize: Int): List[Ship] = {

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

                //Fill the ship
                val shipCells: List[Cell] = Ship.fillShip(posX, posY, orientation, shipSize, listCells)

                //Create the ship
                val newShip: Ship = new Ship(numShip, shipSize, shipCells)

                //If the ship is inside the board
                if (newShip.shipInBoard(boardSize) == false){
                    print("Ship out of the board. Please enter valid values.")
                    addShips(fleet, numShip, shipSize, boardSize)
                }
                else if(newShip.positionAvailable(fleet) == false){
                    print("A ship is already at this position. Please enter valid values.")
                    addShips(fleet, numShip, shipSize, boardSize)
                }

                else{
                    //Add the ship to the fleet
                    val newFleet: List[Ship] = newShip :: fleet
                    if (numShip == 3) addShips(newFleet, numShip+1, shipSize, boardSize)

                    else addShips(newFleet, numShip+1, shipSize-1, boardSize)

                }

        }

        else fleet


    } //end addShips

    //Render the board containing our fleet
    def renderBoard(x: Int, y: Int, boardSize: Int, fleet: List[Ship], hit: List[Cell], miss: List[Cell]): Unit = {

        // Numbers on bottom
        if (x < boardSize+1 && y == boardSize+1){
            print("  " + x + " ")
            renderBoard(x+1, y, boardSize, fleet, hit, miss)
        }

        //Numbers on right
        else if(x > boardSize && y < boardSize+1){
            println("| " + y)
            renderBoard(1, y+1, boardSize, fleet, hit, miss)
        }

        //New row
        else if(x == boardSize+1 && y == boardSize+1){
            println()
            renderBoard(1, y+1, boardSize, fleet, hit, miss)
        }

        else if(x <= boardSize && y < boardSize+1){
            val currentCell: Cell = new Cell(x, y)
            val fleetCells: List[Cell] = Player.getFleetCells(fleet)
            if (fleetCells.contains(currentCell)) {
                print("|_X_")
            }
            else if (hit.contains(currentCell)) {
                print("|_H_")
            }
            else if (miss.contains(currentCell)) {
                print("|_O_")
            }
            else print("|_ _")

            renderBoard(x+1, y, boardSize, fleet, hit, miss)

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
        }//endRenderBoard




    def fleetIsSunk(): Boolean = {
        this.fleet.filter((ship) => Ship.isSunk(ship) == false).length == 0
    }

}

object Player{

    //Get all the cells of a fleet
    def getFleetCells(fleet: List[Ship]): List[Cell] = fleet.flatMap(x => x.getCells())

    /**
     * Ask the position of the cell the player wants to shoot.
     * Checks if there's a ship on this cell.
     * Returns the player who has been shot updated.
     */
    def shoot(shooter: Player, opponent: Player): (Player, Player) = {

        //Get X position for the cell to shoot
        GameUtils.promptShootCell("posX")
        val posX: Int = GameUtils.getUserInput().toInt

        //Get Y position for the cell to shoot
        GameUtils.promptShootCell("posY")
        val posY: Int = GameUtils.getUserInput().toInt

        val shotCell: Cell = new Cell(posX, posY)

        val opponentShot: Player = opponent.copy()

        val fleetShot: List[Ship] = opponentShot.getFleet()

        // fleetShot.map((ship) => cellShot.checkIfInShip(ship))
        fleetShot.foreach((ship) => if (shotCell.checkIfInShip(ship)){

            print("\nShip hit!\n")

            //Register the shot in the hit list
            val newHit: List[Cell] = shotCell :: shooter.getHit()

            val shooterUpdated: Player = shooter.copy(hit=newHit)

            // ship.createUpdatedShip(cellShot)

            // Create a new ship with the hit cell updated
            // val updatedShip: Ship = ship.getCells().foreach((cell) => Cell.hitCell(shotCell, cell))
            val updatedCells: List[Cell] = ship.getCells().map((cell) => Cell.hitCell(shotCell, cell))

            //Ship with the cell marked as touched
            val updatedShip: Ship = ship.copy(cells=updatedCells)

            //Display a message if the ship is sunk
            if (Ship.isSunk(updatedShip)) print("The ship " + updatedShip.getNum() + " is sunk!")

            // Create a temporary fleet without the old version of the ship
            val tempFleet: List[Ship] = fleetShot.filter(_.getNum() != ship.getNum())

            // Add the new version of the ship in the fleet
            val updatedFleet: List[Ship] = updatedShip :: tempFleet

            // Create a version of the player with the updated fleet
            val opponentUpdated: Player = opponent.copy(fleet=updatedFleet)

            // Return the new state of the players
            return (shooterUpdated, opponentUpdated)

           }
       )
            print("\nWater...\n")

            //Register the shot in the miss list
            val newMiss: List[Cell] = shotCell :: shooter.getMiss()
            val shooterUpdated: Player = shooter.copy(miss=newMiss)

            // Return the new state of the players
            return (shooterUpdated, opponent)


    }


}
