package com.innowise.web.controller;

import com.innowise.web.project.About;
import com.innowise.web.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    @GetMapping("/about")
    public About getInfoAboutProject() {
        return (About)context.getBean("about");
    }
}
