package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.repositories.WorkflowRepository;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflows")
public class WorkflowController {

    @Autowired
    private WorkflowRepository workflowRepository;

    @GetMapping("/all")
    public Iterable<Workflow> getAllWorkflows() {
        return workflowRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveDummyWorkflow(@RequestBody Workflow workflow) {
        workflowRepository.save(workflow);
        return new ResponseEntity<String>("Saved!", HttpStatus.OK);
    }
}
