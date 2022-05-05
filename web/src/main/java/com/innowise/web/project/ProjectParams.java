package com.innowise.web.project;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "project")
@Getter
public class ProjectParams {

    private String applicationName;
    private String version;
}
