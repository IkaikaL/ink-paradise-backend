package com.catsaredope.inkparadise.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/my-anime-list")
public class MyAnimeList {
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @CrossOrigin
    @GetMapping("/fetch-account-data")
    public String fetchAccountData(@RequestParam(value = "username", required = true) String username) {
        String externalApiUrl = "https://api.jikan.moe/v4/users/" + username + "/full";

        // Create HttpHeaders and set the User-Agent header
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "ink-paradise");

        // Create a RequestEntity with headers
        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

        // Make the request
        return restTemplate.exchange(requestEntity, String.class).getBody();
    }

    @CrossOrigin
    @GetMapping("/fetch-top-manga")
    public String fetchTopManga(@RequestParam(value = "limit", required = true) String limit) {
        String externalApiUrl = "https://api.jikan.moe/v4/top/manga?limit=" + limit;

        // Create HttpHeaders and set the User-Agent header
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "ink-paradise");

        // Create a RequestEntity with headers
        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(externalApiUrl));

        // Make the request
        return restTemplate.exchange(requestEntity, String.class).getBody();
    }

}