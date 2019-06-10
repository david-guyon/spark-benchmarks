import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object SparkWordCount {
  def main(args: Array[String]) {
    if(args.length == 0) {
      System.out.println("Argument <input filename> is missing")
      System.exit(1)
    }
    System.out.println("Running wordcount on " + args(0))
    val start_time = System.currentTimeMillis()
    val sc = new SparkContext(new SparkConf().setAppName("WordCount benchmark"))
    val text = sc.textFile("hdfs:/" + args(0)) 
    val counts = text.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
    //val arr = counts.collect()
    counts.saveAsTextFile("/output")
    //counts.coalesce(1).saveAsTextFile("/output")
    //counts.coalesce(1).saveAsTextFile("/output-" + start_time)
    System.out.println("Output saved in HDFS under /output")
    //System.out.println("Number of words : " + arr.length)
    val duration = System.currentTimeMillis() - start_time
    System.out.println("Total execution time: " + duration)
  }
}
