import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object SparkWordCount {
  def main(args: Array[String]) {
    val sc = new SparkContext(new SparkConf().setAppName("WordCount benchmark"))
    val text = sc.textFile("hdfs:/input.txt") 
    val counts = text.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
    counts.saveAsTextFile("outfile")
    System.out.println("OK");
  }
}
