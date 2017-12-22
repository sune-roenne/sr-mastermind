package controllers

import javafx.fxml._
import javafx.scene.control.Button

@FXML
class MainController {
  
  var doneBtn : Button = null
  
  @FXML
  def initialize() {
    println("Initializing")
  }
  
  @FXML
  def test() {
    println("Triggered")
  }
  
}