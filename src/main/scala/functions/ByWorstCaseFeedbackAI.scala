package functions

import scala.math._

object ByWorstCaseFeedbackAI extends AI {
  override def nextGuess(possibles: List[model.PiecesRow], previousGuesses: List[model.PiecesRow]) = {
    val listSize = possibles.head.pieces.size
    val possibleFeedbacks = possibleFeedback(listSize)
    val feedbackByWorst = possibleFeedbacks.sortBy {
      case (corr, corrCol) => 2 * corr + abs(corrCol - listSize / 2)
    }
    var found: Option[model.PiecesRow] = None
    var feedbacks = feedbackByWorst
    while (found == None) {
      val feedbackToTry = feedbacks.head
      found = possibles.map(poss => poss -> trimPossibles(possibles, poss, feedbackToTry._1, feedbackToTry._2).size).filter(_._2 != 0).headOption.map(_._1)
      feedbacks = feedbacks.tail
    }
    found.get
  }
}