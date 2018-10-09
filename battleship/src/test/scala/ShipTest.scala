import org.scalatest._
import game._
import elements._
import player._

class ShipTest extends FlatSpec with Matchers {


  "The isSunk method" should "tell if the ship is sunk" in {
   val cell1: Cell = new Cell(1,4, true)
   val cell2: Cell = new Cell(2,4, true)
   val cell3: Cell = new Cell(3,4, true)
   val cell4: Cell = new Cell(4,4)
   val listCells1: List[Cell] = List(cell1, cell2, cell3)
   val listCells2: List[Cell] = List(cell1, cell2, cell3, cell4)
   val ship1: Ship = new Ship(1, 3, listCells1)
   val ship2: Ship = new Ship(2, 4, listCells2)

   Ship.isSunk(ship1) should be (true)
   Ship.isSunk(ship2) should be (false)

 }

}
