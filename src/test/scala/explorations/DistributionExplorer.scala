package explorations

import java.io.File

import functions.GameFunctions
import model.PieceColor
import model.PieceColor._
import model.PiecesRow
import java.io.PrintWriter

object DistributionExplorer {

  val outputFolder = new File("C:/temp/mastermind")
  if (!outputFolder.exists())
    outputFolder.mkdirs()

  def main(args: Array[String]): Unit = {

    val allPossibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, 4)//.filter(_.pieces.distinct.size == 4)
    println("Number of possibles: " + allPossibles.size)
    val correctAnswer = PiecesRow(List(White, Grey, Yellow, Blue))

    var roundNr = 0
    var corrects = 0
    var correctColors = 0
    var currentPossibles = allPossibles

    while (corrects != 4) {
      roundNr += 1
      val possibleDists = currentPossibles.map {
        case poss => {
          val possiblesAtWorst = GameFunctions.trimPossibles(currentPossibles, poss, 0, 2)
          poss -> possiblesAtWorst.size
        }
      }
      val info = ("Dist_round_" + roundNr, possibleDists.sortBy(_._2).map(p => p._1 + ";" + p._2) )
      printInfo(info)
      val chose = possibleDists.sortBy(_._2).head._1
      val correctness = GameFunctions.likeness(chose, correctAnswer)
      corrects = correctness._1
      correctColors = correctness._2
      println("Round : " + roundNr + " - " + chose)
      currentPossibles = GameFunctions.trimPossibles(currentPossibles, chose, corrects, correctColors)

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