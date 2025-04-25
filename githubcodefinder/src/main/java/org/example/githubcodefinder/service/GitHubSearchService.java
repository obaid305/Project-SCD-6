package org.example.githubcodefinder.service;

import org.example.githubcodefinder.model.CodeItem;
import org.example.githubcodefinder.model.SearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GitHubSearchService {

    private final RestTemplate restTemplate;

    @Value("${github.api.token}")
    private String githubToken;

    @Value("${github.api.url:https://api.github.com}")
    private String githubApiUrl;

    public GitHubSearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CodeItem> searchCode(String query, String language) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.set("Accept", "application/vnd.github.v3.text-match+json");

        String searchQuery = query;
        if (language != null && !language.isEmpty()) {
            searchQuery += " language:" + language;
        }

        // Encode the full search query properly
        String encodedQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);

        // Build full URL manually
        String url = githubApiUrl + "/search/code?q=" + encodedQuery + "&per_page=10";

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<SearchResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    SearchResponse.class
            );

            if (response.getBody() != null && response.getBody().getItems() != null) {
                return Arrays.asList(response.getBody().getItems());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
