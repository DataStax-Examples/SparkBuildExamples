name := "writeRead"

version := "0.1"

crossPaths := false

autoScalaLibrary := false

scalaVersion := "2.11.8"

resolvers += Resolver.mavenLocal // for testing
resolvers += "DataStax Repo" at "https://repo.datastax.com/public-repos/"

val dseVersion = "6.8.1"

// Please make sure that following DSE version matches your DSE cluster version.
// SBT 0.13.13 or greater required because of a dependency resolution bug
libraryDependencies += "com.datastax.dse" % "dse-spark-dependencies" % dseVersion % "provided"

//Your dependencies
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"
//libraryDependencies += "org.apache.commons" % "commons-csv" % "1.0"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
//assemblyShadeRules in assembly := Seq(
//  ShadeRule.rename("org.apache.commons.csv.**" -> "shaded.org.apache.commons.csv.@1").inAll
//)
