package com.health.ai.reco.decision_platform.service;

import com.health.ai.reco.decision_platform.model.DecisionRequest;
import com.health.ai.reco.decision_platform.model.enums.RequestContext;
import com.health.ai.reco.decision_platform.model.enums.RiskLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * @author m.khandan
 * 1/2/2026
 */

@Service
@RequiredArgsConstructor
public class RiskAssessmentService {

    // Very simple examples — intentionally deterministic
    private static final Pattern PERSONAL_DATA_PATTERN =
            Pattern.compile("\\b(name|ssn|passport|patient|dob)\\b", Pattern.CASE_INSENSITIVE);

    public RiskLevel assess(DecisionRequest request) {

        String content = request.getContent();

        // 1. Explicit personal data → CRITICAL
        if (PERSONAL_DATA_PATTERN.matcher(content).find()) {
            return RiskLevel.CRITICAL;
        }

        // 2. Health-related content → HIGH
        if (request.getContext() == RequestContext.HEALTH_RECORD) {
            return RiskLevel.HIGH;
        }

        // 3. Financial content → MEDIUM
        if (request.getContext() == RequestContext.FINANCIAL_DATA) {
            return RiskLevel.MEDIUM;
        }

        // 4. Default
        return RiskLevel.LOW;
    }
}
