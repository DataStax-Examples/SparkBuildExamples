# Example projects for using DSE Analytics

These are template projects that illustrate how to build Spark Application written in Java or Scala with 
Maven, SBT or Gradle. Navigate to project that implements simple write-to-/read-from-Cassandra application 
with language and build tool of your choice.

## Dependencies

Compiling Spark Applications depends on `Apache Spark` and optionally on `Spark Cassandra Connector` 
jars. Projects `auto_classpath` and `simple` show two different ways of supplying these dependencies.
Both projects are built and executed with similar commands.

### DSE installed

If DSE is installed on computer where Spark Application is going to be built, you can use `auto_classpath` 
project which will automatically use DSE jar dependencies. All you need to do is set `DSE_HOME` environment 
variable so that SBT (or Gradle) will know where to look for dependency jars. On Linux machines 
use following command to set `DSE_HOME` (substitute <path to DSE> with location such that 
`$DSE_HOME/bin/dse` is a valid command):

`export DSE_HOME=<path to DSE>`

### DSE not installed

If there is no DSE installed on computer where Spark Application is going to be built, use `simple` 
project where all dependencies have to be specified explicitly in `build.sbt` file. Please mind 
dependencies versions, these should match the ones in execution environment.

### Additional dependencies

Prepared projects use extra plugins so additional dependencies can be included with your 
application jar. All you need to do is add dependencies in build configuration file in commented section 
called 'Your dependencies'.

## Building & running

### Sbt

`sbt clean assembly`
`dse spark-submit --class com.datastax.spark.example.scala.WriteRead target/scala-2.10/writeRead-assembly-0.1.jar`

### Gradle

`gradle clean build`
`dse spark-submit --class com.datastax.spark.example.scala.WriteRead build/libs/writeRead-0.1.jar`

### Maven

`mvn clean package`
`dse spark-submit --class com.datastax.spark.example.scala.WriteRead target/writeRead-0.1-dep.jar`



