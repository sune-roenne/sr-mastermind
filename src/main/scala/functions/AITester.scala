package functions

import scala.util.Random

import model.PieceColor
import model.PiecesRow
import scala.util.Try
import scala.util.Failure

object AITester {
  
  val rand = new Random()
  
  def generateRandomRow(listSize : Int) = {
    val possibleColors = PieceColor.commonColors
    PiecesRow((for(i <- 0 until listSize) yield possibleColors(rand.nextInt(listSize))).toList)
  }
  
  def executeTest(ai : AI, rounds : Int, listSize : Int) = {
    (for(i <- 0 until rounds) yield {
      val correctAnswer = generateRandomRow(listSize)
      var possibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, listSize)
      var guesses : List[PiecesRow] = Nil
      var timings : List[Long] = Nil
      while(possibles.size > 1) {
        Try{
          val startTime = System.currentTimeMillis
          val nextGuess = ai.nextGuess(possibles, guesses)
          val timeSpent = System.currentTimeMillis - startTime
          guesses = (nextGuess :: guesses.reverse).reverse
          timings = (timeSpent :: timings.reverse).reverse
          val (corr, corrCol) = GameFunctions.likeness(correctAnswer, nextGuess)
          possibles = GameFunctions.trimPossibles(possibles, nextGuess, corr, corrCol)
        } match {
          case Failure(_) => possibles = Nil
          case _ => 
        }
      }
      TestRoundResult(correctAnswer, guesses, timings)
      
      
    }).toList
  }
  
  case class TestRoundResult(
      correctAnswer : PiecesRow,
      guesses : List[PiecesRow],
      millisecondsUsed : List[Long]
      )
}