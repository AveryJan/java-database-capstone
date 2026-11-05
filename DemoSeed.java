package com.project.back_end.config;
import com.project.back_end.models.*;
import com.project.back_end.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.*;
@Configuration public class DemoSeed {
 @Bean @Profile("demo") CommandLineRunner seed(UserAccountRepository accounts,PatientRepository patients,DoctorRepository doctors,AppointmentRepository appointments,PasswordEncoder encoder){return args->{
  if(accounts.findByEmailIgnoreCase("admin@clinic.test").isPresent())return;
  accounts.save(new UserAccount("admin@clinic.test",encoder.encode("DemoAdmin123!"),Role.ADMIN));
  Doctor d=doctors.save(new Doctor("Dr. Maya Chen","Cardiology","09:00","maya@clinic.test"));
  accounts.save(new UserAccount("maya@clinic.test",encoder.encode("DemoDoctor123!"),Role.DOCTOR));
  Patient p=patients.save(new Patient("Jordan Lee","jordan@clinic.test"));
  accounts.save(new UserAccount("jordan@clinic.test",encoder.encode("DemoPatient123!"),Role.PATIENT));
  appointments.save(new Appointment(d,p,LocalDateTime.now().withHour(23).withMinute(59).withSecond(0).withNano(0)));
  appointments.save(new Appointment(d,p,LocalDateTime.now().plusDays(2).withHour(10).withMinute(0).withSecond(0).withNano(0)));
 };}
}
