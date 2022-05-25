package com.innowise.web.controller;

import com.innowise.web.dto.request.AuthRequest;
import com.innowise.web.dto.response.JwtResponse;
import com.innowise.web.project.ProjectParams;
import com.innowise.web.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {

    private final AuthService authService;
    private final ProjectParams params;

    @GetMapping("/about")
    public ResponseEntity<ProjectParams> getInfoAboutProject() {
        return new ResponseEntity<>(params, HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> signIn(@RequestBody AuthRequest authRequest) {
        JwtResponse response = authService.authenticate(authRequest);
        return new ResponseEntity<>(authService.authenticate(authRequest), HttpStatus.OK);
    }
}
