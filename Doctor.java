package com.project.back_end.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.*;
@Entity @Table(name="doctors")
public class Doctor {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @NotBlank @Column(nullable=false) public String name;
 @NotBlank @Column(nullable=false) public String specialty;
 @NotBlank @Column(nullable=false) public String availableTime;
 @ElementCollection(fetch=FetchType.EAGER)
 @CollectionTable(name="doctor_available_times",joinColumns=@JoinColumn(name="doctor_id"))
 @Column(name="available_time",nullable=false) public List<String> availableTimes=new ArrayList<>();
 @Email @Column(unique=true) public String email;
 public Doctor(){}
 public Doctor(String name,String specialty,String availableTime,String email){this.name=name;this.specialty=specialty;this.availableTime=availableTime;this.email=email;this.availableTimes=new ArrayList<>(List.of(availableTime));}
}
