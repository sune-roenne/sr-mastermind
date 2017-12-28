package explorations

import functions.GameFunctions
import scala.math._

object ByWorstAIExplorations {
  def main(args: Array[String]): Unit = {
    val listSize = 4
    val possibleFeedbacks = GameFunctions.possibleFeedback(listSize)
    val feedbackByWorst = possibleFeedbacks.sortBy {
      case (corr, corrCol) =>  2 * corr + abs(corrCol - listSize / 2)
    }
    feedbackByWorst.foreach(println)

  }
}