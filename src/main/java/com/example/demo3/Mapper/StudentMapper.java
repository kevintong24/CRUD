package com.example.demo3.Mapper;

import com.example.demo3.Dto.StudentDto;
import com.example.demo3.Entity.Student;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class StudentMapper {

    public Student toEntity(StudentDto dto) {
        Student s = new Student();
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        s.setDob(dto.getDob());
        s.setGender(dto.getGender());
        s.setPhone(dto.getPhone());
        return s;
    }

    public StudentDto toDto(Student s) {
        StudentDto dto = new StudentDto();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setEmail(s.getEmail());
        dto.setDob(s.getDob());
        dto.setGender(s.getGender());
        dto.setPhone(s.getPhone());
        return dto;
    }

    public List<StudentDto> toDtoList(List<Student> list) {
        return list.stream().map(this::toDto).toList();
    }
}
