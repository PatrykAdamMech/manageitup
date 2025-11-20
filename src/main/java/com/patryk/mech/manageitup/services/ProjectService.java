package com.patryk.mech.manageitup.services;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.project.DTO.ProjectCreateFullRequest;
import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantCreateRequest;
import com.patryk.mech.manageitup.models.project.DTO.ProjectStatusResponse;
import com.patryk.mech.manageitup.models.project.DTO.ProjectStatusUpdateRequest;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import com.patryk.mech.manageitup.repositories.ProjectParticipantRepository;
import com.patryk.mech.manageitup.repositories.ProjectRepository;
import com.patryk.mech.manageitup.repositories.ProjectStatusRepository;
import com.patryk.mech.manageitup.repositories.UserRepository;
import com.patryk.mech.manageitup.shared.NameUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectStatusRepository psRepository;
    private ProjectParticipantRepository ppRepository;
    private WorkflowService workflowService;
    private ProjectParticipantService ppService;
    private UserRepository userRepository;
    private EntityManager em;

    public ProjectService(ProjectRepository projectRepository, ProjectStatusRepository psRepository, EntityManager em, WorkflowService workflowService, ProjectParticipantRepository ppRepository, ProjectParticipantService ppService) {
        this.projectRepository = projectRepository;
        this.psRepository = psRepository;
        this.em = em;
        this.workflowService = workflowService;
        this.ppRepository = ppRepository;
        this.ppService = ppService;

    }

    @Transactional
    public Project saveProjectFromRequest(ProjectCreateFullRequest request) {
        if (request.getId() != null) {
            System.out.println("Existing project!");
            Project existing = projectRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found: " + request.getId()));

            existing.setTitle(NameUtils.capitalizeWords(request.getTitle()));
            existing.setStartDate(request.getStartDate());
            existing.setEndDate(request.getEndDate());
            existing.setOwner(em.getReference(User.class, request.getOwnerId()));
            existing.setWorkflow(workflowService.requestToWorkflow(request.getWorkflow()));

            List<ProjectParticipant> collectedParticipants;
            List<ProjectParticipant> toDelete;
            List<ProjectParticipant> toAdd;

            collectedParticipants = request.getParticipants().stream()
                    .map(p -> {
                        p.setProjectId(existing.getId());
                        ProjectParticipant pp = ppService.saveProjectParticipantFromRequest(p);
                        return pp;
                    }).toList();

            // remove deleted participants
            toDelete = existing.getParticipants().stream()
                    .filter(pp -> !collectedParticipants.contains(pp))
                    .toList();

            toDelete.forEach(existing::removeParticipant);

            // add missing participants
            toAdd = collectedParticipants.stream()
                    .filter(pp -> !existing.getParticipants().contains(pp))
                    .toList();

            toAdd.forEach(existing::addParticipant);

            if(Objects.nonNull(request.getStatusId())) {
                ProjectStatus s = psRepository.findById(request.getStatusId())
                        .orElseThrow(() -> new EntityNotFoundException("Project status not found: " + request.getStatusId()));
                existing.setStatus(s);
            }

            return projectRepository.save(existing);
        } else {
            Project p = requestToProject(request);
            return projectRepository.save(p);
        }
    }

    @Transactional
    private Project requestToProject(ProjectCreateFullRequest request) {
        Project finalProject = new Project();

        System.out.println("REQUEST: " + request);

        finalProject.setTitle(NameUtils.capitalizeWords(request.getTitle()));
        finalProject.setStartDate(request.getStartDate());
        finalProject.setEndDate(request.getEndDate());

        User u = em.getReference(User.class, request.getOwnerId());
        finalProject.setOwner(u);

        if(Objects.nonNull(request.getStatusId())) {
            ProjectStatus s = psRepository.findById(request.getStatusId())
                    .orElseThrow(() -> new EntityNotFoundException("Project status not found: " + request.getStatusId()));
            finalProject.setStatus(s);
        }

        finalProject.setWorkflow(workflowService.
                requestToWorkflow(request.getWorkflow()));

        Set<ProjectParticipant> pps = new HashSet<>();
        for(ProjectParticipantCreateRequest ppcr : request.getParticipants()) {
                ProjectParticipant pp = ppService.requestToProjectParticipant(ppcr);
            pp.setProject(finalProject);
            pps.add(pp);
        }

        finalProject.setParticipants(pps);

        return finalProject;
    }

    @Transactional
    public Project saveStatusUpdate(ProjectStatusUpdateRequest psur) {
        Project fetchedProject = projectRepository.findById(psur.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found! ID: " + psur.getProjectId()));

        ProjectStatus fetchedStatus = psRepository.findById(psur.getStatusId())
                .orElseThrow(() -> new EntityNotFoundException("Status not found! ID: " + psur.getStatusId()));
        fetchedProject.setStatus(fetchedStatus);

        return projectRepository.save(fetchedProject);
    }

}
