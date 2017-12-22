package launch

import model.PiecesRow
import model.PieceColor
import model.PieceColor._
import functions.GameFunctions

object Launcher {
  def main(args: Array[String]): Unit = {
    exploreGame
  }
  
  def exploreGame {
    val allPossibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, 4)
    val correctAnswer = PiecesRow(List(White, Grey, Yellow, Blue))
    
    var roundNr = 0
    var corrects = 0
    var correctColors = 0
    var currentPossibles = allPossibles
    
    def printCurrentKnowledge {
      val listSize = currentPossibles.head.pieces.size
      val indxPos = for(listIndx <- 0 until listSize) yield (listIndx -> currentPossibles.map(li => li.pieces(listIndx)).distinct)
      val knowledge = indxPos.map(en => if(en._2.size == 1) en._2.head.name else "X") toList;
      if(knowledge.exists(en => en != "X"))
        println("Current knowledge: " + knowledge.mkString("\t"))
    }
    
    while(corrects != 4) {
      roundNr += 1
      println(s"Round nr.: $roundNr. Possibles: ${currentPossibles.size}")
      val nextGuess = GameFunctions.bestGuess(currentPossibles)
      print(nextGuess)
      val guessLikeness = GameFunctions.likeness(nextGuess, correctAnswer)
      corrects = guessLikeness._1
      correctColors = guessLikeness._2
      println(s"Guess likeness: corrects: $corrects. Correct colors: $correctColors")
      currentPossibles = GameFunctions.trimPossibles(currentPossibles, nextGuess, corrects, correctColors)
    }
    println("Game completed. Current possibles")
    currentPossibles.foreach(print)
    
    
  }
  
  def explorePossibleFeedback {
    GameFunctions.possibleFeedback(4).foreach(println)
  }
  
  def exploreLikeness {
    val row1 = PiecesRow(List(White, Grey, Grey, Blue))
    val row2 = PiecesRow(List(White, Yellow, Grey, Grey))
    val (corrects, correctColors) = GameFunctions.likeness(row1, row2)
    println(s"Corrects: $corrects, Correct colors: $correctColors")

  }
  
  def print(row : PiecesRow) = println("  guess: " + row.pieces.map(_.name).mkString("\t"))
  
  def exploreGenerations {
    val allPossibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, 4)
    allPossibles.foreach(print)
    println(s"There are ${allPossibles.size} possibilities")
    
  }
  
  
}