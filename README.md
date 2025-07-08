# Couchbase Scala SDK Compatibility Matrix

This repository contains a **multi-module Maven project** that exercises the Couchbase Scala SDK
across several combinations of Scala and Couchbase SDK versions.

## Modules

| Module directory | Scala version | Couchbase Scala SDK |
|------------------|--------------|---------------------|
| `scala2.12-to-couchbase-sdk2.12` | 2.12.19 | 2.12
| `scala2.13-to-couchbase-sdk2.13` | 2.13.x  | 2.13
| `scala2.13-to-couchbase-sdk3`    | 2.13.x  | 3
| `scala3.3-to-couchbase-sdk2.13`  | 3.3.x   | 2.13
| `scala3.3-to-couchbase-sdk3`     | 3.3.x   | 3

> Each sub-project contains a minimal **`AppTest.scala`** that verifies basic operations.

## Prerequisites

* Java **17** or later (set `JAVA_HOME` accordingly)
* Maven **3.9+** (or use `./mvnw`)

## Running

Build and test everything:

```bash
./build-and-test.sh
```
