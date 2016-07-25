package com.datastax.spark.example;

import com.datastax.driver.core.Session;
import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.cql.CassandraConnectorConf;
import com.google.common.collect.ImmutableMap;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.hive.HiveContext;
import scala.Tuple2;
import scala.runtime.AbstractFunction1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.*;


// For DSE it is not necessary to set connection parameters for spark.master (since it will be done
// automatically)
public class WriteRead
{
  public static void main(String[] args)
  {
    SparkConf conf = new SparkConf().setAppName("Datastax Java example");

    new CassandraConnector(CassandraConnectorConf.apply(conf)).withSessionDo(
      new AbstractFunction1<Session, Object>()
      {
        public Object apply(Session session)
        {
          session.execute("CREATE KEYSPACE IF NOT EXISTS ks WITH "
            + "replication = {'class': 'SimpleStrategy', 'replication_factor': 1 }");
          return session
            .execute("CREATE TABLE IF NOT EXISTS ks.kv (k int, v int, PRIMARY KEY (k))");
        }
      });

    // A SparkContext
    JavaSparkContext jsc = new JavaSparkContext(conf);
    HiveContext hiveContext = new HiveContext(jsc.sc());

    // Write some data to C*
    List<Integer> integers = IntStream.range(1, 10)
      .boxed()
      .collect(Collectors.toList());

    JavaRDD<Tuple2<Integer, Integer>> rdd = jsc.parallelize(integers)
      .map(i -> new Tuple2<>(i, i));

    javaFunctions(rdd)
      .writerBuilder("ks", "kv", mapTupleToRow(Integer.class, Integer.class))
      .saveToCassandra();

    // Read Data Using the Spark Context
    List<Tuple2<Integer, Integer>> scReadData = javaFunctions(jsc)
      .cassandraTable("ks", "kv", mapRowToTuple(Integer.class, Integer.class))
      .collect();

    // Read Data Using the Hive Context
    List<Tuple2<Integer, Integer>> sqlReadData = hiveContext
      .read()
      .format("org.apache.spark.sql.cassandra")
      .options(ImmutableMap.of("table", "kv", "keyspace", "ks"))
      .load()
      .javaRDD()
      .map(row -> new Tuple2<>(row.getInt(0), row.getInt(1))).collect();

    System.out.println("Data Read Via Spark Context");
    scReadData.forEach(System.out::println);
    System.out.println("---------------------------");
    System.out.println("Data Read Via Hive Context");
    sqlReadData.forEach(System.out::println);

    jsc.stop();
    System.exit(0);
  }
}

