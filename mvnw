#!/bin/sh
# Portable wrapper entrypoint for CI environments where Maven is provisioned.
set -eu
exec mvn "$@"
