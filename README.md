# Spark Benchmarks

This repository provides two well-known benchmarks: _wordcount_ and _sort_. They can be easily compiled with _maven_ and executed on Spark with the `spark-submit` command.

## Wordcount

To compile the wordcount  benchmark, move to the _wordcount_ folder and execute `mvn package`. If everything went well, you should see _word-count-1.0-SNAPSHOT.jar_ in the _target_ folder.

This benchmark is using HDFS to get its input file. Therefore, you need to upload the input file in HDFS with `hdfs dfs -put input.txt /`. Of course, you can change _input.txt_ with whatever you'd like.

Hereafter a couple of HDFS commands you can use to check your input file:
```
hdfs dfs -ls /
hdfs dfs -du -h /input.txt
```

Finally, you can execute the benchmark with the following command:
```
spark-submit --master spark://master:7077 --name "wordcount" --class SparkWordCount target/word-count-1.0-SNAPSHOT.jar
```

At the end of the execution, the ouput files are located within HDFS. If you'd like to execute the benchmark again, first you will need to remove these files:
```
hdfs dfs -rm -r /outfile
```
