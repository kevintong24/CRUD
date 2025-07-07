package com.example.demo3;

import com.example.demo3.Dto.ReportDto;
import com.example.demo3.Dto.StudentDto;
import com.example.demo3.Entity.Course;
import com.example.demo3.Entity.Enrollment;
import com.example.demo3.Entity.Student;
import com.example.demo3.Mapper.ReportMapper;
import com.example.demo3.Mapper.StudentMapper;
import com.example.demo3.Repository.EnrollmentRepository;
import com.example.demo3.Service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @Mock
    private EnrollmentRepository enrollmentRepo;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private ReportMapper reportMapper;

    @InjectMocks
    private ReportService reportService;

    @Test
    void testGetStudentsByCourse() {
        Long courseId = 10L;

        // Mock Enrollment with Student
        Student student = new Student();
        student.setId(1L);
        student.setName("Alice");

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);

        StudentDto dto = new StudentDto();
        dto.setId(1L);
        dto.setName("Alice");

        when(enrollmentRepo.findByCourseId(courseId)).thenReturn(List.of(enrollment));
        when(studentMapper.toDto(student)).thenReturn(dto);

        List<StudentDto> result = reportService.getStudentsByCourse(courseId);

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void testEnrollmentPercentageReport() {
        Course course = new Course();
        course.setId(100L);
        course.setName("Java");

        Enrollment e1 = new Enrollment();
        e1.setCourse(course);

        Enrollment e2 = new Enrollment();
        e2.setCourse(course);

        List<Enrollment> allEnrollments = List.of(e1, e2);

        ReportDto reportDto = new ReportDto();
        reportDto.setCourseId(100L);
        reportDto.setCourseName("Java");
        reportDto.setEnrollmentCount(2L);
        reportDto.setPercentage(100.0);

        when(enrollmentRepo.findAll()).thenReturn(allEnrollments);
        when(reportMapper.toReportDto(course, 2L, 2L)).thenReturn(reportDto);

        List<ReportDto> result = reportService.getEnrollmentPercentageReport();
        // Check that the result list contains exactly one report entry.
        assertEquals(1, result.size());
        // Check that the course ID in the report is correct.
        assertEquals(100L, result.get(0).getCourseId());
        // Check that the calculated percentage in the report is correct.
        assertEquals(100.0, result.get(0).getPercentage());
    }
}
