name := "writeRead"

version := "0.1"

scalaVersion := "2.10.6"

// Please make sure that following dependencies have versions corresponding to the ones in your cluster.
// Note that spark-hive and spark-cassandra-connector should be provided with '--packages' flag to
// spark-submit command.
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.2" % "provided",
  "org.apache.spark" %% "spark-sql" % "1.6.2" % "provided",
  "org.apache.spark" %% "spark-hive" % "1.6.2" % "provided",
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.6.0" % "provided"
)

//Your dependencies
//libraryDependencies += "com.github.scopt" %% "scopt" % "3.2.0"
