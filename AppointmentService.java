package com.project.back_end.services;
import com.project.back_end.models.*;
import com.project.back_end.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;import java.util.List;
@Service public class AppointmentService {
private final AppointmentRepository appointments;private final PatientRepository patients;private final DoctorRepository doctors;
public AppointmentService(AppointmentRepository a,PatientRepository p,DoctorRepository d){appointments=a;patients=p;doctors=d;}
public List<Appointment> doctor(Long id){return appointments.findByDoctorIdOrderByAppointmentTimeAsc(id);}
public List<Appointment> patient(Long id){return appointments.findByPatientIdOrderByAppointmentTimeAsc(id);}
@Transactional public Appointment book(Long patientId,Long doctorId,LocalDateTime at){
if(at==null||!at.isAfter(LocalDateTime.now()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Appointment must be in the future");
Patient p=patients.findById(patientId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found"));
Doctor d=doctors.findById(doctorId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found"));
if(appointments.existsByDoctorIdAndAppointmentTimeAndStatus(doctorId,at,"BOOKED"))throw new ResponseStatusException(HttpStatus.CONFLICT,"Slot already booked");
return appointments.save(new Appointment(d,p,at));}
}
