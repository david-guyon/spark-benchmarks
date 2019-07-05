import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object WordCount {
  def main(args: Array[String]) {
    // checking <input file> parameter
    if(args.length == 0) {
      System.out.println("Argument <input file> is missing")
      System.exit(1)
    }

    System.out.println("Running wordcount on " + args(0))
    val start_time = System.currentTimeMillis()
    val sc = new SparkContext(new SparkConf())

    // getting input file from HDFS
    val text = sc.textFile("hdfs:/" + args(0) + "/part-*") 

    // map-reduce phase
    val counts = text.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    // print the number of words
    //val arr = counts.collect()
    //System.out.println("Number of words : " + arr.length)

    // save output to HDFS
    counts.saveAsTextFile("/output")                // multiple files
    //counts.coalesce(1).saveAsTextFile("/output")  // single file
    System.out.println("Output saved in HDFS under /output")

    // print total duration
    val duration = System.currentTimeMillis() - start_time
    System.out.println("Total execution time: " + duration)
  }
}
