package game

import scala.collection.immutable
import player._
import elements._

object Game extends App {

        val boardSize: Int = 10
        val sizeContest: Int = 100

        def getBoardCells(x: Int, y: Int, boardSize: Int, cells: List[Cell]): List[Cell] = {

                if(x<=boardSize){
                    if(y<boardSize){
                        val currentCell: Cell = new Cell(x, y)
                        val updatedCells: List[Cell] = currentCell :: cells
                        getBoardCells(x, y+1, boardSize, updatedCells)
                    }
                    else{
                        val currentCell: Cell = new Cell(x, y)
                        val updatedCells: List[Cell] = currentCell :: cells
                        getBoardCells(x+1, 1, boardSize, updatedCells)
                    }
                }


            else cells

        }

        //Ask the user which mode he wants to play to know the aiLevel of each player.
        def gameMode(): (Int, Int) = {
            val levels: List[Int] = List(0, 1, 2, 3)
            val modes: List[Int] = List(1, 2, 3)

            // Get the game mode
            GameUtils.promptGameMode()
            val gameMode: Int = GameUtils.getUserInput().toInt
            if (modes.contains(gameMode) == false){
                print("\nGame mode not valid. Please try again.\n")
                this.gameMode()
            }

            gameMode match{
                case 1 => {
                    return (0,0)
                }
                case 2 => {
                    GameUtils.promptAskAiLevel()
                    val aiLevel: Int = GameUtils.getUserInput().toInt
                    if (levels.contains(aiLevel) == false){
                        print("\nLevel not valid. Please try again.\n")
                        this.gameMode()
                    }
                    else {
                        return (0, aiLevel)
                    }
                }

                    //add a case 3 for the contest, requiring 2 AI levels.
                case 3 => {
                    print("\nPlayer 1\n")
                    GameUtils.promptAskAiLevel()
                    val aiLevelPlayer1: Int = GameUtils.getUserInput().toInt

                    print("\nPlayer 2\n")
                    GameUtils.promptAskAiLevel()
                    val aiLevelPlayer2: Int = GameUtils.getUserInput().toInt

                    return (aiLevelPlayer1, aiLevelPlayer2)


                }


            }

    }


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

        }


        // Play one round in the game
        def playTurn(player1: Player, player2: Player): (Player, Player) = {

            val hitsP1: List[Cell] = player1.getHits()
            val missP1: List[Cell] = player1.getMiss()
            val hitsP2: List[Cell] = player2.getHits()
            val missP2: List[Cell] = player2.getMiss()
            val boardSize: Int = this.boardSize
            val fleetPlayer1: List[Ship] = player1.getFleet()
            val levelPlayer1: Int = player1.getAiLevel()
            val levelPlayer2: Int = player2.getAiLevel()

            // If there's at least one human player, the boards are displayed
            if (levelPlayer1 == 0){

                print("\n\n---------------\n\n" + player1.getName() + "'s turn\n\n")
                // Render players' hit board
                print("Hit board : \n")
                player1.renderBoard(1, 1, boardSize, List(), hitsP1, missP1)
                print("\n")

                // Render players' own board
                print("Your board : \n")
                player1.renderBoard(1, 1, boardSize, fleetPlayer1, hitsP2, missP2)
                print("\n")
            }


            // Player 1 shoots on Player 2
            val playersUpdated = Player.shoot(player1, player2)

            val player1Updated: Player = playersUpdated._1

            val player2Updated: Player = playersUpdated._2

            if (player2Updated.fleetIsSunk() == false) {

            // New turn, the other player becomes the shooter
            playTurn(player2Updated, player1Updated)
        }

        else {
            print("\nCongratulations, " + player1Updated.getName() + " won!\n")
            val winsUpdated: Int = player1Updated.getWins()+1
            val winner: Player = player1Updated.copy(wins=winsUpdated)

            (winner, player2Updated)

        }
    }

    def aiContest(player1: Player, player2: Player, sizeContest: Int): Unit = {

        // If the contest isn't over
        if (sizeContest != 0){

            // Reinitialize the fleets of each player
            val reinitFleetPlayer1: Player = player1.copy(fleet=List(), hits=List(), miss=List())
            val reinitFleetPlayer2: Player = player2.copy(fleet=List(), hits=List(), miss=List())

            // Get the fleet for each player
            val initPlayers: (Player, Player) = initializationGame(reinitFleetPlayer1, reinitFleetPlayer2)

            val newPlayers: (Player, Player) = playTurn(initPlayers._1, initPlayers._2)

            aiContest(newPlayers._2, newPlayers._1, sizeContest-1)
        }

        else {
            println("AI Level : " + player1.getAiLevel() + ", Wins : " + player1.getWins())
            println("AI Level : " + player2.getAiLevel() + ", Wins : " + player2.getWins())
        }
    }
        // TO DO : Game settings //

        def main(): Unit = {

        val gameMode: (Int, Int) = this.gameMode()
        val levelPlayer1: Int = gameMode._1
        val levelPlayer2: Int = gameMode._2
        val player1 = new Player(1, levelPlayer1, List(), List(), List())
        val player2 = new Player(2, levelPlayer2, List(), List(), List())

        // If it's a contest
        if (levelPlayer1 != 0 && levelPlayer2 != 0){
            aiContest(player1, player2, this.sizeContest)
        }
        else{
            // Get the fleet for each player
            val initPlayers: (Player, Player) = initializationGame(player1, player2)

            playTurn(initPlayers._1, initPlayers._2)
        }

    }

    this.main()



}
