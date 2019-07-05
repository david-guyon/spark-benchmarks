import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark._
import org.apache.spark.rdd.{RDD, ShuffledRDD}
import scala.util.Sorting
import scala.reflect.ClassTag
import java.util.Date
import java.sql.Timestamp

object Sort {
  implicit def rddToHashedRDDFunctions[K : Ordering : ClassTag, V: ClassTag]
        (rdd: RDD[(K, V)]) = new ConfigurableOrderedRDDFunctions[K, V, (K, V)](rdd)

  def main(args: Array[String]) {
    // checking <input file> parameter
    if(args.length == 0) {
      System.out.println("Argument <input filename> is missing")
      System.exit(1)
    }

    System.out.println("Running HiBench-like sort on file: " + args(0))
    val start_time = System.currentTimeMillis()
    val sc = new SparkContext(new SparkConf())

    val parallel = sc.getConf.getInt("spark.default.parallelism", sc.defaultParallelism)
    System.out.println("default parallel: " + parallel.toString)

    // getting input file from HDFS
    val text = sc.textFile("hdfs:/" + args(0) + "/part-*")

    // HiBench-like sort phase
    val partitioner = new HashPartitioner(partitions = parallel/2)
    val sort = text.flatMap(line => line.split(" ")).map((_, 1)).sortByKeyWithPartitioner(partitioner = partitioner).map(_._1)

    val io = new IOCommon(sc)
    io.save("/output", sort)

    // save output to HDFS
    //sort.saveAsTextFile("/output")                // multiple files
    //sort.coalesce(1).saveAsTextFile("/output")  // single file
    System.out.println("Output saved in HDFS under /output")

    // print total duration
    val duration = System.currentTimeMillis() - start_time
    System.out.println("Total execution time: " + duration)
  }
}
