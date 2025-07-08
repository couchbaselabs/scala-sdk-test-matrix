#!/usr/bin/env bash
set -euo pipefail

./mvnw -B clean test 

# Test matrix – latest patch release for each Scala 3 minor.
SCALA_VERSIONS=(
  3.3.6
  3.4.0 
  3.5.2
  3.6.4
  3.7.1
)

# Always refresh to the newest released dependency versions once per job.
# (We do this only once to avoid fetching metadata repeatedly in the loop.)
#mvn -q versions:use-latest-releases -DgenerateBackupPoms=false

for V in "${SCALA_VERSIONS[@]}"; do
  echo "\n===== Building & testing with Scala ${V} ====="
  ./mvnw -B \
    -pl scala3.3-to-couchbase-sdk3 \
    -am \
    -Dscala.version="${V}" \
    clean test
  echo "===== Scala ${V} ✔︎ ====="
done

echo "\nAll Scala versions passed!" 