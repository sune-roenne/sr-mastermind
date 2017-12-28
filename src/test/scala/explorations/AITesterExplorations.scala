package explorations

import functions.AITester
import functions.ByWorstCaseFeedbackAI

object AITesterExplorations {
  def main(args: Array[String]): Unit = {
    val testResult = AITester.executeTest(ByWorstCaseFeedbackAI, 1, 4)
    println(testResult)
    
  }
}