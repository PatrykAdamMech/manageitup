package com.patryk.mech.manageitup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patryk.mech.manageitup.models.common.GenericOptionsResponse;
import com.patryk.mech.manageitup.models.project.DTO.ProjectCreateFullRequest;
import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantResponse;
import com.patryk.mech.manageitup.models.project.DTO.ProjectResponse;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.DTO.ProjectCreateRequest;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.repositories.ProjectRepository;
import com.patryk.mech.manageitup.services.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/projects")
@Tag(name = "Project Management", description = "APIs for managing projects")
public class ProjectController {

    private ProjectRepository projectRepository;

    private ProjectService projectService;

    private EntityManager em;

    public ProjectController(EntityManager em, ProjectRepository projectRepository, ProjectService ps) {
        this.em = em;
        this.projectRepository = projectRepository;
        this.projectService = ps;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponse>> getAllProject() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectRepository.findAll()
                    .stream()
                    .map(ProjectResponse::fromProject)
                    .toList()
                );
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProject(@RequestBody ProjectCreateRequest request) {
        if(request != null) {
            UUID savedProject = projectRepository.save(request.asProject(em)).getId();

            return new ResponseEntity<>(savedProject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add/full")
    public ResponseEntity<String> addProjectFull(@RequestBody ProjectCreateFullRequest request) {
        if(request != null) {
            UUID savedProject = projectRepository.save(projectService.saveProjectFromRequest(request)).getId();

            return new ResponseEntity<>(savedProject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeProject(@RequestParam UUID id) {
        Project project = projectRepository.findById(id).orElse(null);
        if(project == null) ResponseEntity.status(HttpStatus.NO_CONTENT).body("Project not found!");

        project.setWorkflow(null);
        project.setOwner(null);
        project.setParticipants(null);

        projectRepository.save(project);
        projectRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
    }


}
