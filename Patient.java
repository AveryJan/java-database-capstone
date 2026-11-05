package com.project.back_end.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity @Table(name="patients") public class Patient {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @NotBlank @Column(nullable=false) public String name;
 @Email @NotBlank @Column(nullable=false,unique=true) public String email;
 @Column(length=30) public String phone;
 public Patient(){}
 public Patient(String name,String email){this.name=name;this.email=email;}
}
