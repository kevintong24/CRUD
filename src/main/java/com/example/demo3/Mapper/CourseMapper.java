package com.example.demo3.Mapper;

import com.example.demo3.Dto.CourseDto;
import com.example.demo3.Dto.StudentDto;
import com.example.demo3.Entity.Course;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CourseMapper {

    public Course toEntity(CourseDto dto){
        Course course = new Course();
        course.setId(dto.getId());
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setStartDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());
        return course;
    }

    public CourseDto toDto(Course course){
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        dto.setStartDate(course.getStartDate());
        dto.setEndDate(course.getEndDate());
        return dto;
    }

    public List<CourseDto> toDtoList(List<Course> list){
        return list.stream().map(this::toDto).toList();
    }
}
