#!/bin/bash
set -ev
if [ "${TRAVIS_PULL_REQUEST}" = "false" ]; then
  echo "Assembling and publishing release"
  ./gradlew assembleRelease publishApkRelease
else
  echo "Assembling and testing Debug"
  ./gradlew assembleDebugUnitTest testDebug --tests="app.youkai.*"
fi