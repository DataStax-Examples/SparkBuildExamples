package com.datastax.spark.example

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.cassandra._

// For DSE it is not necessary to set connection parameters for spark.master (since it will be done
// automatically)
object WriteRead extends App {

  val spark = SparkSession.builder
    .appName("Datastax Scala example")
    .enableHiveSupport()
    .getOrCreate()

  import spark.implicits._

  // Create keyspace and table
  CassandraConnector(spark.sparkContext).withSessionDo { session =>
    session.execute(
      """CREATE KEYSPACE IF NOT EXISTS ks WITH
        | replication = {'class': 'SimpleStrategy', 'replication_factor': 1 }""".stripMargin)
    session.execute("""CREATE TABLE IF NOT EXISTS ks.kv (k int, v int, PRIMARY KEY (k))""")
  }

  // Write some data
  spark.range(1, 10)
    .map(x => (x, x))
    .rdd
    .saveToCassandra("ks", "kv")

  // Read data as RDD
  val rdd = spark.sparkContext
    .cassandraTable(keyspace = "ks", table = "kv")

  // Read data as DataSet (DataFrame)
  val dataset = spark.read
    .cassandraFormat(keyspace = "ks", table = "kv")
    .load()

  println("Data read as RDD")
  rdd.collect()
    .foreach(println)

  println("Data read as DataSet (DataFrame)")
  dataset.collect()
    .foreach(println)

  spark.stop()
  sys.exit(0)
}