package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantCreateRequest;
import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantResponse;
import com.patryk.mech.manageitup.models.project.DTO.UserResponse;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.repositories.ProjectParticipantRepository;
import com.patryk.mech.manageitup.services.ProjectParticipantService;
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
    private ProjectParticipantService ppService;

    public ProjectParticipantController(ProjectParticipantRepository ppRepository, ProjectParticipantService ppService) {
        this.ppRepository = ppRepository;
        this.ppService = ppService;

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
            UUID proPart;
            try {
                proPart = ppService.saveProjectParticipantFromRequest(participant).getId();
            } catch (Exception e) {
                System.out.println("Error while saving the project!: " + e.getMessage());
                return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<String>(proPart.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }
}
