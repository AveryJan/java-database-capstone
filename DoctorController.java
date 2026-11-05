package com.project.back_end.controllers;
import com.project.back_end.models.Doctor;
import com.project.back_end.services.*;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@RestController @RequestMapping("/api/doctors")
public class DoctorController {
 private final DoctorService service;
 private final TokenService tokens;
 public DoctorController(DoctorService service,TokenService tokens){this.service=service;this.tokens=tokens;}
 @GetMapping public List<Doctor> all(){return service.all();}
 @GetMapping("/{id}") public Doctor one(@PathVariable Long id){return service.one(id);}
 @GetMapping("/by-name") public List<Doctor> name(@RequestParam String name){return service.byName(name);}
 @GetMapping("/search") public List<Doctor> search(@RequestParam(defaultValue="") String specialty,@RequestParam(defaultValue="") String time){return service.search(specialty,time);}
 @GetMapping("/{doctorId}/availability/{date}/{token}") public ResponseEntity<Map<String,Object>> availableWithToken(Principal principal,@PathVariable Long doctorId,@PathVariable LocalDate date,@PathVariable String token){
  if(principal==null||!principal.getName().equalsIgnoreCase(tokens.email(token)))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid token");
  return ResponseEntity.ok(Map.of("doctorId",doctorId,"date",date.toString(),"availableTimes",service.availability(doctorId,date)));
 }
 @GetMapping("/{doctorId}/availability") public ResponseEntity<Map<String,Object>> availability(Principal principal,@PathVariable Long doctorId,@RequestParam LocalDate date){
  if(principal==null)throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication required");
  return ResponseEntity.ok(Map.of("doctorId",doctorId,"date",date.toString(),"availableTimes",service.availability(doctorId,date)));
 }
}
