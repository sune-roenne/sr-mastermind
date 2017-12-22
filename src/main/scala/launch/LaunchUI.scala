package launch

import javafx.application.Application
import javafx.stage.Stage
import javafx.fxml.FXMLLoader
import javafx.scene.Scene

object LaunchUI{
  
  def main(args: Array[String]): Unit = {
    Application.launch( classOf[LaunchUI], args : _ * )
    
  }
  
  
}

class LaunchUI extends Application {
  override def start(stage : Stage) {
    stage.setTitle("Familien Rønnes Mastermind AI")
    val contents = FXMLLoader.load(this.getClass().getClassLoader.getResource("main-jdk8.fxml")) : javafx.scene.layout.AnchorPane
    val scene = new Scene(contents)
    stage.setScene(scene)
    stage.show()
  }
  
}