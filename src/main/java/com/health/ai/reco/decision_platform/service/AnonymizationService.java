package com.health.ai.reco.decision_platform.service;

import com.health.ai.reco.decision_platform.model.DecisionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author m.khandan
 * 1/3/2026
 */

@Slf4j
@Service
public class AnonymizationService {

    private static final Map<String, Pattern> ENTITY_PATTERNS = new LinkedHashMap<>();

    static {
        ENTITY_PATTERNS.put("PERSON", Pattern.compile("\\b[A-Z][a-z]+\\s[A-Z][a-z]+\\b"));

        ENTITY_PATTERNS.put("DATE", Pattern.compile("\\b\\d{2}/\\d{2}/\\d{4}\\b"));

        ENTITY_PATTERNS.put("SSN", Pattern.compile("\\b\\d{3}-\\d{2}-\\d{4}\\b"));

        ENTITY_PATTERNS.put("EMAIL", Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b"));

        ENTITY_PATTERNS.put("PHONE", Pattern.compile("\\b\\+?\\d{10,15}\\b"));

        ENTITY_PATTERNS.put("MEDICAL_ID", Pattern.compile("\\b(MRN|Medical\\sID)[:\\s]*\\d+\\b", Pattern.CASE_INSENSITIVE));
    }

    /**
     * Anonymizes request content and returns a NEW DecisionRequest.
     * Original request is never mutated.
     */
    public DecisionRequest anonymize(DecisionRequest originalRequest) {

        if (originalRequest.getContent() == null || originalRequest.getContent().isBlank()) {
            return originalRequest;
        }

        String anonymizedText = originalRequest.getContent();

        for (Map.Entry<String, Pattern> entry : ENTITY_PATTERNS.entrySet()) {
            String entityType = entry.getKey();
            Pattern pattern = entry.getValue();

            Matcher matcher = pattern.matcher(anonymizedText);

            if (matcher.find()) {
                anonymizedText = matcher.replaceAll("[" + entityType + "]");
                log.debug("Anonymized entity type: {}", entityType);
            }
        }

        log.info("Anonymization completed before AI call");

        return DecisionRequest.builder()
                .content(anonymizedText)
                .context(originalRequest.getContext())
                .requestSource(originalRequest.getRequestSource())
                .build();
    }
}
