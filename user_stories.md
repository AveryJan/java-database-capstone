# Smart Clinic Management System — User stories

These user stories map to proposed public GitHub issues. Each includes acceptance criteria and is ready to create as a GitHub Issue.

## Admin
**US-01 — Add doctor (High).** As an administrator, I want to register a doctor with name, specialty, availability, email, and credentials so that the doctor can sign in and accept appointments.
- Given a logged-in admin, when valid details are submitted, then the doctor and account are persisted.
- Given a duplicate email, then the request is rejected with HTTP 409.
- A patient or doctor cannot add a new doctor.

**US-02 — View doctors (Medium).** As an administrator, I want to review registered doctors so I can maintain the roster.
- GET /api/doctors returns doctors; an invalid ID returns 404.

## Patient
**US-03 — Register and sign in (High).** As a patient, I want to create an account and authenticate securely.
- Registration requires a name, valid email, and a password of at least ten characters.
- Passwords are stored as bcrypt hashes, never plaintext.

**US-04 — Find a doctor (High).** As a patient, I want to search by doctor name, specialty, or available time.
- A partial name search is case-insensitive.
- An empty search returns matching doctors without a server error.

**US-05 — Book and review appointments (High).** As a patient, I want to book a future appointment and view only my appointments.
- Patient token is required to book or view appointments.
- A past date/time is rejected; an already-booked time for a doctor is rejected.
- A patient cannot use an ID parameter to read someone else's appointments.

**US-06 — See prescriptions (Medium).** As a patient, I want to review prescriptions assigned to my account.
- Patient token determines which prescriptions are returned.

## Doctor
**US-07 — Review appointments (High).** As a doctor, I want to view my upcoming patient appointments.
- The login identity determines the doctor's appointment list.
- Doctor tokens cannot use patient/admin endpoints.

**US-08 — Issue a prescription (High).** As a doctor, I want to issue a prescription to a patient so that the patient can view it.
- A doctor JWT is required; invalid patient and empty medication are rejected.
- The saved prescription identifies the authenticated doctor and recipient patient.

## Quality, security, and deployment
**US-09 — Compile and test (High).** As a developer, I want CI compilation and tests on pushes and pull requests.
- GitHub Actions runs Maven verify using Java 17.

**US-10 — Container deployment (Medium).** As a developer, I want Docker Compose to start the Spring Boot API and MySQL together.
- A DB health check gates app startup; persistent MySQL volume is configured.

**US-11 — Reporting (Medium).** As an admin, I want daily and monthly/yearly appointment reports.
- MySQL procedures return count breakdowns; call signatures are documented.

> To satisfy a rubric requiring actual GitHub Issues, create one GitHub Issue per user story. This Markdown document alone does not establish Issues on GitHub.
