import org.scalatest._
import game._
import elements._
import player._

class CellTest extends FlatSpec with Matchers {


  "The hitCell method" should "return the cell shot" in {
   val shotCell: Cell = new Cell(3,4)
   val testCell: Cell = new Cell(3,4)
   val wrongCell: Cell = new Cell(7,9)
   Cell.hitCell(shotCell, testCell) should be (Cell(3,4,true))
   Cell.hitCell(shotCell, wrongCell) should be (wrongCell)
   Cell.hitCell(shotCell, testCell).isTouched() should be (true)
   Cell.hitCell(shotCell, wrongCell).isTouched() should be (false)

 }

}
