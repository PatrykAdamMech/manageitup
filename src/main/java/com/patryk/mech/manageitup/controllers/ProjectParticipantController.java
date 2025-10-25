package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantCreateRequest;
import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantResponse;
import com.patryk.mech.manageitup.models.project.DTO.UserResponse;
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
import java.util.stream.StreamSupport;

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
    public ResponseEntity<List<ProjectParticipantResponse>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                    StreamSupport
                    .stream(ppRepository.findAll().spliterator(), false)
                    .map(ProjectParticipantResponse::fromProjectParticipant)
                    .toList()
                );
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveParticipant(@RequestBody ProjectParticipantCreateRequest participant) {
        if(participant != null) {
            ProjectParticipant proPart = participant.asProjectParticipant(this.entityManager);
            UUID project = ppRepository.save(proPart).getId();

            return new ResponseEntity<String>(project.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }
}
