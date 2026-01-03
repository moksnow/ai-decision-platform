package com.health.ai.reco.decision_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author m.khandan
 * 1/3/2026
 */

@Data
@AllArgsConstructor
public class AnonymizationResult {
    private String anonymizedText;
    private Map<String, List<String>> detectedEntities;
}