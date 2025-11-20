package com.patryk.mech.manageitup.services;

import com.patryk.mech.manageitup.models.project.DTO.TaskCreateRequest;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.models.project.Task;
import com.patryk.mech.manageitup.repositories.ProjectParticipantRepository;
import com.patryk.mech.manageitup.repositories.ProjectRepository;
import com.patryk.mech.manageitup.repositories.TaskRepository;
import com.patryk.mech.manageitup.shared.NameUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private ProjectParticipantRepository ppRepository;
    private EntityManager em;

    public TaskService(TaskRepository taskRepository, EntityManager em, ProjectParticipantRepository ppRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.em = em;
        this.ppRepository = ppRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Task     saveTaskFromRequest(TaskCreateRequest tcr) {
        return taskRepository.save(requestToTask(tcr));
    }

    @Transactional
    public Task requestToTask(TaskCreateRequest tcr) {
        Task finalTask = new Task();

        if(tcr.getId() != null) {
            finalTask = taskRepository.findById(tcr.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + tcr.getId()));
        }

        finalTask.setDescription(NameUtils.capitalize(tcr.getDescription()));
        finalTask.setName(NameUtils.capitalizeWords(tcr.getName()));
        finalTask.setStatus(tcr.getStatus());

        if(Objects.nonNull(tcr.getAssigneeId())) {
            ProjectParticipant pp = ppRepository.findById(tcr.getAssigneeId())
                    .orElseThrow(() -> new EntityNotFoundException("Participant not found with ID: " + tcr.getAssigneeId()));
            finalTask.setAssignee(pp);
        }

        if(Objects.nonNull(tcr.getProjectId())) {
            Project project = projectRepository.findById(tcr.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + tcr.getProjectId()));
            finalTask.setProject(project);

        }
        return finalTask;
    }
}
