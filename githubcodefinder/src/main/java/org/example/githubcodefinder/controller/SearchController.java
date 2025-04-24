package org.example.githubcodefinder.controller;

import org.example.githubcodefinder.model.CodeItem;
import org.example.githubcodefinder.model.SearchRequest;
import org.example.githubcodefinder.service.GitHubSearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final GitHubSearchService gitHubSearchService;

    public SearchController(GitHubSearchService gitHubSearchService) {
        this.gitHubSearchService = gitHubSearchService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("searchRequest", new SearchRequest());
        return "index";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute SearchRequest searchRequest, Model model) {
        // Directly access the fields instead of using getters
        String query = searchRequest.query;
        String language = searchRequest.language;

        List<CodeItem> results = gitHubSearchService.searchCode(query, language);

        model.addAttribute("results", results);
        model.addAttribute("searchRequest", searchRequest);
        return "results";
    }

    @GetMapping("/api/search")
    @ResponseBody
    public List<CodeItem> apiSearch(@RequestParam String query, @RequestParam(required = false) String language) {
        // Use request parameters directly instead of SearchRequest object
        return gitHubSearchService.searchCode(query, language);
    }
}