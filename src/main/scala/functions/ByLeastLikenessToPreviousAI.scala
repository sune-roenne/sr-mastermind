package functions

import scala.util.Random

object ByLeastLikenessToPreviousAI extends AI {
  override def nextGuess(possibles: List[model.PiecesRow], previousGuesses: List[model.PiecesRow]) = {
    if (previousGuesses.size == 0) possibles(new Random().nextInt(possibles.size))
    else {
      val likenessMap = possibles.flatMap ({
        case poss => {
          previousGuesses.map(prev => poss -> likeness(prev, poss))
        }
      }).groupBy(_._1).map(p => p._1 -> (2* p._2.map(_._2._1).sum + p._2.map(_._2._2).sum)).toList
      likenessMap.minBy(_._2)._1

    }
  }

}