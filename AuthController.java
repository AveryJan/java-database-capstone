package com.project.back_end.controllers;
import com.project.back_end.models.*;
import com.project.back_end.repo.*;
import com.project.back_end.services.TokenService;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
@RestController @RequestMapping("/api/auth") public class AuthController {
 private final UserAccountRepository users;private final PatientRepository patients;private final PasswordEncoder encoder;private final TokenService tokens;
 public AuthController(UserAccountRepository u,PatientRepository p,PasswordEncoder e,TokenService t){users=u;patients=p;encoder=e;tokens=t;}
 public record Login(String email,String password){} public record Register(String name,String email,String password){}
 @PostMapping("/login") public Map<String,String> login(@RequestBody Login body){
  if(body.email()==null||body.password()==null)throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials");
  UserAccount u=users.findByEmailIgnoreCase(body.email()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials"));
  if(!encoder.matches(body.password(),u.passwordHash))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials");
  return Map.of("token",tokens.issue(u.email,u.role.name()),"role",u.role.name(),"email",u.email);
 }
 @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) public Map<String,String> register(@RequestBody Register body){
  if(body.name()==null||body.name().isBlank()||body.email()==null||!body.email().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")||body.password()==null||body.password().length()<10)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Provide name, valid email, password (10+ chars)");
  String email=body.email().trim().toLowerCase();if(users.findByEmailIgnoreCase(email).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"Account exists");
  patients.save(new Patient(body.name().trim(),email));users.save(new UserAccount(email,encoder.encode(body.password()),Role.PATIENT));return Map.of("message","Patient registered");
 }
}
