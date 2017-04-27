package com.datastax.spark.example


import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.spark.connector.embedded.{EmbeddedCassandra, SparkTemplate}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

case class KVRow (k: Int, v: Int)

/**
  * An Example Integration Test which uses an embedded Cassandra and Spark service.
  * Run from IntelliJ Idea by right clicking and selecting "Run 'WriteReadSpec'"
  */
@RunWith(classOf[JUnitRunner])
class WriteReadSpec extends FlatSpec with EmbeddedCassandra with SparkTemplate with Matchers{
  override def clearCache(): Unit = CassandraConnector.evictCache()

  //Sets up CassandraConfig and SparkContext
  useCassandraConfig(Seq("cassandra-default.yaml.template"))
  useSparkConf(defaultConf)
  Cleanup

  val connector = CassandraConnector(defaultConf)
  val ksName = "test"
  val tableName = "kv"

  "We" should "be able to access our Embedded Cassandra Node" in {
    connector
      .withSessionDo(session => session.execute("SELECT * FROM system_schema.tables"))
      .all() should not be empty
  }

  it should "be able to do some work with Spark" in {
    sc.parallelize(1 to 10).count should be (10)
  }

  it should "be able to do some work with Cassandra and Spark" in {
    val testData = (1 to 1000).map(value => KVRow(value, value))
    connector.withSessionDo{ session =>
      session.execute(s"CREATE KEYSPACE IF NOT EXISTS $ksName WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 }")
      session.execute(s"CREATE TABLE IF NOT EXISTS $ksName.$tableName (k int, v int, PRIMARY KEY (k))")
      sc.parallelize(testData).saveToCassandra(ksName, tableName)
      val results = sc.cassandraTable[KVRow](ksName, tableName).collect()
      results should contain theSameElementsAs testData
    }
  }

}
