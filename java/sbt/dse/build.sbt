name := "writeRead"

version := "0.1"

crossPaths := false

autoScalaLibrary := false

scalaVersion := "2.11.8"

resolvers += "DataStax Repo" at "https://datastax.artifactoryonline.com/datastax/public-repos/"

val dseVersion = "5.1.0"

// Please make sure that following DSE version matches your DSE cluster version.
// SBT 0.13.13 or greater required because of a dependency resolution bug
libraryDependencies ++= Seq(
  "com.datastax.dse" % "dse-spark-dependencies" % dseVersion % "provided"  excludeAll (
    ExclusionRule("com.datastax.dse", "dse-java-driver-core"),
    ExclusionRule("org.apache.solr", "solr-solrj")
  ),
  "com.datastax.dse" % "dse-java-driver-core" % "1.2.3",
  "org.apache.solr" % "solr-solrj" % "6.0.1"
)

//Your dependencies
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"
//libraryDependencies += "org.apache.commons" % "commons-csv" % "1.0"

assemblyMergeStrategy in assembly := {
  case "META-INF/io.netty.versions.properties" => MergeStrategy.discard
  case x => (assemblyMergeStrategy in assembly).value(x)
}

//assemblyShadeRules in assembly := Seq(
//  ShadeRule.rename("org.apache.commons.csv.**" -> "shaded.org.apache.commons.csv.@1").inAll
//)
