package com.health.ai.reco.decision_platform.controller;

import com.health.ai.reco.decision_platform.model.DecisionRequest;
import com.health.ai.reco.decision_platform.model.DecisionResponse;
import com.health.ai.reco.decision_platform.service.DecisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author m.khandan
 * 1/2/2026
 */
@RestController
@RequestMapping("/api/decision")
@RequiredArgsConstructor
public class DecisionController {

    private final DecisionService decisionService;

    @PostMapping
    public ResponseEntity<DecisionResponse> decide(@Valid @RequestBody DecisionRequest request) {
        DecisionResponse response = decisionService.decide(request);
        return ResponseEntity.ok(response);
    }
}
