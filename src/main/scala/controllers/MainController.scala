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
import javafx.scene.chart.PieChart
import functions.ByMostRepresentedColorAI

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
  var txtPossibles: TextArea = null
  @FXML
  var hboxColorDistribution: HBox = null

  var correct = 0
  var correctColor = 0
  var solutionSoFarCircles = List.empty[Circle]
  var nextGuessCircles = List.empty[Circle]
  var colorDistributionPieCharts = List.empty[PieChart]
  var possibles = List.empty[PiecesRow]
  var previousGuesses = scala.collection.mutable.ArrayBuffer.empty[PiecesRow]

  @FXML
  def initialize() {
    solutionSoFarCircles = hboxSolutionSoFar.getChildren.toList collect {
      case circ: Circle => circ
    }
    nextGuessCircles = hboxNextGuess.getChildren.toList collect {
      case circ: Circle => circ
    }
    colorDistributionPieCharts = hboxColorDistribution.getChildren.toList collect {
      case pieChart: PieChart => pieChart
    }

    btnDone.setOnAction(handler {
      case ev => {
        correct = sldCorrect.getValue.toInt
        correctColor = sldCorrectColor.getValue.toInt
        previousGuesses.reverse.headOption.foreach {
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
        fillColorDistributionCharts()
      }
    })
    possibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, 4)
    btnStartGame.setOnAction(handler {
      case ev => {
        previousGuesses.clear()
        solutionSoFarCircles.foreach(_.setFill(Color.LIGHTGREY))
        possibles = GameFunctions.generateAllPossibles(PieceColor.commonColors, 4)
        txtPossibles.setText(possibles.size + "")
        guess
        fillColorDistributionCharts()
      }
    })
  }

  def guess {
    possibles.headOption.foreach {
      case head => {
        val nextGuess = ByMostRepresentedColorAI.nextGuess(possibles, previousGuesses.toList)
        previousGuesses += nextGuess
        nextGuess.pieces.zip(nextGuessCircles).foreach {
          case (gamCol, circ) => circ.setFill(col(gamCol))
        }
      }
    }

  }

  def fillColorDistributionCharts() {
    possibles.headOption.foreach {
      case head => {
        val listSize = head.pieces.length
        val dist = (for (indx <- 0 until listSize) yield {
          val indxCols = possibles.map(poss => poss.pieces(indx))
          indxCols.groupBy(c => c).map(p => p._1 -> p._2.size).toList.sortBy(p => -p._2)
        }).toList
        dist.zip(colorDistributionPieCharts).foreach {
          case (di, pie) => {
            pie.getData.clear()
            di.foreach(d => {
              val dat = new PieChart.Data(d._1.name, d._2)
              pie.getData.add(dat)
              val color = col(d._1)
              val colorCode = toHexString(color)
              dat.getNode.setStyle("-fx-pie-color: " + colorCode + ";") 
            })
          }
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
  
  def toHexString(col : Color) = {
    String.format( "#%02X%02X%02X",
            (col.getRed() * 255 ).toInt.asInstanceOf[Object],
            (col.getGreen() * 255 ).toInt.asInstanceOf[Object],
            (col.getBlue() * 255 ).toInt.asInstanceOf[Object] )
  }
}