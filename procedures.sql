USE smart_clinic;
DELIMITER $$
DROP PROCEDURE IF EXISTS GetDailyAppointmentReportByDoctor$$
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN report_day DATE)
BEGIN
 SELECT d.id AS doctor_id,d.name AS doctor_name,DATE(a.appointment_time) AS appointment_day,COUNT(a.id) AS total_appointments
 FROM doctors d JOIN appointments a ON a.doctor_id=d.id
 WHERE DATE(a.appointment_time)=report_day AND a.status='BOOKED'
 GROUP BY d.id,d.name,DATE(a.appointment_time) ORDER BY total_appointments DESC,d.name;
END$$
DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByMonth$$
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN report_year INT,IN report_month INT)
BEGIN
 SELECT d.id AS doctor_id,d.name AS doctor_name,COUNT(DISTINCT a.patient_id) AS patients_seen
 FROM doctors d JOIN appointments a ON a.doctor_id=d.id
 WHERE YEAR(a.appointment_time)=report_year AND MONTH(a.appointment_time)=report_month AND a.status='BOOKED'
 GROUP BY d.id,d.name ORDER BY patients_seen DESC,d.id LIMIT 1;
END$$
DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByYear$$
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN report_year INT)
BEGIN
 SELECT d.id AS doctor_id,d.name AS doctor_name,COUNT(DISTINCT a.patient_id) AS patients_seen
 FROM doctors d JOIN appointments a ON a.doctor_id=d.id
 WHERE YEAR(a.appointment_time)=report_year AND a.status='BOOKED'
 GROUP BY d.id,d.name ORDER BY patients_seen DESC,d.id LIMIT 1;
END$$
DELIMITER ;
