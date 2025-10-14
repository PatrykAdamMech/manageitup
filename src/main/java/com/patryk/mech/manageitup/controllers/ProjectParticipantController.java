package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantCreateRequest;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.repositories.ProjectParticipantRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("participant")
@Tag(name = "Project Participants Management", description = "APIs for managing project participants")
public class ProjectParticipantController {

    private ProjectParticipantRepository ppRepository;

    private EntityManager entityManager;

    public ProjectParticipantController(ProjectParticipantRepository ppRepository, EntityManager entityManager) {
        this.ppRepository = ppRepository;
        this.entityManager = entityManager;
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<ProjectParticipant>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(ppRepository.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveParticipant(@RequestBody ProjectParticipantCreateRequest participant) {
        if(participant != null) {
            ProjectParticipant proPart = participant.asProjectParticipant(this.entityManager);
            ppRepository.save(proPart);

            List<UUID> projectList = participant.getProjectIds();

            if(projectList != null) {
                for(UUID id : participant.getProjectIds()) {
                    Project proRef = entityManager.getReference(Project.class, id);
                    if(proRef != null) {
                        proRef.addParticipant(proPart);
                    }
                }
            }

            return new ResponseEntity<String>("Saved!", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }
}
