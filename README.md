# Example projects for using DSE Analytics

These are template projects that illustrate how to build Spark Application written in Java or Scala with 
Maven, SBT or Gradle. Navigate to project that implements simple write-to-/read-from-Cassandra 
application with language and build tool of your choice.

## Dependencies

Compiling Spark Applications depends on `Apache Spark` and optionally on `Spark Cassandra Connector` 
jars. Projects `dse` and `oss` show two different ways of supplying these dependencies. 
Both projects are built and executed with similar commands.

### DSE 

If you are planning to execute your Spark Application on a DSE cluster, you can use `dse` project 
template which will automatically download (and use during compilation) all jars available in DSE cluster. 
Please mind DSE version specified in build file, it should should match the one in your cluster.

### OSS

If you are planning to execute your Spark Application against Open Source Apache Spark and Open Source 
Apache Cassandra, use `oss` project template where all dependencies have to be specified manually in 
build files. Please mind dependencies versions, these should match the ones in your execution environment.

For additional info about version compatibility please refer to 
[Version Compatibility Table](https://github.com/datastax/spark-cassandra-connector#version-compatibility)

### Additional dependencies

Prepared projects use extra plugins so additional dependencies can be included with your 
application's jar. All you need to do is add dependencies in build configuration file.

## Building & running

### Sbt

```
sbt clean assembly
dse spark-submit --class com.datastax.spark.example.scala.WriteRead target/scala-2.10/writeRead-assembly-0.1.jar
```

### Gradle

```
gradle clean shadowJar
dse spark-submit --class com.datastax.spark.example.scala.WriteRead build/libs/writeRead-0.1.jar
```

### Maven

```
mvn clean package
dse spark-submit --class com.datastax.spark.example.scala.WriteRead target/writeRead-0.1-dep.jar
```



