import org.scalatest._
import game._
import elements._
import player._

class GameTest extends FlatSpec with Matchers {


  "The getBoardCells method" should "create the right cells" in {
      val cell1: Cell = new Cell(1,4)
      val cell2: Cell = new Cell(2,4)
      val cell3: Cell = new Cell(3,4)
      val cell4: Cell = new Cell(7,4)

      val boardSize: Int = 5

      Game.getBoardCells(1, 1, boardSize, List()).contains(cell3) should be (true)
      Game.getBoardCells(1, 1, boardSize, List()).contains(cell4) should be (false)

 }

 it should "create the right number of cells" in {

     val boardSize: Int = 5

     Game.getBoardCells(1, 1, boardSize, List()).length should be (25)

 }

 "The initializationGame method" should "create a complete fleet" in {

     val player1 = new Player(1, 2, List(), List(), List())
     val player2 = new Player(2, 2, List(), List(), List())

     val boardSize: Int = 5

     Game.initializationGame(player1, player2)._1.getFleet().length should be (5)
     Game.initializationGame(player1, player2)._2.getFleet().length should be (5)

}



}
