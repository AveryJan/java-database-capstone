package com.project.back_end.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
@Entity @Table(name="prescriptions") public class Prescription {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) @JoinColumn(name="doctor_id") public Doctor doctor;
 @ManyToOne(optional=false) @JoinColumn(name="patient_id") public Patient patient;
 @NotBlank @Column(nullable=false,length=2000) public String medication;
 @Column(nullable=false) public LocalDate issuedDate=LocalDate.now();
 public Prescription(){}
 public Prescription(Doctor doctor,Patient patient,String medication){this.doctor=doctor;this.patient=patient;this.medication=medication;}
}
