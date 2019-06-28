import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scala.util.Sorting

object Sort {
  // checking <input file> parameter
  def main(args: Array[String]) {
    if(args.length == 0) {
      System.out.println("Argument <input filename> is missing")
      System.exit(1)
    }

    System.out.println("Running sort on file: " + args(0))
    val start_time = System.currentTimeMillis()
    val sc = new SparkContext(new SparkConf())

    // getting input file from HDFS
    val text = sc.textFile("hdfs:/" + args(0))

    // sort phase
    val sort = text.sortBy(f => { f.toInt}, true, 10)
    // val sort = text.sortBy(f => { f.split("\n")(0)}, true, 10)

    // save output to HDFS
    sort.saveAsTextFile("/outfile")               // multiple files
    //sort.coalesce(1).saveAsTextFile("/outfile") // single file
    System.out.println("Output saved in HDFS under /output")

    // print total duration
    val duration = System.currentTimeMillis() - start_time
    System.out.println("Total execution time: " + duration)
  }
}
