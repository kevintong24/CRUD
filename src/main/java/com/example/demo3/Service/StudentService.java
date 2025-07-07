package com.example.demo3.Service;

import com.example.demo3.Dto.StudentDto;
import com.example.demo3.Entity.Student;
import com.example.demo3.Mapper.StudentMapper;
import com.example.demo3.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    private final StudentMapper mapper;

    public StudentService(StudentRepository repo, StudentMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<StudentDto> getAll() { return mapper.toDtoList(repo.findAll());}

    public StudentDto getById(Long id){
        Student student = repo.findById(id).orElseThrow(() -> new IllegalStateException("Student ID " +id+ " not found") );
        return mapper.toDto(student);
    }

    public StudentDto getByEmail(String email){
        Student student = repo.findByEmail(email).orElseThrow(() -> new IllegalStateException("Student with email " + email + " not found"));
        return mapper.toDto(student);
    }

    public StudentDto create(StudentDto dto){
        if(repo.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalStateException("Email is already registered");
        }
        Student student = mapper.toEntity(dto);
        return mapper.toDto(repo.save(student));
    }

    public StudentDto update(Long id, StudentDto dto){
        Student existing = repo.findById(id).orElseThrow(()-> new IllegalStateException("Student ID" +id+ "not found"));
        if(!existing.getEmail().equals(dto.getEmail()) && repo.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalStateException("Email already taken");
        }

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setDob(dto.getDob());
        existing.setGender(dto.getGender());
        existing.setPhone(dto.getPhone());

        return mapper.toDto(repo.save(existing));
    }

    public void delete(Long id){
        if(!repo.existsById(id)){
            throw new IllegalStateException("Student ID " + id + " not found");
        }
        repo.deleteById(id);
    }



}
