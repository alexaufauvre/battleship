import org.scalatest._
import game._
import elements._
import player._

class PlayerTest extends FlatSpec with Matchers {


  "The addShips method" should "add every ship in the fleet" in {
    val cell1: Cell = new Cell(1,4, true)
    val cell2: Cell = new Cell(2,4, true)
    val cell3: Cell = new Cell(3,4, true)
    val cell4: Cell = new Cell(4,4)

    val listCells1: List[Cell] = List(cell1, cell2, cell3)
    val listCells2: List[Cell] = List(cell1, cell2, cell3, cell4)

    val ship1: Ship = new Ship(1, 3, listCells1)
    val ship2: Ship = new Ship(2, 4, listCells2)

    val player1: Player = new Player(1, 1, List(), List(), List())

    player1.addShips(List(),1,5,10).length should be (5)
    player1.addShips(List(),4,5,10).length should be (2)

 }

 "The alreadyShot method" should "return if it has already been shot" in {
   val cell1: Cell = new Cell(1,4)
   val cell2: Cell = new Cell(2,4)
   val cell3: Cell = new Cell(3,4)
   val cell4: Cell = new Cell(4,4)
   val shot1: Cell = new Cell(3,4)
   val shot2: Cell = new Cell(1,4)
   val shot3: Cell = new Cell(7,7)


   val hits: List[Cell] = List(cell1, cell2)
   val miss: List[Cell] = List(cell3, cell4)

   val player1: Player = new Player(1, 1, List(), hits, miss)

   player1.alreadyShot(shot1) should be (true)
   player1.alreadyShot(shot2) should be (true)
   player1.alreadyShot(shot3) should be (false)

}

// "The shoot method" should "add shot in hits" in {
//   val cell1: Cell = new Cell(1,4)
//   val cell2: Cell = new Cell(2,4)
//   val cell3: Cell = new Cell(3,4)
//   val cell4: Cell = new Cell(4,4)
//   val cell5: Cell = new Cell(7,8)
//   val cell6: Cell = new Cell(7,9)
//   val cell7: Cell = new Cell(7,10)
//   val shot1: Cell = new Cell(3,4)
//   val shot2: Cell = new Cell(1,4)
//   val shot3: Cell = new Cell(7,7)
//
//   val listCells1: List[Cell] = List(cell1, cell2, cell3, cell4)
//   val listCells2: List[Cell] = List(cell5, cell6, cell7)
//
//   val ship1: Ship = new Ship(1, 3, listCells1)
//   val ship2: Ship = new Ship(2, 4, listCells2)
//
//   val fleet1: List[Ship] = List(ship1)
//   val fleet2: List[Ship] = List(ship2)
//
//
//   val hits1: List[Cell] = List()
//   val miss1: List[Cell] = List()
//   val hits2: List[Cell] = List()
//   val miss2: List[Cell] = List()
//
//   val shooter: Player = new Player(1, 1, fleet1, hits1, miss1)
//   val opponent: Player = new Player(2, 1, fleet2, hits2, miss2)
//
//
//   Player.shoot(shooter, opponent)._1.getHits().length should be (1)
//   Player.shoot(shooter, opponent)._2.getHits().length should be (0)
//
//
//
// }

}
