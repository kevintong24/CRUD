package com.example.demo3;

import com.example.demo3.Dto.CourseDto;
import com.example.demo3.Entity.Course;
import com.example.demo3.Mapper.CourseMapper;
import com.example.demo3.Repository.CourseRepository;
import com.example.demo3.Service.CourseService;
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
public class CourseServiceTest {
    @Mock
    private CourseRepository repo;

    @Mock
    private CourseMapper mapper;

    @InjectMocks
    private CourseService service;

    // 1. getAll()
    @Test
    void testGetAllReturnsListOfCourseDto() {
        List<Course> courses = List.of(new Course(), new Course());
        List<CourseDto> dtos = List.of(new CourseDto(), new CourseDto());

        when(repo.findAll()).thenReturn(courses);
        when(mapper.toDtoList(courses)).thenReturn(dtos);

        List<CourseDto> result = service.getAll();

        assertEquals(2, result.size());
    }

    // 2. getById()
    @Test
    void testGetByIdReturnsCourseDto() {
        Long id = 1L;
        Course course = new Course();
        course.setId(id);
        course.setName("Java");

        CourseDto dto = new CourseDto();
        dto.setId(id);
        dto.setName("Java");

        when(repo.findById(id)).thenReturn(Optional.of(course));
        when(mapper.toDto(course)).thenReturn(dto);

        CourseDto result = service.getById(id);

        assertEquals("Java", result.getName());
    }

    @Test
    void testGetByIdThrowsWhenNotFound() {
        Long id = 99L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> service.getById(id));
    }

    // 3. create()
    @Test
    void testCreateSuccess() {
        CourseDto input = new CourseDto(); input.setName("Spring Boot");
        Course entity = new Course(); entity.setName("Spring Boot");
        Course saved = new Course(); saved.setId(1L); saved.setName("Spring Boot");
        CourseDto output = new CourseDto(); output.setId(1L); output.setName("Spring Boot");

        when(repo.findByName("Spring Boot")).thenReturn(Optional.empty());
        when(mapper.toEntity(input)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(output);

        CourseDto result = service.create(input);

        assertEquals("Spring Boot", result.getName());
        assertNotNull(result.getId());
    }

    @Test
    void testCreateThrowsIfNameExists() {
        CourseDto input = new CourseDto(); input.setName("Duplicate");
        when(repo.findByName("Duplicate")).thenReturn(Optional.of(new Course()));

        assertThrows(IllegalStateException.class, () -> service.create(input));
    }

    // 4. update()
    @Test
    void testUpdateSuccess() {
        Long id = 1L;
        Course existing = new Course(); existing.setId(id); existing.setName("Old");
        CourseDto input = new CourseDto(); input.setName("New");

        CourseDto resultDto = new CourseDto(); resultDto.setName("New");

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.findByName("New")).thenReturn(Optional.empty());
        when(repo.save(existing)).thenReturn(existing);
        when(mapper.toDto(existing)).thenReturn(resultDto);

        CourseDto result = service.update(id, input);

        assertEquals("New", result.getName());
    }

    @Test
    void testUpdateThrowsIfCourseNotFound() {
        Long id = 999L;
        CourseDto dto = new CourseDto();
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> service.update(id, dto));
    }

    @Test
    void testUpdateThrowsIfNameTaken() {
        Long id = 1L;
        Course existing = new Course(); existing.setId(id); existing.setName("Old");
        CourseDto input = new CourseDto(); input.setName("New");

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.findByName("New")).thenReturn(Optional.of(new Course()));

        assertThrows(IllegalStateException.class, () -> service.update(id, input));
    }

    // 5. delete()
    @Test
    void testDeleteSuccess() {
        Long id = 1L;
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }

    @Test
    void testDeleteThrowsIfNotFound() {
        Long id = 123L;
        when(repo.existsById(id)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> service.delete(id));
    }
}
