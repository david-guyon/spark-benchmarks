```
mvn package
spark-submit --master spark://master:7077 --class SparkWordCount target/word-count-1.0-SNAPSHOT.jar
```