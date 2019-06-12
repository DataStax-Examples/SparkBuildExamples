# Example projects for using DSE Analytics

These are template projects that illustrate how to build Spark Application written in Java or Scala
with  Maven, SBT or Gradle which can be run on either DataStax Enterprise (DSE) or Apache Spark. The
example project implements a simple write-to-/read-from-Cassandra application for each language and
build tool.

## Dependencies

Compiling Spark applications depends on Apache Spark and optionally on Spark Cassandra Connector
jars. Projects `dse` and `oss` show two different ways of supplying these dependencies.  Both
projects are built and executed with similar commands.

### DSE

If you are planning to execute your Spark Application on a DSE cluster, you can use the `dse`
project template which will automatically download (and use during compilation) all jars available
in the DSE cluster. Please mind the DSE version specified in the build file; it should should match
the one in your cluster.

Please note that DSE projects templates are meant to be built with `sbt` 0.13.13 or newer. In case of
unresolved dependencies errors, please update `sbt` and than clean `ivy` cache (with
`rm ~/.ivy2/cache/com.datastax.dse/dse-spark-dependencies/` command)

### OSS

If you are planning to execute your Spark Application against Open Source Apache Spark and Open
Source  Apache Cassandra, use the `oss` project template where all dependencies have to be specified
manually in  build files. Please mind the dependency versions; these should match the ones in your
execution environment.

For additional info about version compatibility please refer to the Spark Cassandra Connector
[Version Compatibility Table](https://github.com/datastax/spark-cassandra-connector#version-compatibility).

### Additional dependencies

Prepared projects use extra plugins so additional dependencies can be included with your
application's jar. All you need to do is add dependencies in the build configuration file.

## Building & running

### Sbt

Task         | Command
-------------|------------
build        | `sbt clean assembly`
run (Scala)  | `dse spark-submit --class com.datastax.spark.example.WriteRead target/scala-2.11/writeRead-assembly-0.1.jar`
run (Java)   | `dse spark-submit --class com.datastax.spark.example.WriteRead target/writeRead-assembly-0.1.jar`

### Gradle

Task                | Command
--------------------|------------
build               | `gradle shadowJar`
run (Scala, Java)   | `dse spark-submit --class com.datastax.spark.example.WriteRead build/libs/writeRead-0.1-all.jar`

### Maven

Task                | Command
--------------------|------------
build               | `mvn package`
run (Scala, Java)   | `dse spark-submit --class com.datastax.spark.example.WriteRead target/writeRead-0.1.jar`

Notes:

1. The above command example are for DSE. To run with open source Spark, use `spark-submit` instead
2. Also see included example script [BuildTestAll.sh](BuildTestAll.sh) which runs all combinations


## Running Integrated Tests

Integrated tests have been set up under a `test` task in each build system. To run
the tests, invoke the build system and then launch `test`. These tests demonstrate
how to run integrated embedded Cassandra as well as Local Spark from within your testing
environment.

Currently only Scala Testing examples are provided.

These tests should also function inside IDEs that are configured with the ability to run
the build system's tests.

## Support

The code, examples, and snippets provided in this repository are not "Supported Software" under any DataStax subscriptions or other agreements.

## License

Copyright 2016-2019, DataStax

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

