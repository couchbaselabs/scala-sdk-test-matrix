package com.couchbase

import com.typesafe.config.ConfigFactory

object ClusterConfig {
  private val base = ConfigFactory.load()

  private def parse(path: String) = {
    val f = new java.io.File(path)
    if (f.exists) ConfigFactory.parseFile(f) else ConfigFactory.empty()
  }

  private val merged = parse("../application.local.conf")
    .withFallback(parse("../application.conf"))
    .withFallback(base)
    .resolve()

  private val cfg = merged.getConfig("couchbase")

  val host: String       = cfg.getString("host")
  val username: String   = cfg.getString("username")
  val password: String   = cfg.getString("password")
  val bucket: String     = cfg.getString("bucket")
  val scope: String      = cfg.getString("scope")
  val collection: String = cfg.getString("collection")

  println(
    s"""[ClusterConfig] Resolved Couchbase configuration:\n${cfg.root().render()}""")
} 