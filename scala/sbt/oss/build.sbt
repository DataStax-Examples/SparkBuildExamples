name := "writeRead"

version := "0.1"

scalaVersion := "2.10.6"

val sparkVersion = "1.6.2"
val connectorVersion = "1.6.0"
//The 'test/resources' Directory in  should match the resources directory in the `it` directory
//for the version of the Spark Cassandra Connector in use.


val cassandraVersion = "3.0.2"
val scalaTestVersion = "3.0.0"
val jUnitVersion = "4.12"


// Please make sure that following dependencies have versions corresponding to the ones in your cluster.
// Note that spark-cassandra-connector should be provided with '--packages' flag to spark-submit command.
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
  "com.datastax.spark" %% "spark-cassandra-connector" % connectorVersion % "provided",
  //Test Dependencies
  "com.datastax.spark" %% "spark-cassandra-connector-embedded" % connectorVersion % "test",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "org.apache.cassandra" % "cassandra-all" % cassandraVersion % "test",
  "junit" % "junit" % jUnitVersion % "test"
).map(_.exclude("org.slf4j","log4j-over-slf4j"))  // Excluded to allow for Cassandra to run embedded

//Forking is required for the Embedded Cassandra
fork in Test := true

//Your dependencies
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"
//libraryDependencies += "org.apache.commons" % "commons-csv" % "1.0"

//assemblyShadeRules in assembly := Seq(
//  ShadeRule.rename("org.apache.commons.csv.**" -> "shaded.org.apache.commons.csv.@1").inAll
//)
