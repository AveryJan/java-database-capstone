package com.project.back_end.models;
import jakarta.persistence.*;
@Entity @Table(name="user_accounts") public class UserAccount {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
@Column(nullable=false,unique=true) public String email;
@Column(nullable=false) public String passwordHash;
@Enumerated(EnumType.STRING) @Column(nullable=false) public Role role;
public UserAccount() {} public UserAccount(String email,String passwordHash,Role role){this.email=email;this.passwordHash=passwordHash;this.role=role;}
}
