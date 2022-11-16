name := "writeRead"
version := "0.1"

crossPaths := false

autoScalaLibrary := false

scalaVersion := "2.11.8"

val sparkVersion = "2.2.2"
val connectorVersion = "2.0.10"

// Please make sure that following dependencies have versions corresponding to the ones in your cluster.
// Note that spark-cassandra-connector should be provided with '--packages' flag to spark-submit command.
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
  "com.datastax.spark" %% "spark-cassandra-connector" % connectorVersion % "provided"
)

//Your dependencies
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"
//libraryDependencies += "org.apache.commons" % "commons-csv" % "1.0"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
//assemblyShadeRules in assembly := Seq(
//  ShadeRule.rename("org.apache.commons.csv.**" -> "shaded.org.apache.commons.csv.@1").inAll
//)
