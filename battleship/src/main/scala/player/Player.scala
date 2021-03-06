package player

import scala.collection.immutable
import scala.io.StdIn.readLine
import scala.util.Random
import elements._
import game._

case class Player(num: Int, aiLevel: Int, fleet: List[Ship], hits: List[Cell], miss: List[Cell], lastShot: Cell = Cell(0,0), wins: Int = 0){

    /** Get the number of the player
     *
     *  @return the number of the player
     */
    def getNum(): Int = this.num

    /** Get the AI level of the player
     *
     *  @return the AI level of the player
     */
    def getAiLevel(): Int = this.aiLevel

    /** Get the fleet of the player
     *
     *  @return the fleet of the player
     */
    def getFleet(): List[Ship] = this.fleet

    /** Get the name of the player
     *
     *  @return the name of the player
     */
    def getName(): String = "Player" + this.num

    /** Get the list of cells the player hit
     *
     *  @return the list of cells the player hit
     */
    def getHits(): List[Cell] = this.hits

    /** Get the list of cells the player missed
     *
     *  @return the list of cells the player missed
     */
    def getMiss(): List[Cell] = this.miss

    /** Get the last shot performed by the player
     *
     *  @return the last shot performed by the player
     */
    def getLastShot(): Cell = this.lastShot

    /** Get the number of wins of the player
     *
     *  @return the number of wins of the player
     */
    def getWins(): Int = this.wins


    /**  Get the position of the player's ship
     *
     *  @param aiLevel the player's AI level
     *  @param boardSize the size of the board
     *  @param numShip the number of the ship
     *  @return the X of the ship's initial cell, the X of the ship's initial cell, the ship's orientation
     */
    def getShipPosition(aiLevel: Int, boardSize: Int, numShip: Int): (Int, Int, String) = {

        // If the player is human, ask for entering his fleet
        if (aiLevel == 0){

            //Get X position for the initial cell of the ship
            GameUtils.promptShipInitCell("posX", numShip)
            val inputPosX: String = GameUtils.getUserInput()

            //Get Y position for the initial cell of the ship
            GameUtils.promptShipInitCell("posY", numShip)
            val inputPosY: String = GameUtils.getUserInput()

            if (GameUtils.isInt(inputPosX) && GameUtils.isInt(inputPosY)){

                    val posY: Int = inputPosY.toInt
                    val posX: Int = inputPosX.toInt

                    //Get orientation of the ship
                    GameUtils.promptShipOrientation()
                    val orientation: String = GameUtils.getUserInput()

                    (posX, posY, orientation)
            }
            else {
                GameUtils.promptBadValues()
                getShipPosition(aiLevel, boardSize, numShip)
            }



        }

        // If the player is an AI
        else {
            //Get X position for the initial cell of the ship
            val posX: Int = Random.nextInt(boardSize-1)+1

            //Get Y position for the initial cell of the ship
            val posY: Int = Random.nextInt(boardSize-1)+1

            //Get orientation of the ship
            val orientations: List[String] = List("L","R","U","D")
            val randomIndex: Int = Random.nextInt(orientations.length)
            val orientation: String = orientations(randomIndex)

            return (posX, posY, orientation)

        }
    }


    /**  Get the position of the player's ship
     *
     *  @param fleet the player's fleet
     *  @param numShip the number of the ship
     *  @param shipSize the size of the ship
     *  @param boardSize the size of the board
     *  @return the fleet of the player
     */
    def addShips(fleet: List[Ship], numShip: Int, shipSize: Int, boardSize: Int): List[Ship] = {

            val aiLevel: Int = this.getAiLevel()

            if (numShip <= 5) {

                val shipPosition: (Int, Int, String) = getShipPosition(aiLevel, boardSize, numShip)

                val posX: Int = shipPosition._1
                val posY: Int = shipPosition._2
                val orientation: String = shipPosition._3

                val listCells: List[Cell] = List[Cell]()

                //Fill the ship
                val shipCells: List[Cell] = Ship.fillShip(posX, posY, orientation, shipSize, listCells)

                //Create the ship
                val newShip: Ship = new Ship(numShip, shipSize, shipCells)

                //If the ship is inside the board
                if (newShip.shipInBoard(boardSize) == false){
                    if (aiLevel == 0){
                        print("\nShip out of the board. Please enter valid values.\n")
                    }
                    addShips(fleet, numShip, shipSize, boardSize)
                }
                else if(newShip.positionAvailable(fleet) == false){
                    if (aiLevel == 0){
                        print("\nA ship is already at this position. Please enter valid values.\n")
                    }
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


    }


    /**  Render the board containing our fleet
     *
     *  @param x the x coord of the current cell
     *  @param y the y coord of the current cell
     *  @param boardSize the size of the board
     *  @param fleet the fleet of the player
     *  @param hits the hits of the player
     *  @param miss the miss of the player
     */
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
        }


    /**  Checks if the fleet of the player is sunk
     *
     *  @return true if the fleet is sunk
     */
    def fleetIsSunk(): Boolean = {
        this.fleet.filter((ship) => Ship.isSunk(ship) == false).length == 0
    }

    /**  Checks if the player already shot in this position
     *
     *  @param shotCell the cell to test
     *  @return true if the cell was already shot
     */
    def alreadyShot(shotCell: Cell): Boolean = {
        this.getHits().contains(shotCell) || this.getMiss().contains(shotCell)
    }

    /**   Get the position of the shoot depending on the AI level
     *
     *  @param lastShot the last cell shot by the player
     *  @return the position of the cell to shoot
     */
    def getShootPosition(lastShot: Cell): (Int, Int) = {

        val aiLevel: Int = this.aiLevel
        val hits: List[Cell] = this.hits
        val miss: List[Cell] = this.miss

        // Human player
        if (aiLevel == 0){

            //Get X position for the cell to shoot
            GameUtils.promptShootCell("posX")
            val inputPosX: String = GameUtils.getUserInput()

            //Get Y position for the cell to shoot
            GameUtils.promptShootCell("posY")
            val inputPosY: String = GameUtils.getUserInput()

            if (GameUtils.isInt(inputPosX) && GameUtils.isInt(inputPosY)){
                val posX: Int = inputPosX.toInt
                val posY: Int = inputPosY.toInt
                return (posX, posY)
            }

            else {
                GameUtils.promptBadValues()
                getShootPosition(lastShot)
            }

        }

        // Easy AI player
        else if (aiLevel == 1){

            val posX: Int = Random.nextInt(Game.boardSize-1)+1
            val posY: Int = Random.nextInt(Game.boardSize-1)+1

            return (posX, posY)

        }

        // Medium AI player
        else if (aiLevel == 2){

            val boardSize: Int = Game.boardSize

            // Retrieve all the cells of the board
            val boardCells: List[Cell] = Game.getBoardCells(1,1,boardSize,List())

            // Remove the hit cells
            val tempCells: List[Cell] = boardCells diff hits

            // Remove the missed cells
            val availableCells: List[Cell] = tempCells diff miss

            // Chose a random cell among the available cells
            val randomIndex: Int = Random.nextInt(availableCells.length-1)+1
            val cellShot: Cell = availableCells(randomIndex).copy()
            // Get the X and Y values
            val posX: Int = cellShot.getPosX()
            val posY: Int = cellShot.getPosY()


            return (posX, posY)
        }

        // Hard AI player
        else{

            // Retrieve all the cells of the board
            val boardCells: List[Cell] = Game.getBoardCells(1,1,Game.boardSize,List())

            // Remove the hit cells
            val tempCells: List[Cell] = boardCells diff hits

            // Remove the missed cells
            val availableCells: List[Cell] = tempCells diff miss

            if (hits.isEmpty == false){

                // Chose a random cell among the hits
                val randomIndex: Int = Random.nextInt(hits.length)
                val target: Cell = hits(randomIndex).copy()

                // Retrieve the cells next to the last shot which are available
                val availableNeighbours: List[Cell] = target.getCellsNeighbours(availableCells)

                // If the last shot was a hit, the AI will try to shoot a cell next to this last shot
                if (availableNeighbours.isEmpty == false){

                        // Chose a random cell among the available neighbours
                        val randomIndex: Int = Random.nextInt(availableNeighbours.length)
                        val cellShot: Cell = availableNeighbours(randomIndex).copy()

                        // Get the X and Y values
                        val posX: Int = cellShot.getPosX()
                        val posY: Int = cellShot.getPosY()


                        return (posX, posY)

                }

                else{
                    // Chose a random cell among the available cells (same as Medium AI)
                    val randomIndex: Int = Random.nextInt(availableCells.length)
                    val cellShot: Cell = availableCells(randomIndex).copy()

                    // Get the X and Y values
                    val posX: Int = cellShot.getPosX()
                    val posY: Int = cellShot.getPosY()

                    return (posX, posY)

                }
            }


            else {
                // Chose a random cell among the available cells (same as Medium AI)
                val randomIndex: Int = Random.nextInt(availableCells.length-1)+1
                val cellShot: Cell = availableCells(randomIndex).copy()

                // Get the X and Y values
                val posX: Int = cellShot.getPosX()
                val posY: Int = cellShot.getPosY()

                return (posX, posY)
            }


        }


    }

}

object Player{

    /** Get all the cells of a fleet
     *
     *  @param fleet the fleet we want the cells
     *  @return the list of all the cells belonging to the fleet
     */
    def getFleetCells(fleet: List[Ship]): List[Cell] = fleet.flatMap(x => x.getCells())



    /**
     * Ask the position of the cell the player wants to shoot. Checks if the shot is valid.
     *
     * @param shooter the player who shoots
     * @param opponent the player who receive the shot
     * @return both players updated.
     */
    def shoot(shooter: Player, opponent: Player): (Player, Player) = {

        val aiLevel: Int = shooter.getAiLevel()

        val lastShot: Cell = shooter.getLastShot()

        val shootPosition: (Int, Int) = shooter.getShootPosition(lastShot)

        val posX: Int = shootPosition._1
        val posY: Int = shootPosition._2


        val shotCell: Cell = new Cell(posX, posY)

        //Checks if the shot is valid
        if (shotCell.cellInBoard(Game.boardSize) == false){
            print("\nCell out of the board. Please enter valid values.\n")
            return shoot(shooter, opponent)
        }

        // Easy AI player can shoot several times the same position
        if (aiLevel != 1) {
            // Checks if the player already shot this position
            if (shooter.alreadyShot(shotCell)){
                print("\nYou already shot this position. Please enter another position.\n")
                return shoot(shooter, opponent)
            }
        }

        println("\n" + shooter.getName() + " shot in (" + posX +", " + posY + ")\n")

        val shooterAfterShot: Player = shooter.copy(lastShot=lastShot)

        val opponentShot: Player = opponent.copy()

        val fleetShot: List[Ship] = opponentShot.getFleet()

        // Checks if the shot hit a ship
        fleetShot.foreach((ship) => if (shotCell.checkIfInShip(ship)){

            print("\nShip hit!\n\n")

            //Register the shot in the hit list
            val newHit: List[Cell] = shotCell :: shooterAfterShot.getHits()

            val shooterUpdated: Player = shooterAfterShot.copy(hits=newHit)

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
