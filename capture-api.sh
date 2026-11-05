#!/usr/bin/env bash
set -euo pipefail
BASE="${BASE_URL:-http://localhost:8080}"
mkdir -p evidence/actual
curl -sS -i "$BASE/api/doctors" | tee evidence/actual/Q24-all-doctors.txt
printf '
'
LOGIN=$(curl -fsS -H 'Content-Type: application/json' -d '{"email":"jordan@clinic.test","password":"DemoPatient123!"}' "$BASE/api/auth/login")
TOKEN=$(printf '%s' "$LOGIN" | python3 -c 'import sys,json;print(json.load(sys.stdin)["token"])')
curl -sS -i -H "Authorization: Bearer $TOKEN" "$BASE/api/patient/appointments" | tee evidence/actual/Q25-patient-appointments.txt
printf '
'
curl -sS -i "$BASE/api/doctors/search?specialty=Cardiology&time=09%3A00" | tee evidence/actual/Q26-search-doctors.txt
printf '
Saved real curl responses without printing the private login token.
'
