package com.gft.plannercrud.services;

import com.gft.plannercrud.entities.Course;
import com.gft.plannercrud.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }

    public boolean courseCodeExists(String code) {
        return courseRepository.findCourseByCode(code) != null;
    }

    public Course findById(Long id) throws Exception {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            throw new Exception("Disciplina não encontrada!");
        }

        return course.get();
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

}
