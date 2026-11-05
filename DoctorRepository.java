package com.project.back_end.repo;
import com.project.back_end.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
 List<Doctor> findByNameContainingIgnoreCase(String name);
 List<Doctor> findBySpecialtyContainingIgnoreCaseAndAvailableTimeContainingIgnoreCase(String specialty,String time);
 Optional<Doctor> findByEmailIgnoreCase(String email);
}
