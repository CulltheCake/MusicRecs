package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class SpotifySearchService {

    @Autowired
    private SpotifyTokenService tokenService;

    public JsonNode searchTrack(String query) {
        String token = tokenService.getToken();

        WebClient client = WebClient.create("https://api.spotify.com");

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search")
                        .queryParam("q", query)
                        .queryParam("type", "track")
                        .queryParam("limit", "5")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
