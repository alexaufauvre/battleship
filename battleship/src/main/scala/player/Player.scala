package player

import scala.collection.immutable
import elements._
import game._

case class Player(num: Int, fleet: List[Ship], hits: List[Cell], miss: List[Cell]){

    def getNum(): Int = this.num
    def getFleet(): List[Ship] = this.fleet
    def getName(): String = "Player" + this.num
    def getHits(): List[Cell] = this.hits
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
                    print("\nShip out of the board. Please enter valid values.\n")
                    addShips(fleet, numShip, shipSize, boardSize)
                }
                else if(newShip.positionAvailable(fleet) == false){
                    print("\nA ship is already at this position. Please enter valid values.\n")
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
    def renderBoard(x: Int, y: Int, boardSize: Int, fleet: List[Ship], hits: List[Cell], miss: List[Cell]): Unit = {

        // Numbers on bottom
        if (x < boardSize+1 && y == boardSize+1){
            print("  " + x + " ")
            renderBoard(x+1, y, boardSize, fleet, hits, miss)
        }

        //Numbers on right
        else if(x > boardSize && y < boardSize+1){
            println("| " + y)
            renderBoard(1, y+1, boardSize, fleet, hits, miss)
        }

        //New row
        else if(x == boardSize+1 && y == boardSize+1){
            println()
            renderBoard(1, y+1, boardSize, fleet, hits, miss)
        }

        else if(x <= boardSize && y < boardSize+1){
            val currentCell: Cell = new Cell(x, y)
            val fleetCells: List[Cell] = Player.getFleetCells(fleet)
            if (fleetCells.contains(currentCell)) {
                print("|_X_")
            }
            else if (hits.contains(currentCell)) {
                print(Console.RED + "|_H_" + Console.RESET)
            }
            else if (miss.contains(currentCell)) {
                print(Console.BLUE + "|_O_" + Console.RESET)
            }
            else print("|_ _")

            renderBoard(x+1, y, boardSize, fleet, hits, miss)
            }
        }//endRenderBoard



    //Checks if the fleet of the player is sunk
    def fleetIsSunk(): Boolean = {
        this.fleet.filter((ship) => Ship.isSunk(ship) == false).length == 0
    }

    // Checks if the player already shot in this position
    def alreadyShot(shotCell: Cell): Boolean = {
        this.getHits().contains(shotCell) || this.getMiss().contains(shotCell)
    }

}

object Player{

    //Get all the cells of a fleet
    def getFleetCells(fleet: List[Ship]): List[Cell] = fleet.flatMap(x => x.getCells())

    /**
     * Ask the position of the cell the player wants to shoot.
     * Checks if the shot is valid.
     * Returns both players updated.
     */
    def shoot(shooter: Player, opponent: Player): (Player, Player) = {

        //Get X position for the cell to shoot
        GameUtils.promptShootCell("posX")
        val posX: Int = GameUtils.getUserInput().toInt

        //Get Y position for the cell to shoot
        GameUtils.promptShootCell("posY")
        val posY: Int = GameUtils.getUserInput().toInt

        val shotCell: Cell = new Cell(posX, posY)

        //Checks if the shot is valid
        if (shotCell.cellInBoard(Game.boardSize) == false){
            print("\nCell out of the board. Please enter valid values.\n")
            return shoot(shooter, opponent)
        }

        if (shooter.alreadyShot(shotCell)){
            print("\nYou already shot this position. Please enter another position.\n")
            return shoot(shooter, opponent)
        }

        val opponentShot: Player = opponent.copy()

        val fleetShot: List[Ship] = opponentShot.getFleet()

        // Checks if the shot hit a ship
        fleetShot.foreach((ship) => if (shotCell.checkIfInShip(ship)){

            print("\nShip hit!\n\n")

            //Register the shot in the hit list
            val newHit: List[Cell] = shotCell :: shooter.getHits()

            val shooterUpdated: Player = shooter.copy(hits=newHit)

            // Create a new ship with the hit cell updated
            val updatedCells: List[Cell] = ship.getCells().map((cell) => Cell.hitCell(shotCell, cell))

            //Ship with the cell marked as touched
            val updatedShip: Ship = ship.copy(cells=updatedCells)

            //Display a message if the ship is sunk
            if (Ship.isSunk(updatedShip)) {
                print("\nThe ship " + updatedShip.getNum() + " is sunk!\n")
            }

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
            print("\nWater...\n\n")

            //Register the shot in the miss list
            val newMiss: List[Cell] = shotCell :: shooter.getMiss()
            val shooterUpdated: Player = shooter.copy(miss=newMiss)

            // Return the new state of the players
            return (shooterUpdated, opponent)


    }


}
