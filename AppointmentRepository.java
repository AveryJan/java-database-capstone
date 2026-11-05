package com.project.back_end.repo;
import com.project.back_end.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
 List<Appointment> findByDoctorIdOrderByAppointmentTimeAsc(Long doctorId);
 List<Appointment> findByPatientIdOrderByAppointmentTimeAsc(Long patientId);
 List<Appointment> findByDoctorIdAndAppointmentTimeGreaterThanEqualAndAppointmentTimeLessThanOrderByAppointmentTimeAsc(Long doctorId,LocalDateTime start,LocalDateTime end);
 boolean existsByDoctorIdAndAppointmentTimeAndStatus(Long doctorId,LocalDateTime time,String status);
}
