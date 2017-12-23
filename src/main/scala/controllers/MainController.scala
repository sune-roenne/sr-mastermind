package controllers

import scala.collection.JavaConversions._

import functions.GameFunctions
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml._
import javafx.scene.control.Button
import javafx.scene.control.Slider
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import model.PieceColor
import model.PiecesRow
import javafx.scene.control.TextArea

@FXML
class MainController {

  @FXML
  var btnDone: Button = null
  @FXML
  var btnStartGame: Button = null
  @FXML
  var hboxSolutionSoFar: HBox = null
  @FXML
  var hboxNextGuess: HBox = null
  @FXML
  var sldCorrect: Slider = null
  @FXML
  var sldCorrectColor: Slider = null
  @FXML
  var txtPossibles : TextArea = null

  var correct = 0
  var correctColor = 0
  var solutionSoFarCircles = List.empty[Circle]
  var nextGuessCircles = List.empty[Circle]
  var possibles = List.empty[PiecesRow]
  var lastGuess: Option[PiecesRow] = None

  @FXML
  def initialize() {
    solutionSoFarCircles = hboxSolutionSoFar.getChildren.toList collect {
      case circ: Circle => circ
    }
    nextGuessCircles = hboxNextGuess.getChildren.toList collect {
      case circ: Circle => circ
    }

    btnDone.setOnAction(handler {
      case ev => {
        correct = sldCorrect.getValue.toInt
        correctColor = sldCorrectColor.getValue.toInt
        lastGuess.foreach {
          case guess => {
            possibles = GameFunctions.trimPossibles(possibles, guess, correct, correctColor)
            txtPossibles.setText(possibles.size + "")
            if (possibles.size == 1)
              possibles.head.pieces.zip(solutionSoFarCircles).foreach {
                case (color, circ) => circ.setFill(col(color))
              }
          }
        }
        guess
      }
    })
    possibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, 4)
    btnStartGame.setOnAction(handler {
      case ev => {
        possibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, 4)
        txtPossibles.setText(possibles.size + "")
        guess
      }
    })
  }

  def guess {
    possibles.headOption.foreach {
      case head => {
        val nextGuess = head //GameFunctions.bestGuess(possibles)
        lastGuess = Some(nextGuess)
        nextGuess.pieces.zip(nextGuessCircles).foreach {
          case (gamCol, circ) => circ.setFill(col(gamCol))
        }
      }
    }

  }

  def handler(handl: (ActionEvent => Unit)) = new EventHandler[ActionEvent] {
    override def handle(ev: ActionEvent) { handl(ev) }
  }

  def col(pc: PieceColor) = pc match {
    case PieceColor.Blue   => Color.BLUE
    case PieceColor.Green  => Color.GREEN
    case PieceColor.Grey   => Color.GRAY
    case PieceColor.Orange => Color.ORANGE
    case PieceColor.Purple => Color.PURPLE
    case PieceColor.Red    => Color.RED
    case PieceColor.White  => Color.WHITE
    case PieceColor.Yellow => Color.YELLOW
    case _                 => Color.BLACK

  }
}