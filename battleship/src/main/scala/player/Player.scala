package player

import scala.collection.immutable
import elements._
import game._

case class Player(num: Int, fleet: List[Ship], ownBoard: Board, shotBoard: Board){

    def getNum(): Int = this.num
    def getFleet(): List[Ship] = this.fleet
    def getName(): String = "Player" + this.num


    def addShips(fleet: List[Ship], numShip: Int, shipSize: Int): List[Ship] = {

            if (numShip <= 5) {

                //Get X position for the initial cell of the ship
                GameUtils.promptShipInitCell("posX", numShip+1)
                val posX: Int = GameUtils.getUserInput().toInt

                //Get Y position for the initial cell of the ship
                GameUtils.promptShipInitCell("posY", numShip+1)
                val posY: Int = GameUtils.getUserInput().toInt

                //Get orientation of the ship
                GameUtils.promptShipOrientation()
                val orientation: String = GameUtils.getUserInput()

                val listCells: List[Cell] = List[Cell]()
                //Create the ship
                val shipCells: List[Cell] = Ship.getPosition(posX, posY, orientation, shipSize, listCells)

                val newShip: Ship = new Ship(numShip, shipSize, shipCells)

                val newFleet: List[Ship] = newShip :: fleet

                if (numShip == 3) addShips(newFleet, numShip+1, shipSize)

                else addShips(newFleet, numShip+1, shipSize-1)


        }

        else fleet


    }


}
