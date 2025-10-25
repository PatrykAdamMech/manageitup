package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.common.GenericOptionsResponse;
import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantResponse;
import com.patryk.mech.manageitup.models.project.DTO.WorkflowCreateRequest;
import com.patryk.mech.manageitup.models.project.DTO.WorkflowOptionProjection;
import com.patryk.mech.manageitup.models.project.DTO.WorkflowResponse;
import com.patryk.mech.manageitup.repositories.WorkflowRepository;
import com.patryk.mech.manageitup.services.WorkflowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/workflows")
@Tag(name = "Workflow Management", description = "APIs for managing project workflows")
public class WorkflowController {

    private WorkflowRepository workflowRepository;
    private WorkflowService workflowService;

    private EntityManager entityManager;

    public WorkflowController(EntityManager entityManager, WorkflowRepository workflowRepository, WorkflowService workflowService) {
        this.entityManager = entityManager;
        this.workflowRepository = workflowRepository;
        this.workflowService = workflowService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkflowResponse>> getAllWorkflows() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(workflowRepository.findAll()
                        .stream()
                        .map(WorkflowResponse::fromWorkflow)
                        .toList()
                );
    }

    @GetMapping("/all/{id}")
    public Workflow getAllWorkflows(@PathVariable("id") UUID id) {
        return workflowRepository.findById(id).orElse(null);
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveWorkflow(@RequestBody WorkflowCreateRequest workflow) {
        if(workflow != null) {
            Workflow saved = this.workflowService.saveWorkflowFromRequest(workflow);
            return new ResponseEntity<String>(saved.getId().toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/select")
    public List<GenericOptionsResponse> select(
            @RequestParam(required = false) String matcher,
            @RequestParam(defaultValue = "10") int limit
    ) {
        var pageReq = PageRequest.of(0, Math.min(Math.max(limit, 1), 50),
                Sort.by("name").ascending());

        Page<WorkflowOptionProjection> page = (matcher == null || matcher.isBlank())
                ? workflowRepository.findAllBy(pageReq)
                : workflowRepository
                .findByNameContainingIgnoreCase(
                        matcher.trim(), pageReq);

        return page.getContent().stream()
                .map(u -> new GenericOptionsResponse(u.getId(), u.getName()))
                .toList();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWorkflow(@RequestParam UUID id) {
        if(Objects.nonNull(id)) {
           this.workflowRepository.deleteById(id);
           return ResponseEntity.ok("Deleted!" + id);
        }
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
}
