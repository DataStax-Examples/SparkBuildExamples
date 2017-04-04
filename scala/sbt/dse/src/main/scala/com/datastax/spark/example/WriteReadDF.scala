package com.datastax.spark.example

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession


// For DSE it is not necessary to set connection parameters for spark.master (since it will be done
// automatically)
object WriteReadDF extends App {

//  val conf = new SparkConf()
//    .setAppName("Datastax Scala example")

  val spark = SparkSession.builder
    .appName("Datastax Scala DF example")
    .getOrCreate

  val conf = spark.sparkContext.SparkConf

  CassandraConnector(conf).withSessionDo { session =>
    session.execute(
      """CREATE KEYSPACE IF NOT EXISTS ks WITH
        | replication = {'class': 'SimpleStrategy', 'replication_factor': 1 }""".stripMargin)
    session.execute("""CREATE TABLE IF NOT EXISTS ks.kv (k int, v int, PRIMARY KEY (k))""")
  }

  // A SparkContext
  val sc = new SparkContext(conf)
  val hiveContext = new HiveContext(sc)

  // Write some data to C*
  sc.parallelize(1 to 10).map(x => (x, x)).saveToCassandra("ks", "kv")

  // Read Data Using the Spark Context
  val scReadData = sc.cassandraTable("ks", "kv").collect

  // Read Data Using the Hive Context
  val sqlReadData = hiveContext
    .read
    .format("org.apache.spark.sql.cassandra")
    .options(Map("table" -> "kv", "keyspace" -> "ks"))
    .load()
    .collect()
    .map(row => (row.getInt(0), row.getInt(1)))

  println("Data Read Via Spark Context")
  scReadData.foreach(println)
  println("---------------------------")
  println("Data Read Via Hive Context")
  sqlReadData.foreach(println)

  sc.stop()
  spark.stop
  sys.exit(0)
}
