# Smart Clinic Management System — Java Database Capstone

Spring Boot 3 / Java 17 / MySQL 8.4 / JWT role security / Docker Compose / GitHub Actions.

## Start

```bash
docker compose up -d --build
curl -i http://localhost:8080/api/doctors
```

Demo accounts (local demo only): `admin@clinic.test` / `DemoAdmin123!`; `maya@clinic.test` / `DemoDoctor123!`; `jordan@clinic.test` / `DemoPatient123!`. **Never use demo passwords in production.** Set `.env` secrets before deploying anywhere public.

## Build and test

```bash
./mvnw clean compile
./mvnw test
./mvnw clean package
```

Windows: use installed Maven as `mvn clean verify`. The Unix `mvnw` is a portable command forwarding to locally installed Maven, not an official self-downloading Maven Wrapper.

## Evidence for Q19–Q26

```bash
bash evidence/capture-sql.sh
bash evidence/capture-api.sh
```

`evidence/actual/` contains the **real** command output only after you execute the scripts against the running application; it is intentionally empty in the ZIP. Supply genuine SQL result tables and API responses rather than just commands. If procedures were not created because an existing MySQL volume was reused, load `sql/procedures.sql` explicitly.

## Submission

`SUBMISSION_GUIDE.md` maps the rubric to files and commands. Prior Q13–Q18 screenshots were already marked correct; keep those genuine screenshots from the earlier submission. No new screenshots are fabricated.

## Important limitations

This package was statically inspected, not run against a live MySQL server or complete Maven dependency set here. Database concurrency, production security controls, and end-to-end behavior require additional testing before real clinical use. This is a fictional educational system; do not input actual patient data.
