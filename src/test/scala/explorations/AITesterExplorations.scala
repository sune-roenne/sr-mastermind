package explorations

import functions.AITester
import functions.ByWorstCaseFeedbackAI
import java.io.File
import java.io.PrintWriter

object AITesterExplorations {
  val outputFolder = new File("""c:\temp\mastermind""")
  if(!outputFolder.exists)
    outputFolder.mkdirs()
  
  def main(args: Array[String]): Unit = {
    val underTest = ByWorstCaseFeedbackAI
    val underTestName = underTest.getClass.getName.replaceAll("(\\$)|(functions\\.)", "")
    println(underTestName)
    val testResult = AITester.executeTest(ByWorstCaseFeedbackAI, 50, 4)
    val outputFile = new File(outputFolder, "exectest_" + underTestName + "_" + System.currentTimeMillis()  + ".csv")
    outputFile.createNewFile()
    val writer = new PrintWriter(outputFile)
    writer.write("Run;Answer;CorrectAnswerFound;NoOfGuesses;TotalTime;Guesses\r\n")
    writer.write(
       testResult.zipWithIndex.map({ case (res,indx) => (indx + 1) + ";" +  res.correctAnswer + ";" + res.guesses.endsWith(List(res.correctAnswer)) + ";" + res.guesses.size + ";" + (res.millisecondsUsed.sum/1000L) + ";" + res.guesses.mkString(", ") }).mkString("\r\n")
    )
    
    writer.close()
    
    
    
  }
  
  
}