package com.example.demo3.Service;

import com.example.demo3.Dto.EnrollmentDto;
import com.example.demo3.Entity.Course;
import com.example.demo3.Entity.Enrollment;
import com.example.demo3.Entity.Student;
import com.example.demo3.Mapper.EnrollmentMapper;
import com.example.demo3.Repository.CourseRepository;
import com.example.demo3.Repository.EnrollmentRepository;
import com.example.demo3.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final EnrollmentMapper mapper;

    @Autowired
    public EnrollmentService(StudentRepository studentRepo, CourseRepository courseRepo, EnrollmentRepository enrollmentRepo, EnrollmentMapper mapper){
        this.enrollmentRepo = enrollmentRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.mapper = mapper;

    }

    public List<EnrollmentDto> getAllEnrollments() {
        return mapper.toDtoList(enrollmentRepo.findAll());
    }

    public EnrollmentDto enrollStudent(EnrollmentDto dto){
        Student student = studentRepo.findById(dto.getStudentId()).orElseThrow(()-> new IllegalStateException("Student ID " + dto.getStudentId() + " not found"));
        Course course = courseRepo.findById(dto.getCourseId()).orElseThrow(()-> new IllegalStateException("Course ID " + dto.getCourseId() + " not found"));

        if(enrollmentRepo.findByStudentAndCourse(student, course).isPresent()){
            throw new IllegalStateException("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment(student, course, LocalDate.now());
        enrollment = enrollmentRepo.save(enrollment);
        return mapper.toDto(enrollment);
    }

    public EnrollmentDto reassignStudent(Long enrollmentId, Long newCourseId){
        Enrollment enrollment = enrollmentRepo.findById(enrollmentId).orElseThrow(()-> new IllegalStateException("Enrollment ID " + enrollmentId + " not found"));
        Course newCourse = courseRepo.findById(newCourseId).orElseThrow(()-> new IllegalStateException("Course ID " + newCourseId + " not found"));

        if(enrollmentRepo.findByStudentAndCourse(enrollment.getStudent(), newCourse).isPresent()){
            throw new IllegalStateException("Student is already enrolled in this course");

        }
        enrollment.setCourse(newCourse);
        enrollment = enrollmentRepo.save(enrollment);

        return mapper.toDto(enrollment);
    }

    public void withdrawStudent(Long studentId, Long courseId){
        Student student = studentRepo.findById(studentId).orElseThrow(()-> new IllegalStateException("Student ID " + studentId + " not found"));
        Course course = courseRepo.findById(courseId).orElseThrow(()-> new IllegalStateException("Course ID " + courseId + " not found"));
        Enrollment enrollment = enrollmentRepo.findByStudentAndCourse(student, course).orElseThrow(()-> new IllegalStateException("Enrollment not found for this student and course"));
        enrollmentRepo.delete(enrollment);
    }



}
