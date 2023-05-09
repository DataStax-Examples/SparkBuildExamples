name := "writeRead"
version := "0.1"

scalaVersion := "2.11.8"

resolvers += Resolver.mavenLocal // for testing
resolvers += "DataStax Repo" at "https://repo.datastax.com/public-repos/"

val dseVersion = "6.8.35"

// Please make sure that following DSE version matches your DSE cluster version.
// Exclusions are solely for running integrated testing
// Warning Sbt 0.13.13 or greater is required due to a bug with dependency resolution
libraryDependencies += "com.datastax.dse" % "dse-spark-dependencies" % dseVersion % "provided" exclude(
    "org.slf4j", "log4j-over-slf4j")

//Your dependencies
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"
//libraryDependencies += "org.apache.commons" % "commons-csv" % "1.0"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
//assemblyShadeRules in assembly := Seq(
//  ShadeRule.rename("org.apache.commons.csv.**" -> "shaded.org.apache.commons.csv.@1").inAll
//)
