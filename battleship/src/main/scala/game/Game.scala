package game

import scala.collection.immutable
import player._
import elements._

object Game extends App {

        val player1 = new Player(1, List(), List(), List())
        val player2 = new Player(2, List(), List(), List())


        println("\nPlayer 1 \n")
        val fleetPlayer1 = player1.addShips(List(),5,5,10)
        println("\nPlayer 2 \n")
        //addShips(fleet, number of the first ship, size of the first ship)
        val fleetPlayer2 = player2.addShips(List(),5,5,10)

        val cell1: Cell = new Cell(3,4)
        val cell2: Cell = new Cell(2,2)
        val cell3: Cell = new Cell(12,2)
        val ship1: Ship = fleetPlayer1.head
        val ship2: Ship = fleetPlayer2.head
        // val fleetCells: List[List[Cell]] = fleetPlayer1.map(x => x.cells)
        print("Player 1's Board : \n")
        player1.renderOwnBoard(1, 1, 10, fleetPlayer1, List())
        print("\n")
        print("Player 2's Board : \n")
        player2.renderOwnBoard(1, 1, 10, fleetPlayer2, List())
        print("\n In the board :" + cell1.cellInBoard(10))
        print("\n In the board :" + cell3.cellInBoard(10))
        print("\n In the board :" + ship1.shipInBoard(10))
        print("\n In the board :" + ship2.shipInBoard(10))





        // // Return the num of the ship which contains the cell
        // val shipTest: Int = fleetPlayer1.filter(ship => Ship.checkIfInShip(cell1,ship)).head.getNum



        //For testing
        print("fleetPlayer1 : " + fleetPlayer1)
        print("\n")
        // print("fleetPlayer2 : " + fleetPlayer2)
        // print("\n")
        print("checkTrue : " + cell1.checkIfInShip(ship1))
        print("\n")
        print("checkFalse : " + cell2.checkIfInShip(ship1))
        print("\n")
        print("fleetCells : " + player1.getFleetCells(fleetPlayer1))
        // print("\n")
        // print("shipTest : " + shipTest)


}
