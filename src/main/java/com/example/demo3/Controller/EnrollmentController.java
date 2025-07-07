package com.example.demo3.Controller;

import com.example.demo3.Dto.EnrollmentDto;
import com.example.demo3.Service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService service;

    @Autowired
    public EnrollmentController(EnrollmentService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollment(){
        return ResponseEntity.ok(service.getAllEnrollments());
    }

    @PostMapping
    public ResponseEntity<EnrollmentDto> enroll(@Valid @RequestBody EnrollmentDto dto){
        EnrollmentDto result = service.enrollStudent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{enrollmentId}/reassign/{newCourseId}")
    public ResponseEntity<EnrollmentDto> reassign(@PathVariable Long enrollmentId, @PathVariable Long newCourseId){
        EnrollmentDto result = service.reassignStudent(enrollmentId, newCourseId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> withdraw(@RequestParam Long studentId, @RequestParam Long courseId){
        service.withdrawStudent(studentId, courseId);
        return ResponseEntity.noContent().build();
    }


}
