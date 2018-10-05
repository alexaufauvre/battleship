package elements

import scala.collection.immutable
import player._
import game._

case class Board(size: Int){

    // def getPlayerNum(): Int = this.player.getNum()
    def getSize(): Int = this.size

    // def grids(player: Player, gridSize: Int): Unit = {
    //         val boatSquares: List[Square] = player.boats.flatMap((b) => b.squares)
    //
    //         println(player.name + " boat grid :")
    //         def displayGrid(x: Int, y: Int, gridSize: Int, boatSquares: List[Square]): Unit = {
    //             //Numbers on top
    //             if(x <= gridSize && y == gridSize+1){
    //                 print(" " + x)
    //                 displayGrid(x+1, y, gridSize, boatSquares)
    //             }
    //             //Last top square
    //             else if(x == gridSize+1 && y == gridSize+1){
    //                 println()
    //                 displayGrid(1, y-1, gridSize, boatSquares)
    //             }
    //             //Normal square
    //             else if(x <= gridSize && y > 0){
    //                 val filteredSquares: List[Square] =  boatSquares.filter((s) => s.x == x && s.y == y)
    //                 if(filteredSquares.length > 0){
    //                     val square: Square = filteredSquares.head
    //                     print("|" + square.state)
    //                 }
    //                 else{
    //                     print("| ")
    //                 }
    //                 displayGrid(x+1, y, gridSize, boatSquares)
    //             }
    //             //Numbers on right
    //             else if(x >= gridSize && y > 0 && y < gridSize+1){
    //                 println("| " + y)
    //                 displayGrid(1, y-1, gridSize, boatSquares)
    //             }
    //         }
    //         displayGrid(1, gridSize+1, gridSize, boatSquares)
    //     }
}
