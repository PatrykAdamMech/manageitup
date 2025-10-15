package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.DTO.ProjectCreateRequest;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.repositories.ProjectRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@Tag(name = "Project Management", description = "APIs for managing projects")
public class ProjectController {

    private ProjectRepository projectRepository;

    private EntityManager em;

    public ProjectController(EntityManager em, ProjectRepository projectRepository) {
        this.em = em;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Project>> getAllProject() {
        return ResponseEntity.status(HttpStatus.OK).body(projectRepository.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProject(@RequestBody ProjectCreateRequest request) {
        if(request != null) {
            projectRepository.save(request.asProject(em));

            return new ResponseEntity<>("Saved!", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeProject(@RequestParam UUID id) {
        Project project = projectRepository.findById(id).orElse(null);
        if(project == null) ResponseEntity.status(HttpStatus.NO_CONTENT).body("Project not found!");

        for(ProjectParticipant participant : List.copyOf(project.getParticipants())) {
            participant.removeProject(id);
        }
        project.setWorkflow(null);
        project.setOwner(null);
        project.setParticipants(null);

        projectRepository.save(project);
        projectRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
    }
}
