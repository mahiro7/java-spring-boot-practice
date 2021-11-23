package com.gft.plannercrud.repositories;

import com.gft.plannercrud.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = "SELECT c FROM Course c WHERE c.code = :code")
    Course findCourseByCode(@Param("code") String code);
}
