package com.example.demo3.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "enrollment", uniqueConstraints = {@UniqueConstraint( columnNames = { "student_id ", "course_id"})})
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(nullable = false)
    private LocalDate enrolledAt;

    public Enrollment(){}

    public Enrollment(Student student, Course course, LocalDate enrollAt) {
        this.student = student;
        this.course = course;
        this.enrolledAt = enrollAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrollAt() {
        return enrolledAt;
    }

    public void setEnrollAt(LocalDate enrollAt) {
        this.enrolledAt = enrollAt;
    }
}
