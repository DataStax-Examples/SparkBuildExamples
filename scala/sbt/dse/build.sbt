
name := "writeRead"

version := "0.1"

scalaVersion := "2.10.6"

resolvers += "DataStax Repo" at "https://datastax.artifactoryonline.com/datastax/public-repos/"

// Please make sure that following DSE version matches your DSE cluster version.
// Exclusions are solely for running integrated testing
libraryDependencies += (
  "com.datastax.dse" % "dse-spark-dependencies" % "5.0.1" % "provided" excludeAll (
      ExclusionRule("org.slf4j","slf4j-log4j12"),
      ExclusionRule("org.mortbay.jetty"),
      ExclusionRule("javax.servlet")
    )
  )

// Test Dependencies
//The 'test/resources' Directory in  should match the resources directory in the `it` directory
//for the version of the Spark Cassandra Connector in use.
val scalaTestVersion = "3.0.0"
val connectorVersion = "1.6.0"
libraryDependencies ++= Seq(
  "com.datastax.spark" %% "spark-cassandra-connector-embedded" % connectorVersion % "test",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
).map(_.exclude("org.slf4j","slf4j-log4j12"))  // Excluded to allow for Cassandra to run embedded

//Forking is required for the Embedded Cassandra
fork in Test := true


//Your dependencies
//libraryDependencies += "com.github.scopt" %% "scopt" % "3.2.0"
