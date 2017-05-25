package com.datastax.spark.example;

import com.datastax.driver.core.Session;
import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.japi.rdd.CassandraTableScanJavaRDD;
import com.google.common.collect.ImmutableMap;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;
import scala.runtime.AbstractFunction1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.*;


// For DSE it is not necessary to set connection parameters for spark.master (since it will be done
// automatically)
public class WriteRead {
  public static void main(String[] args) {

    // A SparkSession
    SparkSession spark = SparkSession
      .builder()
      .appName("Datastax Java example")
      .getOrCreate();

    CassandraConnector.apply(spark.sparkContext()).withSessionDo(
      new AbstractFunction1<Session, Object>() {
        public Object apply(Session session) {
          session.execute("CREATE KEYSPACE IF NOT EXISTS ks WITH "
            + "replication = {'class': 'SimpleStrategy', 'replication_factor': 1 }");
          return session
            .execute("CREATE TABLE IF NOT EXISTS ks.kv (k int, v int, PRIMARY KEY (k))");
        }
      });

    JavaRDD<Tuple2<Integer, Integer>> data = spark
      .range(1, 10)
      .javaRDD()
      .map(x -> new Tuple2<>(x.intValue(), x.intValue()));

    javaFunctions(data)
      .writerBuilder("ks", "kv", mapTupleToRow(Integer.class, Integer.class))
      .saveToCassandra();

    // Read data as RDD
    JavaRDD<Tuple2<Integer, Integer>> scReadData = javaFunctions(spark.sparkContext())
      .cassandraTable("ks", "kv", mapRowToTuple(Integer.class, Integer.class));

    // Read data as DataSet (DataFrame)
    Dataset<Row> dataset = spark
      .read()
      .format("org.apache.spark.sql.cassandra")
      .options(ImmutableMap.of("table", "kv", "keyspace", "ks"))
      .load();

    System.out.println("Data read as RDD");
    scReadData.collect()
      .forEach(System.out::println);

    System.out.println("Data read as DataSet (DataFrame)");
    dataset
      .javaRDD()
      .map(row -> new Tuple2<>(row.getInt(0), row.getInt(1)))
      .collect()
      .forEach(System.out::println);

    spark.stop();
    System.exit(0);
  }
}

