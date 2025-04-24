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
import org.springframework.web.util.UriComponentsBuilder;

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

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(githubApiUrl + "/search/code")
                .queryParam("q", searchQuery)
                .queryParam("per_page", 10);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<SearchResponse> response = restTemplate.exchange(
                    builder.toUriString(),
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
