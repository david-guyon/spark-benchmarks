## Compile and execute

```
mvn package
spark-submit --master spark://master:7077 --class SparkWordCount target/word-count-1.0-SNAPSHOT.jar
```

## Manage HDFS files

```
hdfs dfs -put input.txt /
hdfs dfs -ls /
```

You should see the file from any node when executing the `ls` command.

In order to be able to re-execute the same benchmark, you'll need to remove output files from previous executions. 

```
hdfs dfs -rm -r /user/root/outfile
```
