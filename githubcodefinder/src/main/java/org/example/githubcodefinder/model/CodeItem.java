package org.example.githubcodefinder.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CodeItem {
    private String name;
    private String path;
    private String sha;
    private String url;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("git_url")
    private String gitUrl;

    @JsonProperty("download_url")
    private String downloadUrl;

    private Repository repository;

    private String score;

    @JsonProperty("text_matches")
    private TextMatch[] textMatches;

    public String getCodeSnippet() {
        if (textMatches != null && textMatches.length > 0) {
            return textMatches[0].getFragment();
        }
        return "No preview available";
    }
}
