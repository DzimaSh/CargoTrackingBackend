package com.innowise.web.controller;

import com.innowise.web.project.ProjectParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    private final ProjectParams params;

    @Autowired
    public MainController(ProjectParams params) {
        this.params = params;
    }

    @GetMapping("/about")
    public ProjectParams getInfoAboutProject() {
        return params;
    }
}
