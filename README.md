Prerequisites
You need to get sbt, an interactive scala tool, according to the following instructions 	
	→ https://www.scala-sbt.org/1.0/docs/Setup.html
Installation
Open a terminal, clone this repository and move in the directory:
		 git clone https://github.com/alexaufauvre/battleship
		 cd battleship/battleship/
Running
Once you have it, you should be able to run the project. Launch the sbt (simple build tool):
	 sbt
You are now using the sbt shell. The last step is to compile and run:
	 compile
	 run
How to play
When you run the program, a menu is displayed and you have to chose among 4 game modes:
Player vs Player: a game mode in which two human players compete.


Player vs AI: a game mode in which a human player compete against an Artificial Intelligence (AI)I. Three levels of difficulty are available: Easy, Medium and Hard.


AI contest: a game mode in which you chose two AI to play 100 games against each other. At the end, the total amount of wins is displayed for each AI.


General AI contest: a game mode in which each level of AI plays 100 games against each other. At the end, the total amount of wins for each contest is available in a csv file named ai_proof.csv.
