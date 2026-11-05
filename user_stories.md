# Smart Clinic — Agile user stories

All stories include title, role, priority, story-point estimate and testable acceptance criteria. Story points use a Fibonacci scale.

## Admin

### US-01: Add a doctor
- **Role:** Admin
- **User story:** As a admin, I want to register a doctor with name, specialty, availability and credentials so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 5
- **Acceptance criteria:** Valid fields create both doctor and login; duplicate email gives 409; other roles receive 403.

### US-02: View doctor roster
- **Role:** Admin
- **User story:** As a admin, I want to see all registered doctors so that the clinic workflow is reliable.
- **Priority:** Medium
- **Story points:** 3
- **Acceptance criteria:** GET doctors returns complete directory; unknown doctor returns 404.

### US-03: Protect doctor onboarding
- **Role:** Admin
- **User story:** As a admin, I want to prevent unauthorized provider creation so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 3
- **Acceptance criteria:** Unauthenticated request returns 401; patient role returns 403.

### US-04: Review appointment reporting
- **Role:** Admin
- **User story:** As a admin, I want to analyze daily booking totals by provider so that the clinic workflow is reliable.
- **Priority:** Medium
- **Story points:** 5
- **Acceptance criteria:** Daily stored procedure returns doctor_id, name, appointment_day and count.

### US-05: Review utilization trends
- **Role:** Admin
- **User story:** As a admin, I want to find most visited providers by month and year so that the clinic workflow is reliable.
- **Priority:** Medium
- **Story points:** 5
- **Acceptance criteria:** Stored procedures return top doctor_id and patients_seen using requested period.

## Patient

### US-06: Register account
- **Role:** Patient
- **User story:** As a patient, I want to create a patient profile using verified input so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 5
- **Acceptance criteria:** Name/email/password required; duplicate email rejected; password hash stored.

### US-07: Log in
- **Role:** Patient
- **User story:** As a patient, I want to sign in securely with email and password so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 5
- **Acceptance criteria:** Correct credentials yield signed role JWT; bad credentials return 401.

### US-08: Search providers
- **Role:** Patient
- **User story:** As a patient, I want to find a doctor by name, specialty or availability so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 3
- **Acceptance criteria:** Case-insensitive partial name search and specialty/time query return matching doctors.

### US-09: Book appointment
- **Role:** Patient
- **User story:** As a patient, I want to reserve an available future slot so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 8
- **Acceptance criteria:** Past times rejected 400; busy times rejected 409; successful booking persists.

### US-10: View own appointments
- **Role:** Patient
- **User story:** As a patient, I want to see only my bookings so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 5
- **Acceptance criteria:** Patient token required; principal identity determines patient id.

### US-11: Review prescriptions
- **Role:** Patient
- **User story:** As a patient, I want to see prescriptions issued to me so that the clinic workflow is reliable.
- **Priority:** Medium
- **Story points:** 3
- **Acceptance criteria:** Valid token retrieves only prescriptions assigned to logged-in patient.

## Doctor

### US-12: Doctor login
- **Role:** Doctor
- **User story:** As a doctor, I want to sign in using doctor credentials so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 5
- **Acceptance criteria:** Password validated against BCrypt; signed DOCTOR JWT returned.

### US-13: View appointments
- **Role:** Doctor
- **User story:** As a doctor, I want to see upcoming patient appointments so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 5
- **Acceptance criteria:** GET endpoint uses doctor principal not arbitrary doctor ID.

### US-14: Filter appointments by date
- **Role:** Doctor
- **User story:** As a doctor, I want to view appointments scheduled for one day so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 5
- **Acceptance criteria:** Date-specific endpoint returns only appointments within date boundaries.

### US-15: Check available times
- **Role:** Doctor
- **User story:** As a doctor, I want to see unbooked appointment slots for a date so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 5
- **Acceptance criteria:** Returns configured times excluding booked times, rejects missing doctors.

### US-16: Write prescription
- **Role:** Doctor
- **User story:** As a doctor, I want to create prescription for a patient so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 8
- **Acceptance criteria:** Validated medication and patient; role token required; structured 201 response.

### US-17: Restrict patient data
- **Role:** Doctor
- **User story:** As a doctor, I want to avoid exposing unrelated accounts so that the clinic workflow is reliable.
- **Priority:** High
- **Story points:** 5
- **Acceptance criteria:** Patient/Admin endpoints deny DOCTOR role; JWT cannot be forged.

## Delivery and testing

CI should compile and test on push and pull requests; Docker Compose starts MySQL and Spring Boot; run evidence scripts only against the actual local system.
