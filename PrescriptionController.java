package com.project.back_end.controllers;
import com.project.back_end.models.*;import com.project.back_end.repo.*;
import org.springframework.web.bind.annotation.*;import org.springframework.http.*;import org.springframework.web.server.ResponseStatusException;
import java.security.Principal;import java.util.List;
@RestController public class PrescriptionController {
private final PrescriptionRepository prescriptions;private final DoctorRepository doctors;private final PatientRepository patients;
public PrescriptionController(PrescriptionRepository r,DoctorRepository d,PatientRepository p){prescriptions=r;doctors=d;patients=p;}
public record NewPrescription(Long patientId,String medication){}
@PostMapping("/api/doctor/prescriptions") @ResponseStatus(HttpStatus.CREATED) public Prescription create(Principal principal,@RequestBody NewPrescription req){var doctor=doctors.findByEmailIgnoreCase(principal.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found"));var patient=patients.findById(req.patientId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found"));if(req.medication()==null||req.medication().isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Medication required");return prescriptions.save(new Prescription(doctor,patient,req.medication()));}
@GetMapping("/api/patient/prescriptions") public List<Prescription> myPrescriptions(Principal principal){var patient=patients.findByEmailIgnoreCase(principal.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found"));return prescriptions.findByPatientId(patient.id);}
}
