package launch

import model.PiecesRow
import model.PieceColor

object Launcher {
  def main(args: Array[String]): Unit = {
    val pieceLimit = 4
    
    def generate(possibles : List[PieceColor], current : List[PieceColor]) : List[PiecesRow] = {
      if(current.size == pieceLimit) 
        PiecesRow(current.reverse) :: Nil
      else
        possibles match {
        case Nil => Nil
        case lis => lis.flatMap(col => generate(lis.filter(_.name != col.name), col :: current) )
      }
    }
    
    val allPossibles = generate(PieceColor.commonColors, Nil)
    def print(row : PiecesRow) = println(row.pieces.map(_.name).mkString("\t"))
    allPossibles.foreach(print)
    println(s"There are ${allPossibles.size} possibilities")
    
  }
}