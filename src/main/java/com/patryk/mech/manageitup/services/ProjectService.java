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
    private UserRepository userRepository;
    private EntityManager em;

    public ProjectService(ProjectRepository projectRepository, ProjectStatusRepository psRepository, EntityManager em, WorkflowService workflowService, ProjectParticipantRepository ppRepository) {
        this.projectRepository = projectRepository;
        this.psRepository = psRepository;
        this.em = em;
        this.workflowService = workflowService;
        this.ppRepository = ppRepository;

    }

    @Transactional
    public Project saveProjectFromRequest(ProjectCreateFullRequest request) {
        if (request.getId() != null) {
            Project existing = projectRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found: " + request.getId()));

            existing.setTitle(request.getTitle());
            existing.setStartDate(request.getStartDate());
            existing.setEndDate(request.getEndDate());
            existing.setOwner(em.getReference(User.class, request.getOwnerId()));
            existing.setWorkflow(workflowService.requestToWorkflow(request.getWorkflow()));

            // Build map of requested participants by userId
            Map<UUID, ProjectParticipantCreateRequest> requestedByUser = request.getParticipants()
                    .stream()
                    .collect(Collectors.toMap(ProjectParticipantCreateRequest::getUserId, Function.identity()));

            // Remove participants that are not in the request (use iterator to remove safely)
            Iterator<ProjectParticipant> it = existing.getParticipants().iterator();
            while (it.hasNext()) {
                ProjectParticipant pp = it.next();
                UUID userId = pp.getUser().getId();
                if (!requestedByUser.containsKey(userId)) {
                    it.remove(); // with orphanRemoval=true this will delete the row at flush
                } else {
                    // update existing participant's fields from request
                    ProjectParticipantCreateRequest reqPP = requestedByUser.remove(userId);
                    pp.setRole(reqPP.getRole());
                    // update other fields...
                }
            }

            // requestedByUser now contains only truly new participants — add them to the SAME collection
            for (ProjectParticipantCreateRequest newReq : requestedByUser.values()) {
                ProjectParticipant newPP = newReq.asProjectParticipant(em); // user reference, role etc.
                newPP.setProject(existing);
                existing.getParticipants().add(newPP); // IMPORTANT — mutate existing collection
            }

            if(Objects.nonNull(request.getStatusId())) {
                ProjectStatus s = em.getReference(ProjectStatus.class, request.getStatusId());
                existing.setStatus(s);
            }

            // Save managed entity
            return projectRepository.save(existing);
        } else {
            // create flow — new Project, setting a brand new participants list is OK here
            Project p = requestToProject(request);
            return projectRepository.save(p);
        }
    }

    @Transactional
    private Project requestToProject(ProjectCreateFullRequest request) {
        Project finalProject = new Project();

        System.out.println("REQUEST: " + request);

        finalProject.setTitle(request.getTitle());
        finalProject.setStartDate(request.getStartDate());
        finalProject.setEndDate(request.getEndDate());

        User u = em.getReference(User.class, request.getOwnerId());
        finalProject.setOwner(u);

        if(Objects.nonNull(request.getStatusId())) {
            ProjectStatus s = em.getReference(ProjectStatus.class, request.getStatusId());
            finalProject.setStatus(s);
        }

        finalProject.setWorkflow(workflowService.
                requestToWorkflow(request.getWorkflow()));

        List<ProjectParticipant> pps = new ArrayList<>();
        for(ProjectParticipantCreateRequest ppcr : request.getParticipants()) {
            ProjectParticipant pp = ppcr.asProjectParticipant(this.em);
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
