package com.example

import org.scalatest.funsuite.AnyFunSuite
import com.couchbase.client.scala.{Cluster, ClusterOptions}
import com.couchbase.client.scala.json.JsonObject
import com.example.ClusterConfig as CC

import scala.util.{Failure, Success}
import org.scalatestplus.junit.JUnitRunner
import org.junit.runner.RunWith

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration


@RunWith(classOf[JUnitRunner])
class AppTest extends AnyFunSuite:
  private val cluster = Cluster.connect(CC.host, ClusterOptions.create(CC.username, CC.password)).get
  private val bucket = cluster.bucket(CC.bucket)
  private val scope = bucket.scope(CC.scope)
  private val collection = scope.collection(CC.collection)
  
  cluster.waitUntilReady(Duration(30, TimeUnit.SECONDS))

  test("basic kv ops"):
    val doc = JsonObject("foo" -> "bar")
    collection.upsert("doc::1", doc) match
      case Success(_) =>
      case Failure(err) => fail(err.getMessage)

    collection.get("doc::1") match
      case Success(result) => assert(result.contentAs[JsonObject].get == doc)
      case Failure(err) => fail(err.getMessage)

    collection.remove("doc::1") match
      case Success(_) =>
      case Failure(err) => fail(err.getMessage)

  test("basic query"):
    cluster.query("SELECT 1=1") match
      case Success(result) =>
        result.rowsAs[JsonObject] match
          case Success(rows) => assert(rows.nonEmpty)
          case Failure(err) => fail(err.getMessage)
      case Failure(err) => fail(err.getMessage)

  test("basic transaction"):
    cluster.transactions.run(ctx =>
      val id = s"txn-doc::${java.util.UUID.randomUUID()}"
      for
        _ <- ctx.insert(collection, id, JsonObject("foo" -> "bar"))
        doc <- ctx.get(collection, id)
        _ <- ctx.replace(doc, JsonObject("foo" -> "baz"))
      yield ()
    ) match
      case Success(_) =>
      case Failure(err) => fail(err.getMessage)

