package game

import scala.collection.immutable
import player._
import elements._

object Game extends App {

        val player1 = new Player(1, List(), List(), List())
        val player2 = new Player(2, List(), List(), List())
        val boardSize: Int = 10

        def initializationGame(player1: Player, player2: Player): (Player, Player) = {

            //Create Player 1's fleet
            println("\nPlayer 1 \n")
            //addShips(fleet, number of the first ship, size of the first ship, size of the board)
            val fleetPlayer1 = player1.addShips(List(),4,5,10)

            //Create Player 2's fleet
            println("\nPlayer 2 \n")
            //addShips(fleet, number of the first ship, size of the first ship, size of the board)
            val fleetPlayer2 = player2.addShips(List(),4,5,10)

            val initPlayer1: Player = player1.copy(fleet=fleetPlayer1)
            val initPlayer2: Player = player2.copy(fleet=fleetPlayer2)

            (initPlayer1, initPlayer2)




            // print("Player 2's Board : \n")
            // player2.renderBoard(1, 1, 10, fleetPlayer2, List())
        }


        // Play one round in the game
        def playTurn(player1: Player, player2: Player): Unit = {

            val hitsP1: List[Cell] = player1.getHits()
            val missP1: List[Cell] = player1.getMiss()
            val hitsP2: List[Cell] = player2.getHits()
            val missP2: List[Cell] = player2.getMiss()
            val boardSize: Int = this.boardSize
            val fleetPlayer1: List[Ship] = player1.getFleet()

            // Render players' hit board
            print("Hit board : \n")
            player1.renderBoard(1, 1, boardSize, List(), hitsP1, missP1)
            print("\n")

            // Render players' own board
            print("Your board : \n")
            player1.renderBoard(1, 1, boardSize, fleetPlayer1, hitsP2, missP2)
            print("\n")

            // Player 1 shoot on Player 2
            val playersUpdated = Player.shoot(player1, player2)

            val player1Updated: Player = playersUpdated._1

            val player2Updated: Player = playersUpdated._2

            if (player2Updated.fleetIsSunk() == false) {

            // New turn, the other player becomes the shooter
            playTurn(player2Updated, player1Updated)
        }

        else print("\nCongratulations, " + player1Updated.getName + " won!\n")
    }
        // TO DO : Game settings //


        val initPlayers: (Player, Player) = initializationGame(player1, player2)
        playTurn(initPlayers._1, initPlayers._2)

        // val cell1: Cell = new Cell(3,4)
        // val cell2: Cell = new Cell(2,2)
        // val cell3: Cell = new Cell(12,2)
        // val ship1: Ship = fleetPlayer1.head
        // val ship2: Ship = fleetPlayer2.head
        // val fleetCells: List[List[Cell]] = fleetPlayer1.map(x => x.cells)

        // print("\n In the board :" + cell1.cellInBoard(10))
        // print("\n In the board :" + cell3.cellInBoard(10))
        // print("\n In the board :" + ship1.shipInBoard(10))
        // print("\n In the board :" + ship2.shipInBoard(10))


        // // Return the num of the ship which contains the cell
        // val shipTest: Int = fleetPlayer1.filter(ship => Ship.checkIfInShip(cell1,ship)).head.getNum



        //For testing purpose
        // print("fleetPlayer1 : " + player1.fleet)
        // print("\n")
        // print("fleetPlayer2 : " + fleetPlayer2)
        // print("\n")
        // print("checkTrue : " + cell1.checkIfInShip(ship1))
        // print("\n")
        // print("checkFalse : " + cell2.checkIfInShip(ship1))
        // print("\n")
        // print("fleetCells : " + Player.getFleetCells(fleetPlayer1))
        // print("\n")
        // print("shipTest : " + shipTest)


}
