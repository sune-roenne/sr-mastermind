package functions

import model.PiecesRow
import model.PieceColor

trait AI {
  def generateAllPossibles(colors: List[PieceColor], pieceLimit: Int) = GameFunctions.generateAllPossibles(colors, pieceLimit)

  def trimPossibles(possibles: List[PiecesRow], guess: PiecesRow, corrects: Int, correctColors: Int) = GameFunctions.trimPossibles(possibles, guess, corrects, correctColors)

  def likeness(firstRow: PiecesRow, secondRow: PiecesRow) = GameFunctions.likeness(firstRow, secondRow)
  
  def possibleFeedback(listSize: Int) = GameFunctions.possibleFeedback(listSize)
  
  def nextGuess(possibles : List[PiecesRow], previousGuesses : List[PiecesRow]) : PiecesRow


}