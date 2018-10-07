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
            val fleetPlayer1 = player1.addShips(List(),1,5,10)

            //Create Player 2's fleet
            println("\nPlayer 2 \n")
            //addShips(fleet, number of the first ship, size of the first ship, size of the board)
            val fleetPlayer2 = player2.addShips(List(),1,5,10)

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

            print("\n" + player1.getName() + "'s turn\n\n")
            // Render players' hit board
            print("Hit board : \n")
            player1.renderBoard(1, 1, boardSize, List(), hitsP1, missP1)
            print("\n")

            // Render players' own board
            print("Your board : \n")
            player1.renderBoard(1, 1, boardSize, fleetPlayer1, hitsP2, missP2)
            print("\n")

            // Player 1 shoots on Player 2
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

        def main(): Unit = {
        val initPlayers: (Player, Player) = initializationGame(player1, player2)
        playTurn(initPlayers._1, initPlayers._2)
    }

    this.main()



}
