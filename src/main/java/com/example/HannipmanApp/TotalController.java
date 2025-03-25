package com.example.HannipmanApp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/total")
@RequiredArgsConstructor
public class TotalController {
    private final TotalDiaryService totalDiaryService;

    @GetMapping
    public ResponseEntity<Map<String, Long>> getTotalSummary() {
        return ResponseEntity.ok(totalDiaryService.getSummary());
    }
}

