package model

case class PieceColor (
    name : String
)

object PieceColor {
  val commonColors = List(
      PieceColor("Red"),
      PieceColor("Blue"),
      PieceColor("Green"),
      PieceColor("Yellow"),
      PieceColor("Orange"),
      PieceColor("Purple"),
      PieceColor("Grey"),
      PieceColor("White")
      )
}
