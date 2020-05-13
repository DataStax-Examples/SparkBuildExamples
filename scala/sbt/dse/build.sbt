
name := "writeRead"

version := "0.1"

scalaVersion := "2.11.8"

resolvers += Resolver.mavenLocal // for testing
resolvers += "DataStax Repo" at "https://repo.datastax.com/public-repos/"

val dseVersion = "6.8.1"

// Please make sure that following DSE version matches your DSE cluster version.
// Exclusions are solely for running integrated testing
// Warning Sbt 0.13.13 or greater is required due to a bug with dependency resolution
libraryDependencies += "com.datastax.dse" % "dse-spark-dependencies" % dseVersion % "provided" exclude(
    "org.slf4j", "log4j-over-slf4j")

// Test Dependencies
// The 'test/resources' Directory in should match the resources directory in the `it` directory
// for the version of the Spark Cassandra Connector in use.
val scalaTestVersion = "3.0.0"
val connectorVersion = "2.0.10"
val jUnitVersion = "4.12"
val cassandraVersion = "3.2"

libraryDependencies ++= Seq(
  "com.datastax.spark" %% "spark-cassandra-connector-embedded" % connectorVersion % "test" exclude(
    "com.datastax.cassandra", "*"),
  "org.apache.cassandra" % "cassandra-all" % cassandraVersion % "test",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "junit" % "junit" % "4.12" % "test"
).map(_.excludeAll(
  ExclusionRule("org.slf4j","log4j-over-slf4j"),
  ExclusionRule("org.slf4j","slf4j-log4j12"))
)  // Excluded to allow for Cassandra to run embedded

//Forking is required for the Embedded Cassandra
fork in Test := true


//Your dependencies
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"
//libraryDependencies += "org.apache.commons" % "commons-csv" % "1.0"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
//assemblyShadeRules in assembly := Seq(
//  ShadeRule.rename("org.apache.commons.csv.**" -> "shaded.org.apache.commons.csv.@1").inAll
//)
