USE smart_clinic;
SHOW TABLES;
SELECT * FROM patients LIMIT 5;
CALL GetDailyAppointmentReportByDoctor(CURDATE());
CALL GetDoctorWithMostPatientsByMonth(YEAR(CURDATE()),MONTH(CURDATE()));
CALL GetDoctorWithMostPatientsByYear(YEAR(CURDATE()));
