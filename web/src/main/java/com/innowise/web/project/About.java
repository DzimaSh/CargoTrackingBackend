package com.innowise.web.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

public class About {

    @Value("${name}")
    @JsonProperty("applicationName")
    private String name;
    @Value("${version}")
    @JsonProperty("version")
    private String version;

    public String getVersion() {
        return version;
    }
    public String getName() {
        return name;
    }
}
