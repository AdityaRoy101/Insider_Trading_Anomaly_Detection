package com.md.marketdataingestionservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingestion")
public class IngestionController {

    @GetMapping("/health")
    public ResponseEntity health() {

        return ResponseEntity.ok().body("Ingestion Service is accepting requests, service up and running.....");
    }
}
