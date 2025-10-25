package com.patryk.mech.manageitup.services;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.project.DTO.ProjectCreateFullRequest;
import com.patryk.mech.manageitup.models.project.DTO.ProjectParticipantCreateRequest;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.repositories.ProjectParticipantRepository;
import com.patryk.mech.manageitup.repositories.ProjectRepository;
import com.patryk.mech.manageitup.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectParticipantRepository ppRepository;
    private WorkflowService workflowService;
    private UserRepository userRepository;
    private EntityManager em;

    public ProjectService(ProjectRepository projectRepository, EntityManager em, WorkflowService workflowService, ProjectParticipantRepository ppRepository) {
        this.projectRepository = projectRepository;
        this.em = em;
        this.workflowService = workflowService;
        this.ppRepository = ppRepository;

    }

    @Transactional
    public Project saveProjectFromRequest(ProjectCreateFullRequest request) {
        return projectRepository.save(this.requestToProject(request));
    }

    @Transactional
    private Project requestToProject(ProjectCreateFullRequest request) {

        System.out.println("REQUEST: " + request);

        Project finalProject = new Project();

        finalProject.setTitle(request.getTitle());
        finalProject.setStartDate(request.getStartDate());
        finalProject.setEndDate(request.getEndDate());

        User u = em.getReference(User.class, request.getOwnerId());
        finalProject.setOwner(u);

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

}
