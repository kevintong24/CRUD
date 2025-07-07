package com.example.demo3.Dto;

import jakarta.validation.constraints.NotNull;
import org.aspectj.bridge.IMessage;

import java.time.LocalDate;

public class EnrollmentDto {
    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private String studentName;

    private String courseName;

    private LocalDate enrollAt;

    public EnrollmentDto(){}

    public EnrollmentDto(Long id, Long studentId, String studentName, Long courseId, String courseName, LocalDate enrollAt) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.studentName = studentName;
        this.courseName = courseName;
        this.enrollAt = enrollAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getEnrollAt() {
        return enrollAt;
    }

    public void setEnrollAt(LocalDate enrollAt) {
        this.enrollAt = enrollAt;
    }
}
