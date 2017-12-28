package explorations

import functions.GameFunctions
import model.PieceColor

object PossibleGenerationExploration {
  def main(args: Array[String]): Unit = {
    val possibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, 4)
    possibles.foreach(println)
    
    println("Number of possibles: " + possibles.distinct.size)
  }
}