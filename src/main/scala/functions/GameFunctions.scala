package functions

import model.PiecesRow
import model.PieceColor
import scala.math.min

object GameFunctions {

  def generateAllPossibles(colors: List[PieceColor], pieceLimit: Int) = {
    def generate(possibles: List[PieceColor], current: List[PieceColor]): List[PiecesRow] = {
      if (current.size == pieceLimit)
        PiecesRow(current.reverse) :: Nil
      else
        possibles match {
          case Nil => Nil
          case lis => lis.flatMap(col => generate(lis.filter(_.name != col.name), col :: current))
        }
    }
    generate(colors, Nil)
  }

  def trimPossibles(possibles: List[PiecesRow], guess: PiecesRow, corrects: Int, correctColors: Int) = {
    val possibleLikeness = possibles.map(poss => poss -> likeness(guess, poss))
    possibleLikeness.filter(p => p._2 == (corrects, correctColors)).map(_._1)
  }

  def likeness(firstRow: PiecesRow, secondRow: PiecesRow) = {
    val corrects = firstRow.pieces.zip(secondRow.pieces).zipWithIndex collect {
      case ((inFirst, inSecond), indx) if inFirst == inSecond => indx
    };
    def removeCorrects(lis: List[PieceColor]) = lis.zipWithIndex.filter(p => !corrects.contains(p._2)).map(_._1)
    val (firstWithoutCorrect, secondWithoutCorrect) = (removeCorrects(firstRow.pieces), removeCorrects(secondRow.pieces))
    val noOfCorrectCols = firstWithoutCorrect.groupBy(en => en).map(en => en._1 -> en._2.size).map {
      case (col, occurences) => min(occurences, secondWithoutCorrect.filter(_ == col).size)
    } sum;
    (corrects.size, noOfCorrectCols)
  }

  def possibleFeedback(listSize: Int) =
    for (
      corrects <- 0 to listSize;
      correctColors <- 0 to listSize - corrects
    ) yield (corrects, correctColors)

  def bestGuess(possibles: List[PiecesRow]) = {
    val listSize = possibles.headOption.map(_.pieces.size).getOrElse(0)
    val feedbacks = possibleFeedback(listSize)
    var count = 0
    val possibleScores = possibles.map {
      case poss => {
        count += 1
        /*if(count % 100 == 0)
          println("Count: " + count)*/
        val otherPossibles = possibles.filter(_ != poss)
        val byFeedbacks = feedbacks.map(feedback => trimPossibles(otherPossibles, poss, feedback._1, feedback._2))
        (poss, byFeedbacks.map(_.size).max)
      }
    }
    val minScore = possibleScores.map(_._2).min
    possibleScores.filter(_._2 == minScore).map(_._1).head
  }

}