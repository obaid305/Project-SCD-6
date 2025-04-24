package org.example.githubcodefinder.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Repository {
    private long id;
    private String name;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("html_url")
    private String htmlUrl;

    private String description;
}
