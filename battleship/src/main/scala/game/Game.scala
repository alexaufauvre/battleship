package game

import scala.collection.immutable
import player._
import elements._

object Game extends App {

        val player1 = new Player(1, List(), Board(10), Board(10))
        val player2 = new Player(2, List(), Board(10), Board(10))


        println("\nPlayer 1 \n")
        val fleetPlayer1 = player1.addShips(List(),1,5)
        println("\nPlayer 2 \n")
        //addShips(fleet, number of the first ship, size of the first ship)
        val fleetPlayer2 = player2.addShips(List(),1,5)

        //For testing
        print("fleetPlayer1 : " + fleetPlayer1)
        print("\n")
        print("fleetPlayer2 : " + fleetPlayer2)

}
