package com.gft.plannercrud.repositories;

import com.gft.plannercrud.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
