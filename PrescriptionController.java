package com.project.back_end.controllers;
import com.project.back_end.models.*;
import com.project.back_end.repo.*;
import com.project.back_end.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.Principal;
import java.util.*;
@RestController public class PrescriptionController {
 private final PrescriptionRepository prescriptions;private final DoctorRepository doctors;private final PatientRepository patients;private final TokenService tokens;
 public PrescriptionController(PrescriptionRepository r,DoctorRepository d,PatientRepository p,TokenService t){prescriptions=r;doctors=d;patients=p;tokens=t;}
 public record NewPrescription(Long patientId,String medication){}
 private Prescription save(Principal principal,Long patientId,String medication){
  var doctor=doctors.findByEmailIgnoreCase(principal.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found"));
  var patient=patients.findById(patientId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found"));
  if(medication==null||medication.isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Medication required");
  return prescriptions.save(new Prescription(doctor,patient,medication));
 }
 @PostMapping("/api/doctor/prescriptions") public ResponseEntity<Map<String,String>> create(Principal principal,@RequestBody NewPrescription req){
  Prescription result=save(principal,req.patientId(),req.medication());
  return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Prescription saved","prescriptionId",result.id.toString()));
 }
 @PostMapping("/api/doctor/prescriptions/{token}") public ResponseEntity<Map<String,String>> createWithToken(Principal principal,@PathVariable String token,@Valid @RequestBody Prescription request){
  if(principal==null||!principal.getName().equalsIgnoreCase(tokens.email(token))||!"DOCTOR".equals(tokens.role(token)))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid doctor token");
  if(request.patient==null||request.patient.id==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Patient ID required");
  Prescription saved=save(principal,request.patient.id,request.medication);
  return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Prescription saved successfully","prescriptionId",saved.id.toString()));
 }
 @GetMapping("/api/patient/prescriptions") public List<Prescription> myPrescriptions(Principal principal){var patient=patients.findByEmailIgnoreCase(principal.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found"));return prescriptions.findByPatientId(patient.id);}
}
