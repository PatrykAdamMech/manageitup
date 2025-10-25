package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.common.GenericOptionsResponse;
import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantResponse;
import com.patryk.mech.manageitup.models.project.DTO.ProjectStatusOptionProjection;
import com.patryk.mech.manageitup.models.project.DTO.ProjectStatusResponse;
import com.patryk.mech.manageitup.models.project.DTO.UserOptionProjection;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import com.patryk.mech.manageitup.repositories.ProjectStatusRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/status")
@Tag(name = "Status Management", description = "APIs for managing project statuses")
public class ProjectStatusController {

    @Autowired
    private ProjectStatusRepository psRepository;

    @GetMapping("/all")
    public ResponseEntity<List<ProjectStatusResponse>> getAllStatuses() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                    StreamSupport
                    .stream(psRepository.findAll().spliterator(), false)
                    .map(ProjectStatusResponse::fromStatus)
                    .toList()
                );
    }

    @GetMapping("/all/{id}")
    public ProjectStatus getStatusById(@PathVariable UUID id) {
        return psRepository.findById(id).orElse(null);
    }

    @PostMapping("/add")
    public ResponseEntity<UUID> saveStatus(@RequestBody ProjectStatus status) {
        UUID id = psRepository.save(status).getId();
        return new ResponseEntity<UUID>(id, HttpStatus.OK);
    }

    @GetMapping("/select")
    public List<GenericOptionsResponse> select(
            @RequestParam(required = false) String matcher,
            @RequestParam(defaultValue = "10") int limit
    ) {
        var pageReq = PageRequest.of(0, Math.min(Math.max(limit, 1), 50),
                Sort.by("name").ascending());

        Page<ProjectStatusOptionProjection> page = (matcher == null || matcher.isBlank())
                ? psRepository.findAllBy(pageReq)
                : psRepository
                .findByNameContainingIgnoreCase(
                        matcher.trim(), pageReq);

        return page.getContent().stream()
                .map(u -> new GenericOptionsResponse(u.getId(), u.getName()))
                .toList();
    }
}
