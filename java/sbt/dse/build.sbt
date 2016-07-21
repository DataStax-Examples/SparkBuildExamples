name := "writeRead"

version := "0.1"

crossPaths := false

autoScalaLibrary := false

resolvers += "DataStax Repo" at "https://datastax.artifactoryonline.com/datastax/public-repos/"

// Please make sure that following DSE version matches your DSE cluster version.
libraryDependencies += "com.datastax.dse" % "dse-spark-dependencies" % "5.0.1" % "provided"

//Your dependencies
//libraryDependencies += "com.github.scopt" %% "scopt" % "3.2.0"

