package com.health.ai.reco.decision_platform.service.ai;

import com.health.ai.reco.decision_platform.model.DecisionRequest;
import org.springframework.stereotype.Component;

/**
 * @author m.khandan
 * 1/2/2026
 */

@Component
public class DummyAiProvider implements AiProvider {

    @Override
    public String callAI(DecisionRequest request) {
        // Simulate different responses based on content
        if (request.getContent().toLowerCase().contains("patient")) {
            return "Simulated AI: Sensitive health content detected.";
        }

        if (request.getContent().toLowerCase().contains("financial")) {
            return "Simulated AI: Financial data analysis placeholder.";
        }

        return "Simulated AI: Generic response for testing purposes.";
    }

}
