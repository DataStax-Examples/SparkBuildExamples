package com.datastax.spark.connector

import com.datastax.spark.connector.embedded.EmbeddedCassandra
import com.datastax.spark.connector.util.SerialShutdownHooks

object Cleanup {

  EmbeddedCassandra.removeShutdownHook
  // now embedded C* won't shutdown itself, let's do it in serial fashion
  SerialShutdownHooks.add("Shutting down all Cassandra runners")(() => {
    EmbeddedCassandra.shutdown
  })

}
