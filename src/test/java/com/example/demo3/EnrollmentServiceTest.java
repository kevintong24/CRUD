package com.example.demo3;

import com.example.demo3.Dto.EnrollmentDto;
import com.example.demo3.Entity.Course;
import com.example.demo3.Entity.Enrollment;
import com.example.demo3.Entity.Student;
import com.example.demo3.Mapper.EnrollmentMapper;
import com.example.demo3.Repository.CourseRepository;
import com.example.demo3.Repository.EnrollmentRepository;
import com.example.demo3.Repository.StudentRepository;
import com.example.demo3.Service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @Mock
    private StudentRepository studentRepo;
    @Mock
    private CourseRepository courseRepo;
    @Mock
    private EnrollmentRepository enrollmentRepo;
    @Mock
    private EnrollmentMapper mapper;

    @InjectMocks private EnrollmentService service;

    private Student student;
    private Course course;
    private Enrollment enrollment;



    @Test
    void testGetAllEnrollments() {

        student = new Student();
        student.setId(1L);
        student.setName("Alice");

        course = new Course();
        course.setId(10L);
        course.setName("Java");

        enrollment = new Enrollment(student, course, LocalDate.now());
        enrollment.setId(100L);

        List<Enrollment> list = List.of(enrollment);
        EnrollmentDto dto = new EnrollmentDto();
        when(enrollmentRepo.findAll()).thenReturn(list);
        when(mapper.toDtoList(list)).thenReturn(List.of(dto));

        List<EnrollmentDto> result = service.getAllEnrollments();
        assertEquals(1, result.size());
    }

    @Test
    void testEnrollStudentSuccess() {

        student = new Student();
        student.setId(1L);
        student.setName("Alice");

        course = new Course();
        course.setId(10L);
        course.setName("Java");

        enrollment = new Enrollment(student, course, LocalDate.now());
        enrollment.setId(100L);

        EnrollmentDto inputDto = new EnrollmentDto();
        inputDto.setStudentId(1L);
        inputDto.setCourseId(10L);

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(10L)).thenReturn(Optional.of(course));
        when(enrollmentRepo.findByStudentAndCourse(student, course)).thenReturn(Optional.empty());
        when(enrollmentRepo.save(any())).thenReturn(enrollment);

        EnrollmentDto outputDto = new EnrollmentDto();
        when(mapper.toDto(enrollment)).thenReturn(outputDto);

        EnrollmentDto result = service.enrollStudent(inputDto);
        assertNotNull(result);
    }

    @Test
    void testEnrollStudentThrowsIfStudentNotFound() {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setStudentId(1L);
        when(studentRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> service.enrollStudent(dto));
    }

    @Test
    void testEnrollStudentThrowsIfAlreadyEnrolled() {

        student = new Student();
        student.setId(1L);
        student.setName("Alice");

        course = new Course();
        course.setId(10L);
        course.setName("Java");

        enrollment = new Enrollment(student, course, LocalDate.now());
        enrollment.setId(100L);
        EnrollmentDto dto = new EnrollmentDto();
        dto.setStudentId(1L);
        dto.setCourseId(10L);

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(10L)).thenReturn(Optional.of(course));
        when(enrollmentRepo.findByStudentAndCourse(student, course)).thenReturn(Optional.of(enrollment));

        assertThrows(IllegalStateException.class, () -> service.enrollStudent(dto));
    }

    @Test
    void testReassignStudentSuccess() {
        Long newCourseId = 20L;

        student = new Student();
        student.setId(1L);
        student.setName("Alice");

        course = new Course();
        course.setId(10L);
        course.setName("Java");

        enrollment = new Enrollment(student, course, LocalDate.now());
        enrollment.setId(100L);

        Course newCourse = new Course();
        newCourse.setId(newCourseId);
        newCourse.setName("Spring");

        when(enrollmentRepo.findById(100L)).thenReturn(Optional.of(enrollment));
        when(courseRepo.findById(newCourseId)).thenReturn(Optional.of(newCourse));
        when(enrollmentRepo.findByStudentAndCourse(student, newCourse)).thenReturn(Optional.empty());
        when(enrollmentRepo.save(enrollment)).thenReturn(enrollment);

        EnrollmentDto dto = new EnrollmentDto();
        when(mapper.toDto(enrollment)).thenReturn(dto);

        EnrollmentDto result = service.reassignStudent(100L, newCourseId);
        assertNotNull(result);
    }

    @Test
    void testWithdrawStudentSuccess() {

        student = new Student();
        student.setId(1L);
        student.setName("Alice");

        course = new Course();
        course.setId(10L);
        course.setName("Java");

        enrollment = new Enrollment(student, course, LocalDate.now());
        enrollment.setId(100L);

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(10L)).thenReturn(Optional.of(course));
        when(enrollmentRepo.findByStudentAndCourse(student, course)).thenReturn(Optional.of(enrollment));

        service.withdrawStudent(1L, 10L);

        verify(enrollmentRepo).delete(enrollment);
    }

    @Test
    void testWithdrawStudentThrowsIfNotFound() {
        student = new Student();
        student.setId(1L);
        student.setName("Alice");

        course = new Course();
        course.setId(10L);
        course.setName("Java");

        enrollment = new Enrollment(student, course, LocalDate.now());
        enrollment.setId(100L);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(10L)).thenReturn(Optional.of(course));
        when(enrollmentRepo.findByStudentAndCourse(student, course)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> service.withdrawStudent(1L, 10L));
    }
}
