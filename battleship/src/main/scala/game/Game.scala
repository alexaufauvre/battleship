package game

import java.io.{BufferedWriter, FileWriter}
import au.com.bytecode.opencsv.CSVWriter
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
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

        // Ask the player if they want to play again
        def askForNewGame(): Int = {
            val answers: List[Int] = List(1, 2)

            GameUtils.promptAskNewGame()
            val newGame: Int = GameUtils.getUserInput().toInt


            if (answers.contains(newGame) == false){
                print("\nAnswer not valid. Please try again.\n")
                this.askForNewGame()
            }

            return newGame
        }

        //Ask the user which mode he wants to play to know the aiLevel of each player.
        def gameMode(): (Int, Int) = {
            val levels: List[Int] = List(0, 1, 2, 3)
            val modes: List[Int] = List(1, 2, 3, 4)

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

                case 4 => {
                    // TO DO : Fix the return in this case to end the program.
                    generalAiContest()
                    (4,4)
                }


            }

    }


        def initializationGame(player1: Player, player2: Player): (Player, Player) = {
            val levelPlayer1: Int = player1.getAiLevel()
            val levelPlayer2: Int = player2.getAiLevel()


            //Create Player 1's fleet
            println("\nPlayer 1 \n")
            //addShips(fleet, number of the first ship, size of the first ship, size of the board)
            val fleetPlayer1 = player1.addShips(List(),4,2,10)

            //Create Player 2's fleet
            println("\nPlayer 2 \n")
            //addShips(fleet, number of the first ship, size of the first ship, size of the board)
            val fleetPlayer2 = player2.addShips(List(),4,2,10)

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

            //If the player is human, we ask if they want to play again
            if (levelPlayer1 == 0){

                val newGame: Int = askForNewGame()

                newGame match {
                    case 1 => {

                        val newPlayer1: Player = winner.copy(fleet=List(), hits=List(), miss=List())
                        val newPlayer2: Player = player2Updated.copy(fleet=List(), hits=List(), miss=List())
                        playTurn(newPlayer2, newPlayer1)

                    }

                    case 2 => (winner, player2Updated)
                }

            }

        else (winner, player2Updated)


        }
    }

    // Runs a loop of games between 2 AI. Returns the two players with their score updated.
    def aiContest(player1: Player, player2: Player, sizeContest: Int): (Player, Player) = {

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
            val levelPlayer1: Int = player1.getAiLevel()
            val levelPlayer2: Int = player2.getAiLevel()
            val winsPlayer1: Int = player1.getWins()
            val winsPlayer2: Int = player2.getWins()

            println("AI Level : " + levelPlayer1 + ", Wins : " + winsPlayer1)
            println("AI Level : " + levelPlayer2 + ", Wins : " + winsPlayer2)

            (player1, player2)
        }
    }

    def generalAiContest(): Unit = {
        val sizeContest: Int = this.sizeContest

        // Create the 3 AI players
        val easyAI: Player = new Player(1, 1, List(), List(), List())
        val mediumAI: Player = new Player(2, 2, List(), List(), List())
        val hardAI: Player = new Player(3, 3, List(), List(), List())

        // Do the contests between the players
        val easyVsMedium: (Player, Player) = aiContest(easyAI, mediumAI, sizeContest)
        val easyVsHard: (Player, Player) = aiContest(easyAI, hardAI, sizeContest)
        val mediumVsHard: (Player, Player) = aiContest(mediumAI, hardAI, sizeContest)

        // Preparing the lines for writing in the CSV file
        val newLine1: String =  easyVsMedium._1.getAiLevel() + ";" + easyVsMedium._1.getWins() + ";" + easyVsMedium._2.getAiLevel() + ";" + easyVsMedium._2.getWins() + "\n"
        val newLine2: String =  easyVsHard._1.getAiLevel() + ";" + easyVsHard._1.getWins() + ";" + easyVsHard._2.getAiLevel() + ";" + easyVsHard._2.getWins() + "\n"
        val newLine3: String =  mediumVsHard._1.getAiLevel() + ";" + mediumVsHard._1.getWins() + ";" + mediumVsHard._2.getAiLevel() + ";" + mediumVsHard._2.getWins() + "\n"

        GameUtils.promptGeneralAiContest()

        writeCSV(newLine1, newLine2, newLine3)



    }

    def writeCSV(line1: String, line2: String, line3: String): Unit = {
        val outputFile = new BufferedWriter(new FileWriter("../ai_proof.csv"))
        val csvWriter = new CSVWriter(outputFile)

        outputFile.write("AI Name; score; AI Name2; score2\n")
        outputFile.write(line1)
        outputFile.write(line2)
        outputFile.write(line3)

        outputFile.close();
    }
        // TO DO : Game settings //

        def main(): Unit = {

        val gameMode: (Int, Int) = this.gameMode()
        val levelPlayer1: Int = gameMode._1
        val levelPlayer2: Int = gameMode._2
        val player1 = new Player(1, levelPlayer1, List(), List(), List())
        val player2 = new Player(2, levelPlayer2, List(), List(), List())

        if (levelPlayer1 == 4 && levelPlayer2 == 4){

        }


        // If it's a contest
        else if (levelPlayer1 != 0 && levelPlayer1 <= 3 && levelPlayer2 != 0 && levelPlayer2 <= 3){
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
