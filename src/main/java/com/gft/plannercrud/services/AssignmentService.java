package com.gft.plannercrud.services;

import com.gft.plannercrud.entities.Assignment;
import com.gft.plannercrud.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignment getAssignmentById(Long id) throws Exception {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        if (assignment.isEmpty()) {
            throw new Exception("Tarefa não encontrada!");
        }

        return assignment.get();
    }

    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> listAssignments() {
        return assignmentRepository.findAll();
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

}
