package com.visualisingbpe.bpebackend.controller;

import com.visualisingbpe.bpebackend.model.bperesponse;
import com.visualisingbpe.bpebackend.service.bpeservice;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bpe")
@CrossOrigin(origins = "http://localhost:3000")
public class bpecontroller {

    private final bpeservice service;

    public bpecontroller(bpeservice service) {
        this.service = service;
    }

    @PostMapping
    public bperesponse runBpe(@RequestBody Map<String, Object> payload) {
        String sentence = ((String) payload.get("sentence")).toLowerCase();
        Object mergesObj = payload.getOrDefault("merges", 6);
        int merges = (mergesObj instanceof Number) ? ((Number) mergesObj).intValue() : 6;
        return service.applyBpe(sentence, merges);
    }

}
