package com.example.demo3.Mapper;

import com.example.demo3.Dto.EnrollmentDto;
import com.example.demo3.Entity.Enrollment;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class EnrollmentMapper {

    public EnrollmentDto toDto(Enrollment e) {
        return new EnrollmentDto(
                e.getId(),
                e.getStudent().getId(),
                e.getStudent().getName(),
                e.getCourse().getId(),
                e.getCourse().getName(),
                e.getEnrollAt()
        );
    }

    public List<EnrollmentDto> toDtoList(List<Enrollment> list) {
        return list.stream().map(this::toDto).toList();
    }
}
