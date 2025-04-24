// GitHubCodeFinderApplication.java
package org.example.githubcodefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GitHubCodeFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitHubCodeFinderApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}