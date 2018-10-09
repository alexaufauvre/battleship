package player

import scala.io.StdIn.readLine
import scala.collection.immutable
import elements._
import game._

object GameUtils{

    // Prompt functions
    def promptShipInitCell(pos: String, numShip: Int): Unit = println("\nEnter the "+ pos + " for the ship " + numShip)
    def promptShipOrientation(): Unit = println("\n(L)eft, (R)ight, (U)p, (D)own")
    def promptGameMode(): Unit = println("\nSelect the game mode:\n\n(1) Player vs Player\n(2) Player vs AI\n(3) AI contest\n(4) General AI contest\n")
    def promptAskAiLevel(): Unit = println("\nSelect the AI level\n\n(1) Easy\n(2) Medium\n(3) Hard\n")
    def promptAskNewGame(): Unit = println("\nDo you want to play another time?\n\n(1) Yes\n(2) No\n")
    def promptGeneralAiContest(): Unit = println("\nThe general contest is over! Please check the result in ai_proof.csv\n")
    def promptBadValues(): Unit = println("\nBad values. Please enter correct values.\n")
    def promptShootCell(pos: String): Unit = println("\nEnter the " + pos + " of the cell you want to shoot")

    /**  Reads the user input
     *
     *  @return the user input
     */
    def getUserInput(): String = readLine.trim.toUpperCase

    /**  Checks if the user Input is an Integer
     *
     *  @param userInput the user input to test
     *  @return true if it's an integer
     */
    def isInt(userInput: String): Boolean = {
        if(userInput.matches("[0-9]+"))
            true
        else false
    }




}
