package com.innowise.web.info;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "project")
@Getter
@Setter
public class ProjectParams {

    private String applicationName;
    private String version;
}
