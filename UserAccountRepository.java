package com.project.back_end.repo;
import com.project.back_end.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {Optional<UserAccount> findByEmailIgnoreCase(String email);}
