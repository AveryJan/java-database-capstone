# Smart Clinic — MySQL 8.4 relational database schema

The implemented Spring Boot JPA entities map to six main tables plus the doctor availability collection table. The column names below use Spring Boot's snake-case naming strategy. These types are valid MySQL types; inspect actual output with `SHOW CREATE TABLE` to confirm Hibernate version-specific DDL.

| Table | Column | MySQL data type | Constraints |
|---|---|---|---|
| user_accounts | id | BIGINT | PK, AUTO_INCREMENT |
| user_accounts | email | VARCHAR(255) | NOT NULL, UNIQUE |
| user_accounts | password_hash | VARCHAR(255) | NOT NULL |
| user_accounts | role | ENUM('ADMIN','DOCTOR','PATIENT') or VARCHAR(255) | NOT NULL |
| doctors | id | BIGINT | PK, AUTO_INCREMENT |
| doctors | name | VARCHAR(255) | NOT NULL |
| doctors | specialty | VARCHAR(255) | NOT NULL |
| doctors | available_time | VARCHAR(255) | NOT NULL (legacy search string) |
| doctors | email | VARCHAR(255) | UNIQUE |
| doctor_available_times | doctor_id | BIGINT | FK to doctors.id |
| doctor_available_times | available_time | VARCHAR(255) | NOT NULL |
| patients | id | BIGINT | PK, AUTO_INCREMENT |
| patients | name | VARCHAR(255) | NOT NULL |
| patients | email | VARCHAR(255) | NOT NULL, UNIQUE |
| patients | phone | VARCHAR(30) | NULL |
| appointments | id | BIGINT | PK, AUTO_INCREMENT |
| appointments | doctor_id | BIGINT | NOT NULL, FK to doctors.id |
| appointments | patient_id | BIGINT | NOT NULL, FK to patients.id |
| appointments | appointment_time | DATETIME(6) | NOT NULL |
| appointments | status | VARCHAR(255) | NOT NULL |
| prescriptions | id | BIGINT | PK, AUTO_INCREMENT |
| prescriptions | doctor_id | BIGINT | NOT NULL, FK to doctors.id |
| prescriptions | patient_id | BIGINT | NOT NULL, FK to patients.id |
| prescriptions | medication | VARCHAR(2000) | NOT NULL |
| prescriptions | issued_date | DATE | NOT NULL |

Relationships: `doctors` 1-to-many `appointments`; `patients` 1-to-many `appointments`; doctors and patients each 1-to-many prescriptions; `doctors` 1-to-many `doctor_available_times`. Account ownership is connected by the unique email in application logic, not a foreign key. The `appointments` table is indexed by `(doctor_id,appointment_time)` and `patient_id`.

## Example explicit MySQL DDL

```sql
CREATE TABLE IF NOT EXISTS doctors (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  specialty VARCHAR(255) NOT NULL,
  available_time VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE
);
CREATE TABLE IF NOT EXISTS patients (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  phone VARCHAR(30)
);
CREATE TABLE IF NOT EXISTS appointments (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  doctor_id BIGINT NOT NULL,
  patient_id BIGINT NOT NULL,
  appointment_time DATETIME(6) NOT NULL,
  status VARCHAR(255) NOT NULL,
  CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
  CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
  INDEX idx_appt_doctor_date(doctor_id,appointment_time),
  INDEX idx_appt_patient(patient_id)
);
CREATE TABLE IF NOT EXISTS prescriptions (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  doctor_id BIGINT NOT NULL,
  patient_id BIGINT NOT NULL,
  medication VARCHAR(2000) NOT NULL,
  issued_date DATE NOT NULL,
  CONSTRAINT fk_prescription_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
  CONSTRAINT fk_prescription_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);
CREATE TABLE IF NOT EXISTS doctor_available_times (
  doctor_id BIGINT NOT NULL,
  available_time VARCHAR(255) NOT NULL,
  CONSTRAINT fk_availability_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
CREATE TABLE IF NOT EXISTS user_accounts (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(255) NOT NULL
);
```

The app uses `spring.jpa.hibernate.ddl-auto=update` for educational/demo purposes. In production, prefer versioned migrations, `validate` and UTC time handling. A database-level unique reservation constraint or locking is needed to close a concurrent-booking race.
