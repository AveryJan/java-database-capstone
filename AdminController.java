package com.project.back_end.controllers;
import com.project.back_end.models.*;import com.project.back_end.repo.UserAccountRepository;import com.project.back_end.services.DoctorService;
import org.springframework.http.*;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@RestController @RequestMapping("/api/admin") public class AdminController {
private final DoctorService doctors;private final UserAccountRepository users;private final PasswordEncoder encoder;
public AdminController(DoctorService d,UserAccountRepository u,PasswordEncoder e){doctors=d;users=u;encoder=e;}
public record NewDoctor(String name,String specialty,String availableTime,String email,String password){}
@PostMapping("/doctors") @ResponseStatus(HttpStatus.CREATED) public Doctor add(@RequestBody NewDoctor body){if(body.name()==null||body.name().isBlank()||body.specialty()==null||body.specialty().isBlank()||body.availableTime()==null||body.availableTime().isBlank()||body.email()==null||!body.email().contains("@")||body.password()==null||body.password().length()<10)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Valid doctor details and 10+ character password required");String email=body.email().trim().toLowerCase();if(users.findByEmailIgnoreCase(email).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"Account exists");Doctor doctor=doctors.add(new Doctor(body.name(),body.specialty(),body.availableTime(),email));users.save(new UserAccount(email,encoder.encode(body.password()),Role.DOCTOR));return doctor;}
}
