package com.project.back_end.controllers;
import com.project.back_end.models.*;
import com.project.back_end.repo.*;
import com.project.back_end.services.DoctorService;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@RestController @RequestMapping("/api/admin") public class AdminController {
 private final DoctorService doctors;private final UserAccountRepository users;private final PasswordEncoder encoder;
 public AdminController(DoctorService d,UserAccountRepository u,PasswordEncoder e){doctors=d;users=u;encoder=e;}
 public record DoctorRequest(String name,String specialty,String availableTime,String email,String password){}
 @PostMapping("/doctors") @ResponseStatus(HttpStatus.CREATED) public Doctor add(@RequestBody DoctorRequest body){
  if(body.name()==null||body.name().isBlank()||body.specialty()==null||body.specialty().isBlank()||body.availableTime()==null||body.availableTime().isBlank()||body.email()==null||!body.email().contains("@")||body.password()==null||body.password().length()<10)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid doctor details");
  if(users.findByEmailIgnoreCase(body.email()).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"Account exists");
  Doctor d=doctors.add(new Doctor(body.name(),body.specialty(),body.availableTime(),body.email()));
  users.save(new UserAccount(body.email(),encoder.encode(body.password()),Role.DOCTOR));return d;
 }
}
