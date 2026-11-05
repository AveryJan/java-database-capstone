# Resubmission guide — targeted to peer review feedback

**Q1** `user_stories.md` — Admin 5, Patient 6, Doctor 6 stories; acceptance criteria, priorities, story points.

**Q2** `schema-design.md` — MySQL field types and relationships; includes executable example DDL.

**Q3** `app/src/main/java/com/project/back_end/models/Doctor.java` — `@Entity`, `Long` ID, `@ElementCollection List<String> availableTimes`.

**Q4** `app/src/main/java/com/project/back_end/models/Appointment.java` — doctor/patient `@ManyToOne`, `@NotNull @Future LocalDateTime`.

**Q5** `app/src/main/java/com/project/back_end/controllers/DoctorController.java` — date-specific availability endpoint with principal/token checks and `ResponseEntity`.

**Q6** `app/src/main/java/com/project/back_end/services/AppointmentService.java` — doctor-and-date query.

**Q7** `app/src/main/java/com/project/back_end/controllers/PrescriptionController.java` — token path, validated prescription request, `ResponseEntity<Map<String,String>>`.

**Q8** `app/src/main/java/com/project/back_end/repo/PatientRepository.java` — email or phone lookup.

**Q9** `app/src/main/java/com/project/back_end/services/TokenService.java` — `generateToken(String email)` and private `getSigningKey()`.

**Q10** `app/src/main/java/com/project/back_end/services/DoctorService.java` — date-specific availability and doctor login.

**Q11** `Dockerfile` — multi-stage build with `mvn -B clean package -DskipTests`.

**Q12** `.github/workflows/compile-backend.yml` — push/PR and Java 17 compile. Workflow runs from `app` folder with root pom reference via wrapper forwarding.

**Q13–Q18** Keep your previously credited screenshots; this package does not replace them.

**Q19–Q23** Run `bash evidence/capture-sql.sh`; submit *actual* result tables/screenshots, not SQL code. `Q20` already earned full credit.

**Q24–Q26** Run `bash evidence/capture-api.sh`; submit the actual captured HTTP responses, not the curl commands.

### GitHub file URLs after uploading the ZIP content to the repo root

Use: `https://github.com/AveryJan/java-database-capstone/blob/main/<file-path>`.

### Caution

GitHub branch creation was denied by the connected account permissions, so these revised files have **not** been pushed to GitHub. The repository link remains the old version until you upload these files. Full credit depends on the grader and genuine runtime evidence; it cannot be guaranteed.
