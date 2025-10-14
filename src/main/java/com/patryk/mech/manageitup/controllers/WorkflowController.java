package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.project.DTO.WorkflowCreateRequest;
import com.patryk.mech.manageitup.repositories.WorkflowRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflows")
@Tag(name = "Workflow Management", description = "APIs for managing project workflows")
public class WorkflowController {

    private WorkflowRepository workflowRepository;

    private EntityManager entityManager;

    public WorkflowController(EntityManager entityManager, WorkflowRepository workflowRepository) {
        this.entityManager = entityManager;
        this.workflowRepository = workflowRepository;
    }

    @GetMapping("/all")
    public Iterable<Workflow> getAllWorkflows() {
        return workflowRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveDummyWorkflow(@RequestBody WorkflowCreateRequest workflow) {
        if(workflow != null) {
            workflowRepository.save(workflow.asWorkflow(entityManager));
            return new ResponseEntity<String>("Saved!", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }
}
