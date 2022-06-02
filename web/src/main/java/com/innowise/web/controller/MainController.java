package com.innowise.web.controller;

import com.innowise.web.info.ProjectParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final ProjectParams params;

    @GetMapping("/api/about")
    public ResponseEntity<ProjectParams> getProjectInfo() {
        return new ResponseEntity<>(params, HttpStatus.OK);
    }

}
