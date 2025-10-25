package com.patryk.mech.manageitup.services;

import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.project.DTO.WorkflowCreateRequest;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import com.patryk.mech.manageitup.repositories.ProjectStatusRepository;
import com.patryk.mech.manageitup.repositories.WorkflowRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WorkflowService {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private ProjectStatusRepository statusRepository;

    @Autowired
    private EntityManager em;

    public WorkflowService() {
    }

    @Transactional
    public Workflow saveWorkflowFromRequest(WorkflowCreateRequest wcr) {
        return workflowRepository.save(requestToWorkflow(wcr));
    }

    @Transactional
    public Workflow requestToWorkflow(WorkflowCreateRequest wcr) {

        if (Objects.nonNull(wcr.getId())) {
            return workflowRepository.findById(wcr.getId()).orElseThrow(() -> new EntityNotFoundException("Workflow not found: " + wcr.getId()));
        }

        Workflow w = new Workflow();
        w.setName(wcr.getName());

        w = workflowRepository.save(w);

        for(ProjectStatus ps : wcr.getStatuses()) {
            ProjectStatus finalStatus;

            if(Objects.nonNull(ps.getId())) {
                finalStatus = statusRepository.findById(ps.getId()).orElseThrow(() -> new EntityNotFoundException("Status not found: " + ps.getId()));
            } else {
                finalStatus = new ProjectStatus();
                finalStatus.setName(ps.getName());
                finalStatus.setPriority(ps.getPriority());
            }
            finalStatus.setWorkflow(w);

            w.addStatus(finalStatus);

        }
        System.out.println("Workflow: " + w);
        return w;
    }


}
