package com.health.ai.reco.decision_platform.service;

import com.health.ai.reco.decision_platform.model.DecisionRequest;
import com.health.ai.reco.decision_platform.model.DecisionResponse;
import com.health.ai.reco.decision_platform.model.enums.RiskLevel;
import com.health.ai.reco.decision_platform.service.ai.AiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author m.khandan
 * 1/2/2026
 */

@Service
@RequiredArgsConstructor
public class DecisionService {

    private final GovernanceService governanceService;
    private final RiskAssessmentService riskAssessmentService;
    private final AnonymizationService anonymizationService;
    private final AuditService auditService;
    private final AiProvider aiProvider;

    public DecisionResponse decide(DecisionRequest request) {

        // 1. Derive risk level internally
        RiskLevel riskLevel = riskAssessmentService.assess(request);

        // 2. Governance evaluation
        boolean allowed = governanceService.isAllowed(request, riskLevel);

        DecisionResponse response = new DecisionResponse();
        response.setAllowed(allowed);
        response.setRiskLevel(riskLevel);

        if (allowed) {
            // 3. Anonymize before sending to AI
            DecisionRequest anonymizedRequest = anonymizationService.anonymize(request);

            // 4. Call AI provider (dummy or real)
            String aiResult = aiProvider.callAI(anonymizedRequest);

            response.setAiOutput(aiResult);
            response.setDecisionReason("Approved by governance. AI processed anonymized content.");
        } else {
            response.setDecisionReason("Blocked by governance rules");
        }

        // 5. Always audit (even blocked decisions)
        auditService.logDecision(request, response);

        return response;
    }
}
