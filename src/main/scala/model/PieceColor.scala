package model

case class PieceColor (
    name : String
)

object PieceColor {
  val Red = PieceColor("Red")
  val Blue = PieceColor("Blue")
  val Green = PieceColor("Green")
  val Yellow = PieceColor("Yellow")
  val Orange = PieceColor("Orange")
  val Purple = PieceColor("Purple")
  val Grey = PieceColor("Grey")
  val White = PieceColor("White")
  
  
  val commonColors = List(
      Red,
      Blue,
      Green,
      Yellow,
      Orange,
      Purple,
      Grey,
      White
      )
}
