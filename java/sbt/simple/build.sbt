name := "writeRead"

version := "0.1"

crossPaths := false

autoScalaLibrary := false

// Please make sure that following dependencies have versions corresponding to the ones distributed with
// your DSE version. One way of verifying DSE dependencies versions is inspecting the output of
// `bin/dse spark-classpath` command.
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.1" % "provided",
  "org.apache.spark" %% "spark-sql" % "1.6.1" % "provided",
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.6.0" % "provided"
)

//Your dependencies
//libraryDependencies += "com.github.scopt" %% "scopt" % "3.2.0"
