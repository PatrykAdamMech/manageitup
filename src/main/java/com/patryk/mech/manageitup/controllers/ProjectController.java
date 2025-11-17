package com.patryk.mech.manageitup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patryk.mech.manageitup.models.common.GenericOptionsResponse;
import com.patryk.mech.manageitup.models.project.DTO.*;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import com.patryk.mech.manageitup.repositories.ProjectRepository;
import com.patryk.mech.manageitup.services.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
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
    public ResponseEntity<Set<ProjectResponse>> getAllProject() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectRepository.findAll()
                    .stream()
                    .map(ProjectResponse::fromProject)
                    .collect(Collectors.toSet())
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable UUID id) {
        Project foundProject = this.projectRepository.findById(id).orElse(null);
        if(foundProject != null) {
            return new ResponseEntity<>(ProjectResponse.fromProject(foundProject), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/get/user/owner/{id}")
    public ResponseEntity<Set<ProjectResponse>> getProjectByUserId(@PathVariable UUID id) {
        Set<ProjectResponse> responses = projectRepository.findDistinctByOwner_Id(id)
                .stream().map(ProjectResponse::new)
                .collect(Collectors.toSet());

        if(!responses.isEmpty()) {
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/get/user/participant/{id}")
    public ResponseEntity<Set<ProjectResponse>> getProjectByParticipantId(@PathVariable UUID id) {
        Set<ProjectResponse> responses = projectRepository.findDistinctByParticipants_User_Id(id)
                .stream().map(ProjectResponse::new)
                .collect(Collectors.toSet());

        if(!responses.isEmpty()) {
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProject(@RequestBody ProjectCreateRequest request) {
        if(request != null) {
            UUID savedProject;
            try {
                savedProject = projectRepository.save(request.asProject(em)).getId();
            } catch (Exception e) {
                System.out.println("Error while saving the project!: " + e.getMessage());
                return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(savedProject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add/full")
    public ResponseEntity<String> addProjectFull(@RequestBody ProjectCreateFullRequest request) {
        if(request != null) {
            UUID savedProject;
            try {
                savedProject = projectRepository.save(projectService.saveProjectFromRequest(request)).getId();
            } catch (Exception e) {
                System.out.println("Error while saving the project!: " + e.getMessage());
                return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(savedProject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/full")
    public ResponseEntity<String> updateProjectFull(@RequestBody ProjectCreateFullRequest request) {
        System.out.println("Received update request: " + request);
        if(request != null) {
            UUID savedProject;
            try {
                savedProject = projectRepository.save(projectService.saveProjectFromRequest(request)).getId();
            } catch (Exception e) {
                System.out.println("Error while saving the project!: " + e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(savedProject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/status")
    public ResponseEntity<String> updateProjectStatus(@RequestBody ProjectStatusUpdateRequest request) {
        if(request != null) {
            UUID savedProject;
            try {
                savedProject  = projectService.saveStatusUpdate(request).getId();
            } catch (Exception e) {
                System.out.println("Error while saving the project!: " + e.getMessage());
                return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(savedProject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeProject(@PathVariable UUID id) {

        Project project = projectRepository.findById(id)
                .orElse(null);
        if(project == null) ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found!");

        projectRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
    }


}
