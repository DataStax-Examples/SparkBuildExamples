/*
DSE Example Build Sbt File for Spark
In this example build script we use the DSE spark-classpath to build our classpath. Sbt-assembly is
also included in case you add additional dependencies.
 */
name := "writeRead"

version := "0.1"

crossPaths := false

autoScalaLibrary := false


val DSE_HOME = sys.env.getOrElse("DSE_HOME", sys.env("HOME")+"/dse")

val sparkClasspathStr = s"$DSE_HOME/bin/dse spark-classpath".!!.trim
val sparkClasspathArr = sparkClasspathStr.split(':')

//Find all Jars on dse spark-classpath
val sparkClasspath = {
  for ( dseJar <- sparkClasspathArr if dseJar.endsWith("jar"))
    yield Attributed.blank(file(dseJar))
}.toSeq

//Your dependencies
//libraryDependencies += "com.github.scopt" %% "scopt" % "3.2.0"

//Add dse jars to classpath
unmanagedJars in Compile ++= sparkClasspath
unmanagedJars in Test ++= sparkClasspath

//DSE Jars don't have to be in the assembly
assemblyExcludedJars in assembly := sparkClasspath
