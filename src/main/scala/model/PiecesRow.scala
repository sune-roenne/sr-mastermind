package model

case class PiecesRow (
    pieces : List[PieceColor]
    
    ) {
  override def toString = pieces.map(p => p.name).mkString("(", "," , ")")
}