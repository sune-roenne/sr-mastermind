package functions

import scala.util.Random
import model.PiecesRow

object ByMostRepresentedColorAI extends AI{
    override def nextGuess(possibles: List[model.PiecesRow], previousGuesses: List[model.PiecesRow]) = {
    if (previousGuesses.size == 0) possibles(new Random().nextInt(possibles.size))
    else {
      val listSize = possibles.head.pieces.size
      val mostFreq = PiecesRow((for(indx <- 0 until listSize) yield {
        possibles.map(_.pieces(indx)).groupBy(col => col).map(p => p._1 -> p._2.size).maxBy(_._2)._1
      }).toList)
      possibles.map(poss => poss -> likeness(poss, mostFreq)).map(p => p._1 -> (p._2._1 * 2 + p._2._2)).maxBy(_._2)._1

    }
  }

}