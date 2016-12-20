name := "writeRead"

version := "0.1"

crossPaths := false

autoScalaLibrary := false

resolvers += "DataStax Repo" at "https://datastax.artifactoryonline.com/datastax/public-repos/"

val dseVersion = "5.0.4"

// Please make sure that following DSE version matches your DSE cluster version.
// SBT 0.13.13 or greater required because of a dependency resolution bug
libraryDependencies += "com.datastax.dse" % "dse-spark-dependencies" % dseVersion % "provided"

//Your dependencies
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"
//libraryDependencies += "org.apache.commons" % "commons-csv" % "1.0"

//assemblyShadeRules in assembly := Seq(
//  ShadeRule.rename("org.apache.commons.csv.**" -> "shaded.org.apache.commons.csv.@1").inAll
//)
