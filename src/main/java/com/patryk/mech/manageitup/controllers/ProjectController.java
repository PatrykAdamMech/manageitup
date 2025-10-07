package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/all")
    public Iterable<Project> getAllProject() {
        return projectRepository.findAll();
    }

    @PostMapping("/addDummy")
    public ResponseEntity<String> addDummyProject() {
        projectRepository.save(new Project());
        return new ResponseEntity<>("OK!", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        if(project != null) {
            projectRepository.save(project);
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

}
