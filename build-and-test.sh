#!/usr/bin/env bash
set -euo pipefail

# Update Maven project to latest released dependency versions, then build & test
mvn -q versions:use-latest-releases -DgenerateBackupPoms=false
mvn -B clean test 