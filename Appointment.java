package com.project.back_end.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
@Entity @Table(name="appointments",indexes={@Index(name="idx_appt_doctor_date",columnList="doctor_id,appointment_time"),@Index(name="idx_appt_patient",columnList="patient_id")})
public class Appointment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) @JoinColumn(name="doctor_id") public Doctor doctor;
 @ManyToOne(optional=false) @JoinColumn(name="patient_id") public Patient patient;
 @NotNull @Future @Column(nullable=false) public LocalDateTime appointmentTime;
 @Column(nullable=false) public String status="BOOKED";
 public Appointment(){}
 public Appointment(Doctor doctor,Patient patient,LocalDateTime appointmentTime){this.doctor=doctor;this.patient=patient;this.appointmentTime=appointmentTime;}
}
