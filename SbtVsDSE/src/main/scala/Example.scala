/**
  * An Example application
  */

import org.apache.spark._
import org.apache.spark.sql.hive.HiveContext

import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.spark.connector._

object Example extends App {

  val conf = new SparkConf()
    .setAppName("Example")

  CassandraConnector(conf).withSessionDo{ session =>
    session.execute(
      """CREATE KEYSPACE IF NOT EXISTS ks WITH
        | replication = {'class': 'SimpleStrategy', 'replication_factor': 1 }""".stripMargin)
    session.execute("""CREATE TABLE IF NOT EXISTS ks.kv (k int, v int, PRIMARY KEY (k))""")
  }


  // A SparkContext
  val sc = new SparkContext(conf)

  // This Hive Context will use the DSE HiveMetaStore
  val sqlContext = new HiveContext(sc)

  // Write some data to C*
  sc.parallelize(1 to 10).map( x => (x,x)).saveToCassandra("ks", "kv")

  // Read Data Using the Spark Context
  val scReadData = sc.cassandraTable("ks", "kv").collect

  // Read Data Using the Hive Context
  val sqlReadData = sqlContext
    .sql("SELECT * FROM ks.kv")
    .collect().map( row => (row.getInt(0), row.getInt(1)))

  println("Data Read Via Spark Context")
  scReadData.foreach(println)
  println("---------------------------")
  println("Data Read Via Hive Context")
  sqlReadData.foreach(println)
  sc.stop()
  sys.exit(0)
}
