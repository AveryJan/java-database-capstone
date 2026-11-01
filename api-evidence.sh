#!/usr/bin/env bash
set -euo pipefail
BASE="${BASE_URL:-http://localhost:8080}"
command -v curl >/dev/null || { echo 'curl required' >&2; exit 1; }
command -v python3 >/dev/null || { echo 'python3 required' >&2; exit 1; }
echo 'Q24: GET all doctors'
curl -sS -i "$BASE/api/doctors"
echo -e '\nQ25: GET patient appointments with patient credentials'
LOGIN=$(curl -fsS -H 'Content-Type: application/json' -d '{"email":"jordan@clinic.test","password":"DemoPatient123!"}' "$BASE/api/auth/login")
TOKEN=$(printf '%s' "$LOGIN" | python3 -c 'import json,sys;print(json.load(sys.stdin)["token"])')
curl -sS -i -H "Authorization: Bearer $TOKEN" "$BASE/api/patient/appointments"
echo -e '\nQ26: GET cardiology doctors matching available time 09:00'
curl -sS -i "$BASE/api/doctors/search?specialty=Cardiology&time=09%3A00"
echo
