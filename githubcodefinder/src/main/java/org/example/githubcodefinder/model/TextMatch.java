package org.example.githubcodefinder.model;
import lombok.Data;

@Data
public class TextMatch {
    private String object_url;
    private String object_type;
    private String property;
    private String fragment;
}