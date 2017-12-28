package explorations

import model.PiecesRow
import model.PieceColor._
import functions.GameFunctions
import model.PieceColor

object LikenessExplorer {
  def main(args: Array[String]): Unit = {
     val first = PiecesRow(List(White, Grey, Yellow, Blue))
     val second = PiecesRow(List(Red, Red, Red, Red))
     val allPossibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, 4)
     val afterTrim = GameFunctions.trimPossibles(allPossibles, second, 0, 0)
     println(afterTrim.size)

  }
}