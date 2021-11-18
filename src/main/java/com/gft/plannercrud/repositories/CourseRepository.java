package com.gft.plannercrud.repositories;

import com.gft.plannercrud.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
