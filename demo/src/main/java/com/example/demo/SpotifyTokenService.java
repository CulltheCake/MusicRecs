package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SpotifyTokenService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private String token;

    public String getToken() {
        if (token == null) {
            token = fetchToken();
        }
        return token;
    }

    private String fetchToken() {
        String credentials = clientId + ":" + clientSecret;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        WebClient client = WebClient.create("https://accounts.spotify.com");
        return client.post()
                .uri("/api/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encoded)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.get("access_token").asText())
                .block();
    }
}
