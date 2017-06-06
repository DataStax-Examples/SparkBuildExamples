
name := "writeRead"

version := "0.1"

scalaVersion := "2.11.8"

resolvers += "DataStax Repo" at "https://repo.datastax.com/public-repos/"

val dseVersion = "5.1.0"

// Please make sure that following DSE version matches your DSE cluster version.
// Exclusions are solely for running integrated testing
// Warning Sbt 0.13.13 or greater is required due to a bug with dependency resolution
libraryDependencies += "com.datastax.dse" % "dse-spark-dependencies" % dseVersion % "provided" excludeAll (
    ExclusionRule("org.slf4j","slf4j-log4j12"),
    ExclusionRule("org.mortbay.jetty"),
    ExclusionRule("javax.servlet"),
    ExclusionRule("org.apache.cassandra"),
    ExclusionRule("com.datastax.dse", "dse-java-driver-core"),
    ExclusionRule("org.apache.solr", "solr-solrj")
  )

// Test Dependencies
// The 'test/resources' Directory in should match the resources directory in the `it` directory
// for the version of the Spark Cassandra Connector in use.
val scalaTestVersion = "3.0.0"
val connectorVersion = "2.0.1"
val jUnitVersion = "4.12"
val cassandraVersion = "3.2"

libraryDependencies ++= Seq(
  "com.datastax.dse" % "dse-java-driver-core" % "1.2.3",
  "org.apache.solr" % "solr-solrj" % "6.0.1",
  "com.datastax.spark" %% "spark-cassandra-connector-embedded" % connectorVersion % "test",
  "org.apache.cassandra" % "cassandra-all" % cassandraVersion % "test",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "junit" % "junit" % "4.12"
).map(_.excludeAll(
  ExclusionRule("org.slf4j","log4j-over-slf4j"),
  ExclusionRule("org.slf4j","slf4j-log4j12"))
)  // Excluded to allow for Cassandra to run embedded

//Forking is required for the Embedded Cassandra
fork in Test := true

assemblyMergeStrategy in assembly := {
  case "META-INF/io.netty.versions.properties" => MergeStrategy.discard
  case x => (assemblyMergeStrategy in assembly).value(x)
}

//Your dependencies
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"
//libraryDependencies += "org.apache.commons" % "commons-csv" % "1.0"

//assemblyShadeRules in assembly := Seq(
//  ShadeRule.rename("org.apache.commons.csv.**" -> "shaded.org.apache.commons.csv.@1").inAll
//)
