package com.project.back_end.repo;
import com.project.back_end.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;import java.util.Optional;
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
List<Doctor> findByNameContainingIgnoreCase(String name);
List<Doctor> findBySpecialtyContainingIgnoreCaseAndAvailableTimeContainingIgnoreCase(String specialty,String time);
Optional<Doctor> findByEmailIgnoreCase(String email);
}
