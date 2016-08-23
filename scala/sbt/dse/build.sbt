name := "writeRead"

version := "0.1"

scalaVersion := "2.10.6"

resolvers += "DataStax Repo" at "https://datastax.artifactoryonline.com/datastax/public-repos/"

// Please make sure that following DSE version matches your DSE cluster version.
libraryDependencies += "com.datastax.dse" % "dse-spark-dependencies" % "5.0.1" % "provided"

//Your dependencies
//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"
//libraryDependencies += "com.google.guava" % "guava" % "11.0"

//assemblyShadeRules in assembly := Seq(
//  ShadeRule.rename("com.google.**" -> "shaded.com.google.@1").inAll
//)
