package com.example.demo3.Mapper;

import com.example.demo3.Dto.ReportDto;
import com.example.demo3.Entity.Course;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {


    // Maps course & count to CourseEnrollmentReportDto
    public ReportDto toReportDto(Course course, Long count, long total) {
        double percentage = (count * 100.0) / total;
        return new ReportDto(
                course.getId(),
                course.getName(),
                count,
                Math.round(percentage * 10.0) / 10.0
        );
    }
}

