package com.project.back_end.repo;
import com.project.back_end.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PatientRepository extends JpaRepository<Patient,Long> {
 Optional<Patient> findByEmailIgnoreCase(String email);
 Optional<Patient> findByEmailIgnoreCaseOrPhone(String email,String phone);
}
