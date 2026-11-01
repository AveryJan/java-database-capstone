package com.project.back_end.services;
import com.project.back_end.models.Doctor;
import com.project.back_end.repo.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
@Service public class DoctorService {
private final DoctorRepository doctors;
public DoctorService(DoctorRepository doctors){this.doctors=doctors;}
public List<Doctor> all(){return doctors.findAll();}
public Doctor one(Long id){return doctors.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found"));}
public List<Doctor> byName(String name){return doctors.findByNameContainingIgnoreCase(name);}
public List<Doctor> search(String specialty,String time){return doctors.findBySpecialtyContainingIgnoreCaseAndAvailableTimeContainingIgnoreCase(specialty,time);}
public Doctor add(Doctor doctor){doctor.id=null;return doctors.save(doctor);}
}
