package org.example.githubcodefinder.model;

import lombok.Data;

@Data
public class SearchRequest {
    // Make fields public for direct access
    public String query;
    public String language;

    // Add explicit getters and setters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}