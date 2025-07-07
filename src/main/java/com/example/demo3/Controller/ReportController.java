package com.example.demo3.Controller;

import com.example.demo3.Dto.ReportDto;
import com.example.demo3.Dto.StudentDto;
import com.example.demo3.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<Object>> defaultReportLanding() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/course/{courseId}/students")
    public ResponseEntity<List<StudentDto>> getStudentsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(reportService.getStudentsByCourse(courseId));
    }

    @GetMapping("/course-percentages")
    public ResponseEntity<List<ReportDto>> getEnrollmentPercentages() {
        return ResponseEntity.ok(reportService.getEnrollmentPercentageReport());
    }
}
