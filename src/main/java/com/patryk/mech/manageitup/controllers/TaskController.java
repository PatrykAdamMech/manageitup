package com.patryk.mech.manageitup.controllers;


import com.patryk.mech.manageitup.models.common.GenericOptionsResponse;
import com.patryk.mech.manageitup.models.project.DTO.TaskCreateRequest;
import com.patryk.mech.manageitup.models.project.DTO.TaskResponse;
import com.patryk.mech.manageitup.models.project.DTO.TaskStatusOption;
import com.patryk.mech.manageitup.models.project.Task;
import com.patryk.mech.manageitup.repositories.TaskRepository;
import com.patryk.mech.manageitup.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.media.UUIDSchema;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Management", description = "APIs for managing tasks")
public class TaskController {

    private TaskRepository taskRepository;
    private TaskService taskService;

    public TaskController(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public List<TaskResponse> getAllTasks() {
        return StreamSupport
                .stream(this.taskRepository.findAll().spliterator(), false)
                .map(TaskResponse::fromTask)
                .toList();
    }

    @GetMapping("/get/{id}")
    public List<TaskResponse> getAllTasksForProject(@PathVariable UUID projectId) {
        return this.taskRepository.findByProject_Id(projectId)
                .stream()
                .map(TaskResponse::fromTask)
                .toList();
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> addTask(@RequestBody TaskCreateRequest tcr) {
        UUID createdTaskId = null;
        try {
            createdTaskId = this.taskService.saveTaskFromRequest(tcr).getId();
        } catch (Exception e) {
            System.out.println("Error while saving the task: " + e.getMessage());
        }

        return new ResponseEntity<>(createdTaskId == null ? "" : createdTaskId.toString(), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> updateTask(@RequestBody TaskCreateRequest tcr) {
        System.out.println("Update request; Body: " + tcr);
        UUID createdTaskId;
        try {
            createdTaskId = this.taskService.saveTaskFromRequest(tcr).getId();
        } catch (Exception e) {
            System.out.println("Error while saving the project!: " + e.getMessage());
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdTaskId.toString(), HttpStatus.OK);
    }

    @GetMapping("/statuses/all")
    public List<TaskStatusOption> getAllTaskStatuses() {
        return Arrays.stream(Task.TaskStatus.values())
                .map(s -> new TaskStatusOption(s.toString(), s.getLabel()))
                .toList();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable UUID id) {

        Task foundTask = taskRepository.findById(id)
                .orElse(null);
        if(foundTask == null) return new ResponseEntity<>("Task not found with ID: " + id, HttpStatus.NOT_FOUND);

        taskRepository.deleteById(foundTask.getId());
        return ResponseEntity.ok("Deleted!");
    }
}
