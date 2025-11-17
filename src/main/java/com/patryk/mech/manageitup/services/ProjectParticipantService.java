package com.patryk.mech.manageitup.services;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantCreateRequest;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.repositories.ProjectParticipantRepository;
import com.patryk.mech.manageitup.repositories.ProjectRepository;
import com.patryk.mech.manageitup.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ProjectParticipantService {
    private ProjectParticipantRepository ppRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    public ProjectParticipantService(ProjectParticipantRepository ppRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.ppRepository = ppRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public ProjectParticipant saveProjectParticipantFromRequest(ProjectParticipantCreateRequest ppcr) {
        return ppRepository.save(requestToProjectParticipant(ppcr));
    }

    @Transactional
    public ProjectParticipant requestToProjectParticipant(ProjectParticipantCreateRequest ppcr) {
        ProjectParticipant pp = new ProjectParticipant();

        if(Objects.nonNull(ppcr.getId())) {
            pp = ppRepository.findById(ppcr.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Project participant not found with ID: " + ppcr.getId()));
        }

        pp.setRole(ppcr.getRole());

        User user = userRepository.findById(ppcr.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User status not found: " + ppcr.getUserId()));
        pp.setUser(user);

        if(Objects.nonNull(ppcr.getProjectId())) {
            Project project = projectRepository.findById(ppcr.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project status not found: " + ppcr.getProjectId()));

            pp.setProject(project);
        }

        return pp;
    }
}
