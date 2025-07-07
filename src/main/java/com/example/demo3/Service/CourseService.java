package com.example.demo3.Service;

import com.example.demo3.Dto.CourseDto;
import com.example.demo3.Dto.StudentDto;
import com.example.demo3.Entity.Course;
import com.example.demo3.Mapper.CourseMapper;
import com.example.demo3.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseService {
    private final CourseRepository repo;
    private final CourseMapper mapper;

    @Autowired
    public CourseService(CourseRepository repo, CourseMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<CourseDto> getAll(){
        return mapper.toDtoList(repo.findAll());
    }

    public CourseDto getById(Long id){
        Course course = repo.findById(id).orElseThrow(() -> new IllegalStateException("Course ID " + id + " not found"));
        return mapper.toDto(course);
    }

    public CourseDto create(CourseDto dto){
        if(repo.findByName(dto.getName()).isPresent()){
            throw new IllegalStateException("Course already exist");
        }
        Course saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    public CourseDto update(Long id, CourseDto dto){
        Course existing = repo.findById(id).orElseThrow(()-> new IllegalStateException("Course ID " + id + " not found"));

        if(!existing.getName().equals(dto.getName()) && repo.findByName(dto.getName()).isPresent()){
            throw new IllegalStateException("Course name already exists");
        }

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());

        return mapper.toDto(repo.save(existing));
    }

    public void delete(Long id){
        if(!repo.existsById(id)){
            throw new IllegalStateException("Course ID " + id + " not found");
        }
        repo.deleteById(id);
    }


}
