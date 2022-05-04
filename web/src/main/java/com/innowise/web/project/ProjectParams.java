package com.innowise.web.project;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProjectParams {

    @Value("${applicationName}")
    private String applicationName;
    @Value("${version}")
    private String version;

    public String getVersion() {
        return version;
    }
    public String getApplicationName() {
        return applicationName;
    }
}
