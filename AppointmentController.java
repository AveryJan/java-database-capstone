package com.project.back_end.controllers;
import com.project.back_end.models.*;import com.project.back_end.repo.*;import com.project.back_end.services.AppointmentService;
import java.time.LocalDateTime;import java.security.Principal;import java.util.List;
import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import org.springframework.web.server.ResponseStatusException;
@RestController public class AppointmentController {
private final AppointmentService appointments;private final DoctorRepository doctors;private final PatientRepository patients;
public AppointmentController(AppointmentService a,DoctorRepository d,PatientRepository p){appointments=a;doctors=d;patients=p;}
public record Booking(Long doctorId,LocalDateTime appointmentTime){}
@GetMapping("/api/doctor/appointments") public List<Appointment> doctor(Principal principal){var d=doctors.findByEmailIgnoreCase(principal.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor profile not found"));return appointments.doctor(d.id);}
@GetMapping("/api/patient/appointments") public List<Appointment> patient(Principal principal){var p=patients.findByEmailIgnoreCase(principal.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient profile not found"));return appointments.patient(p.id);}
@PostMapping("/api/patient/appointments") @ResponseStatus(HttpStatus.CREATED) public Appointment book(Principal principal,@RequestBody Booking body){var p=patients.findByEmailIgnoreCase(principal.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found"));return appointments.book(p.id,body.doctorId(),body.appointmentTime());}
}
