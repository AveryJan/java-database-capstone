#!/usr/bin/env bash
set -euo pipefail
mkdir -p evidence/actual
# Requires: docker compose up -d --build and mysql initial schema creation
# Procedure definitions are loaded at first database initialization.
docker compose exec -T db mysql -uclinic -p"${DB_PASSWORD:-clinic_dev_password}" --table smart_clinic < sql/assignment-queries.sql | tee evidence/actual/Q19-Q23-sql-output.txt
printf '
Saved actual SQL output. Capture a screenshot of each relevant result table for submission.
'
