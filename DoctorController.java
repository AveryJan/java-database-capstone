package com.project.back_end.controllers;
import com.project.back_end.models.Doctor;import com.project.back_end.services.DoctorService;
import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController @RequestMapping("/api/doctors") public class DoctorController {
private final DoctorService service;public DoctorController(DoctorService service){this.service=service;}
@GetMapping public List<Doctor> all(){return service.all();}
@GetMapping("/{id}") public Doctor one(@PathVariable Long id){return service.one(id);}
@GetMapping("/by-name") public List<Doctor> name(@RequestParam String name){return service.byName(name);}
@GetMapping("/search") public List<Doctor> search(@RequestParam(defaultValue="") String specialty,@RequestParam(defaultValue="") String time){return service.search(specialty,time);}
}
