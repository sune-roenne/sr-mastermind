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
      var guesses = scala.collection.mutable.ArrayBuffer.empty[PiecesRow] 
      var timings = scala.collection.mutable.ArrayBuffer.empty[Long]
      while(possibles.size > 1) {
        Try{
          val startTime = System.currentTimeMillis
          val nextGuess = ai.nextGuess(possibles, guesses.toList)
          val timeSpent = System.currentTimeMillis - startTime
          guesses += nextGuess
          timings += timeSpent
          val (corr, corrCol) = GameFunctions.likeness(correctAnswer, nextGuess)
          possibles = GameFunctions.trimPossibles(possibles, nextGuess, corr, corrCol)
          possibles match {
            case car :: Nil if car != nextGuess => {guesses += car; timings += 0 }
            case _ => 
          }
        } match {
          case Failure(e) => {possibles = Nil; e.printStackTrace()}
          case _ => 
        }
      }
      TestRoundResult(correctAnswer, guesses.toList, timings.toList)
      
      
    }).toList
  }
  
  case class TestRoundResult(
      correctAnswer : PiecesRow,
      guesses : List[PiecesRow],
      millisecondsUsed : List[Long]
      )
}