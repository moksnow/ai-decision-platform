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
    private final AuditService auditService;
    private final AiProvider aiProvider;

    public DecisionResponse decide(DecisionRequest request) {

        // 1. Platform derives risk (never trust client estimation)
        RiskLevel riskLevel = riskAssessmentService.assess(request);

        // 2. Governance rules decide if AI usage is allowed
        boolean allowed = governanceService.isAllowed(request, riskLevel);

        DecisionResponse response = new DecisionResponse();
        response.setAllowed(allowed);
        response.setRiskLevel(riskLevel);

        if (allowed) {
            // Only call AI if governance allows
            String aiResult = aiProvider.callAI(request);
            response.setDecisionReason("Approved. AI output: " + aiResult);
        } else {
            response.setDecisionReason("Blocked by governance rules");
        }

        auditService.logDecision(request, response);

        return response;
    }
}
