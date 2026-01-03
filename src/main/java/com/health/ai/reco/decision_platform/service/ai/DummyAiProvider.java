package com.health.ai.reco.decision_platform.service.ai;

import com.health.ai.reco.decision_platform.model.DecisionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author m.khandan
 * 1/2/2026
 */

@Slf4j
@Component
public class DummyAiProvider implements AiProvider {

    @Override
    public String callAI(DecisionRequest request) {
        String anonymizedContent = request.getContent();
        if (anonymizedContent == null || anonymizedContent.isBlank()) {
            log.warn("Empty content received in DummyAiProvider");
            return "No content to analyze.";
        }

// Example: generate mock analysis based on content length
        String analysis;
        int length = anonymizedContent.length();

        if (length < 20) {
            analysis = "Content is short. Minimal information detected.";
        } else if (length < 100) {
            analysis = "Content is moderate. AI suggests careful review.";
        } else {
            analysis = "Content is extensive. AI suggests detailed analysis.";
        }

        log.info("Dummy AI processed anonymized content. Length: {}", length);
        return analysis;
    }
}
