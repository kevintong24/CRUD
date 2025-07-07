package com.example.demo3.Controller;

import com.example.demo3.Dto.StudentDto;
import com.example.demo3.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;
    @Autowired
    public StudentController(StudentService service){
        this.service = service;
    }

    @GetMapping
    public List<StudentDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/by-email")
    public ResponseEntity<StudentDto> getByEmail(@RequestParam String email){
        if(!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")){
            throw new IllegalArgumentException("Email is invalid");
        }
        return ResponseEntity.ok(service.getByEmail(email));
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@Valid @RequestBody StudentDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @Valid @RequestBody StudentDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
