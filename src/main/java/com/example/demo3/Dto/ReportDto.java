package com.example.demo3.Dto;

public class ReportDto {
    private Long courseId;
    private String courseName;
    private Long enrollmentCount;
    private double percentage;

    public ReportDto(){}

    public ReportDto(Long courseId, String courseName, Long enrollmentCount, double percentage) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.enrollmentCount = enrollmentCount;
        this.percentage = percentage;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getEnrollmentCount() {
        return enrollmentCount;
    }

    public void setEnrollmentCount(Long enrollmentCount) {
        this.enrollmentCount = enrollmentCount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}

