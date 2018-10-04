package player

import scala.io.StdIn.readLine
import scala.collection.immutable
import elements._
import game._

object GameUtils{

    def promptShipInitCell(pos: String, numShip: Int): Unit = println("\nEnter the "+ pos + " for the ship " + numShip)
    def promptShipOrientation(): Unit = println("\n(L)eft, (R)ight, (U)p, (D)own")

    def getUserInput(): String = readLine.trim.toUpperCase


}
