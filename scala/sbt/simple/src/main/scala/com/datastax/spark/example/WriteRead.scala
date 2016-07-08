package com.datastax.spark.example

import org.apache.spark.sql.cassandra._
import org.apache.spark.{SparkConf, SparkContext}

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.CassandraConnector

object WriteRead extends App {

  val conf = new SparkConf()
    .setAppName("Datastax Scala example")

  CassandraConnector(conf).withSessionDo{ session =>
    session.execute(
      """CREATE KEYSPACE IF NOT EXISTS ks WITH
        | replication = {'class': 'SimpleStrategy', 'replication_factor': 1 }""".stripMargin)
    session.execute("""CREATE TABLE IF NOT EXISTS ks.kv (k int, v int, PRIMARY KEY (k))""")
  }

  // A SparkContext
  val sc = new SparkContext(conf)
  val sqlContext = new CassandraSQLContext(sc)

  // Write some data to C*
  sc.parallelize(1 to 10).map( x => (x,x)).saveToCassandra("ks", "kv")

  // Read Data Using the Spark Context
  val scReadData = sc.cassandraTable("ks", "kv").collect

  // Read Data Using the Sql Context
  sqlContext.setKeyspace("ks")
  val sqlReadData = sqlContext
    .sql("SELECT * FROM kv")
    .collect().map( row => (row.getInt(0), row.getInt(1)))

  println("Data Read Via Spark Context")
  scReadData.foreach(println)
  println("---------------------------")
  println("Data Read Via Sql Context")
  sqlReadData.foreach(println)

  sc.stop()
  sys.exit(0)
}