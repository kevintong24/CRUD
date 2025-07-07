package com.example.demo3;

import com.example.demo3.Dto.StudentDto;
import com.example.demo3.Entity.Student;
import com.example.demo3.Mapper.CourseMapper;
import com.example.demo3.Mapper.StudentMapper;
import com.example.demo3.Repository.CourseRepository;
import com.example.demo3.Repository.StudentRepository;
import com.example.demo3.Service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    //Mock dependencies
    @Mock
    private StudentRepository repo;

    @Mock
    private StudentMapper mapper;

    @InjectMocks
    private StudentService service;

    // 1. getAll()
    @Test
    void testGetAllReturnsListOfStudentDto() {
        //simulate db contain 2 data
        List<Student> students = List.of(new Student(), new Student());
        List<StudentDto> dtos = List.of(new StudentDto(), new StudentDto());

        when(repo.findAll()).thenReturn(students);
        when(mapper.toDtoList(students)).thenReturn(dtos);

        List<StudentDto> result = service.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void testGetByIdReturnsStudentDto() {
        // Arrange: setup test data
        Long id = 1L;
        Student student = new Student();
        student.setId(id);
        student.setName("Alice");

        StudentDto dto = new StudentDto();
        dto.setId(id);
        dto.setName("Alice");

        // Mock behavior
        when(repo.findById(id)).thenReturn(Optional.of(student));
        when(mapper.toDto(student)).thenReturn(dto);

        // Act: call the method
        StudentDto result = service.getById(id);

        // Assert: verify result
        assertEquals("Alice", result.getName());

    }

    @Test
    void testGetByIdThrowsWhenNotFound() {
        Long id = 99L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> service.getById(id));
    }

    // 3. getByEmail()
    @Test
    void testGetByEmailReturnsStudentDto() {
        String email = "test@mail.com";
        Student student = new Student(); student.setEmail(email); student.setName("Test");
        StudentDto dto = new StudentDto(); dto.setEmail(email); dto.setName("Test");

        when(repo.findByEmail(email)).thenReturn(Optional.of(student));
        when(mapper.toDto(student)).thenReturn(dto);

        StudentDto result = service.getByEmail(email);

        assertEquals("Test", result.getName());
    }

    @Test
    void testGetByEmailThrowsWhenNotFound() {
        String email = "not@found.com";
        when(repo.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> service.getByEmail(email));
    }

    // 4. create()
    @Test
    void testCreateSuccess() {
        StudentDto input = new StudentDto(); input.setEmail("a@mail.com"); input.setName("New");
        Student entity = new Student(); entity.setEmail("a@mail.com");
        Student saved = new Student(); saved.setId(1L); saved.setEmail("a@mail.com");
        StudentDto output = new StudentDto(); output.setId(1L); output.setEmail("a@mail.com");

        when(repo.findByEmail(input.getEmail())).thenReturn(Optional.empty());
        when(mapper.toEntity(input)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(output);

        StudentDto result = service.create(input);

        assertEquals("a@mail.com", result.getEmail());
        assertNotNull(result.getId());
    }

    @Test
    void testCreateThrowsIfEmailExists() {
        StudentDto input = new StudentDto(); input.setEmail("a@mail.com");
        when(repo.findByEmail("a@mail.com")).thenReturn(Optional.of(new Student()));

        assertThrows(IllegalStateException.class, () -> service.create(input));
    }

    // 5. update()
    @Test
    void testUpdateSuccess() {
        Long id = 1L;
        Student existing = new Student(); existing.setId(id); existing.setEmail("old@mail.com");
        StudentDto input = new StudentDto(); input.setEmail("new@mail.com"); input.setName("New");

        StudentDto resultDto = new StudentDto(); resultDto.setEmail("new@mail.com"); resultDto.setName("New");

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.findByEmail("new@mail.com")).thenReturn(Optional.empty());
        when(repo.save(existing)).thenReturn(existing);
        when(mapper.toDto(existing)).thenReturn(resultDto);

        StudentDto result = service.update(id, input);

        assertEquals("new@mail.com", result.getEmail());
    }

    @Test
    void testUpdateThrowsIfStudentNotFound() {
        Long id = 999L;
        StudentDto dto = new StudentDto();
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> service.update(id, dto));
    }

    @Test
    void testUpdateThrowsIfEmailTaken() {
        Long id = 1L;
        Student existing = new Student(); existing.setId(id); existing.setEmail("old@mail.com");
        StudentDto input = new StudentDto(); input.setEmail("new@mail.com");

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.findByEmail("new@mail.com")).thenReturn(Optional.of(new Student()));

        assertThrows(IllegalStateException.class, () -> service.update(id, input));
    }

    // 6. delete()
    @Test
    void testDeleteSuccess() {
        Long id = 1L;
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }

    @Test
    void testDeleteThrowsIfNotFound() {
        Long id = 999L;
        when(repo.existsById(id)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> service.delete(id));
    }
}

