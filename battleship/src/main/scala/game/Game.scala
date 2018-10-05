package game

import scala.collection.immutable
import player._
import elements._

object Game extends App {

        val player1 = new Player(1, List(), List(), List())
        val player2 = new Player(2, List(), List(), List())


        println("\nPlayer 1 \n")
        val fleetPlayer1 = player1.addShips(List(),4,5)
        println("\nPlayer 2 \n")
        //addShips(fleet, number of the first ship, size of the first ship)
        val fleetPlayer2 = player2.addShips(List(),4,5)

        val cell1: Cell = new Cell(3,4)
        val cell2: Cell = new Cell(2,2)
        val ship1: Ship = fleetPlayer1.head
        // val fleetCells: List[List[Cell]] = fleetPlayer1.map(x => x.cells)
        player1.renderOwnBoard(1, 1, 10, fleetPlayer1, List())

        // // Return the num of the ship which contains the cell
        // val shipTest: Int = fleetPlayer1.filter(ship => Ship.checkIfInShip(cell1,ship)).head.getNum



        //For testing
        print("fleetPlayer1 : " + fleetPlayer1)
        print("\n")
        print("fleetPlayer2 : " + fleetPlayer2)
        print("\n")
        print("checkTrue : " + Ship.checkIfInShip(cell1, ship1))
        print("\n")
        print("checkFalse : " + Ship.checkIfInShip(cell2, ship1))
        print("\n")
        print("fleetCells : " + player1.getFleetCells(fleetPlayer1))
        // print("\n")
        // print("shipTest : " + shipTest)


}
