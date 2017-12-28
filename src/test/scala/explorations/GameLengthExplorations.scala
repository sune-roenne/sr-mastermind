package explorations

import model.PiecesRow
import functions.GameFunctions
import model.PieceColor
import model.PieceColor._
import java.io.PrintWriter
import java.io.File

object GameLengthExplorations {
  
  val outputFolder = new File("C:/temp/mastermind")
  if (!outputFolder.exists())
    outputFolder.mkdirs()
  
  
  def main(args: Array[String]): Unit = {
    val allPossibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, 4)
    val correctAnswer = PiecesRow(List(White, Grey, Yellow, Blue))

    var roundNr = 0
    var corrects = 0
    var correctColors = 0
    var currentPossibles = allPossibles

    while (corrects != 4) {
      roundNr += 1
      val possibleDict = currentPossibles.map {
        case poss => {
          val likeness = GameFunctions.likeness(poss, correctAnswer)
          val possFiltered = GameFunctions.trimPossibles(currentPossibles, poss, likeness._1, likeness._2)
          (poss, possFiltered.size,likeness._1, likeness._2)
        }
      }
      val infos = ("Gamelength_" + roundNr, possibleDict.sortBy(_._2).map(pos => pos._1 + ";" + pos._2 + ";" + pos._3 + ";" + pos._4))
      printInfo(infos)
      val nextGuess = possibleDict.minBy(_._2)._1
      val likeness = GameFunctions.likeness(nextGuess, correctAnswer)
      corrects = likeness._1
      correctColors = likeness._2
      currentPossibles = GameFunctions.trimPossibles(currentPossibles, nextGuess, corrects, correctColors)
      println("Round: " + roundNr + " - " + nextGuess + " " + likeness)
    }

  }
  
  def printInfo(infos: (String, List[String])) {
    val outputFile = new File(outputFolder, infos._1 + ".csv")
    if (outputFile.exists())
      outputFile.delete()
    outputFile.createNewFile()
    val writer = new PrintWriter(outputFile)
    writer.write(infos._2.mkString("\n"))
    writer.close()
  }  
  
}