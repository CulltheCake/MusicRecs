package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {

    @Autowired
    private SpotifySearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<JsonNode> search(@RequestParam String query) {
        return ResponseEntity.ok(searchService.searchTrack(query));
    }
}
