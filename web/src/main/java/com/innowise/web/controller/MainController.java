package com.innowise.web.controller;

import com.innowise.web.project.ProjectParams;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {

    private final ProjectParams params;

    @GetMapping("/about")
    public ProjectParams getInfoAboutProject() {
        return params;
    }
}
