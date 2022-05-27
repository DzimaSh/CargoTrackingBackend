package com.innowise.web.controller;

import com.innowise.web.dto.request.AuthRequest;
import com.innowise.web.dto.request.LogoutRequest;
import com.innowise.web.dto.request.RefreshJwtRequest;
import com.innowise.web.dto.response.JwtResponse;
import com.innowise.web.project.ProjectParams;
import com.innowise.web.security.AuthService;
import com.innowise.web.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshJwt(@RequestBody RefreshJwtRequest jwtRequest) {
        JwtResponse response = authService.refresh(jwtRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal JwtUser user,
                                       @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
