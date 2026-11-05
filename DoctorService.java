package com.project.back_end.services;
import com.project.back_end.models.*;
import com.project.back_end.repo.*;
import java.time.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service public class DoctorService {
 private final DoctorRepository doctors;
 private final AppointmentRepository appointments;
 private final UserAccountRepository users;
 private final PasswordEncoder encoder;
 private final TokenService tokens;
 public DoctorService(DoctorRepository doctors,AppointmentRepository appointments,UserAccountRepository users,PasswordEncoder encoder,TokenService tokens){this.doctors=doctors;this.appointments=appointments;this.users=users;this.encoder=encoder;this.tokens=tokens;}
 public List<Doctor> all(){return doctors.findAll();}
 public Doctor one(Long id){return doctors.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found"));}
 public List<Doctor> byName(String name){return doctors.findByNameContainingIgnoreCase(name);}
 public List<Doctor> search(String specialty,String time){return doctors.findBySpecialtyContainingIgnoreCaseAndAvailableTimeContainingIgnoreCase(specialty,time);}
 public Doctor add(Doctor doctor){doctor.id=null;return doctors.save(doctor);}
 public List<String> availability(Long doctorId,LocalDate date){
  Doctor d=one(doctorId);
  List<Appointment> taken=appointments.findByDoctorIdAndAppointmentTimeGreaterThanEqualAndAppointmentTimeLessThanOrderByAppointmentTimeAsc(doctorId,date.atStartOfDay(),date.plusDays(1).atStartOfDay());
  Set<String> booked=new HashSet<>();for(Appointment a:taken)if("BOOKED".equals(a.status))booked.add(a.appointmentTime.toLocalTime().toString());
  List<String> result=new ArrayList<>();for(String slot:d.availableTimes)if(!booked.contains(slot))result.add(slot);
  return result;
 }
 public String login(String email,String password){UserAccount user=users.findByEmailIgnoreCase(email).filter(u->u.role==Role.DOCTOR).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid doctor credentials"));if(!encoder.matches(password,user.passwordHash))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid doctor credentials");return tokens.issue(user.email,user.role.name());}
}
