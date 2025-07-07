package com.example.demo3.Service;

import com.example.demo3.Dto.ReportDto;
import com.example.demo3.Dto.StudentDto;
import com.example.demo3.Entity.Course;
import com.example.demo3.Entity.Enrollment;
import com.example.demo3.Mapper.ReportMapper;
import com.example.demo3.Mapper.StudentMapper;
import com.example.demo3.Repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final EnrollmentRepository enrollmentRepo;
    private final StudentMapper studentMapper;
    private final ReportMapper reportMapper;

    @Autowired
    public ReportService(EnrollmentRepository enrollmentRepo,
                         StudentMapper studentMapper,
                         ReportMapper reportMapper) {
        this.enrollmentRepo = enrollmentRepo;
        this.studentMapper = studentMapper;
        this.reportMapper = reportMapper;
    }


    public List<StudentDto> getStudentsByCourse(Long courseId) {
        return enrollmentRepo.findByCourseId(courseId).stream()
                .map(e -> studentMapper.toDto(e.getStudent()))
                .toList();
    }


    public List<ReportDto> getEnrollmentPercentageReport() {
        List<Enrollment> allEnrollments = enrollmentRepo.findAll();
        long totalEnrollments = allEnrollments.size();

        if (totalEnrollments == 0) return Collections.emptyList();

        Map<Course, Long> enrollmentsByCourse = allEnrollments.stream()
                .collect(Collectors.groupingBy(Enrollment::getCourse, Collectors.counting()));

        return enrollmentsByCourse.entrySet().stream()
                .map(entry -> reportMapper.toReportDto(entry.getKey(), entry.getValue(), totalEnrollments))
                .toList();
    }
}
