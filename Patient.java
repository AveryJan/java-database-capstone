package com.project.back_end.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity @Table(name="patients") public class Patient {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
@NotBlank @Column(nullable=false) public String name;
@NotBlank @Email @Column(nullable=false,unique=true) public String email;
public Patient(){} public Patient(String name,String email){this.name=name;this.email=email;}
}
