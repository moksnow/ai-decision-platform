package com.health.ai.reco.decision_platform.service;

import com.health.ai.reco.decision_platform.model.DecisionRequest;
import com.health.ai.reco.decision_platform.model.enums.RiskLevel;
import com.health.ai.reco.decision_platform.service.rule.GovernanceRuleEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author m.khandan
 * 1/2/2026
 */

@Service
@RequiredArgsConstructor
public class GovernanceService {
    private final GovernanceRuleEngine ruleEngine;

    /**
     * Central authority for AI governance decisions.
     */
    public boolean isAllowed(DecisionRequest request, RiskLevel riskLevel) {
        return ruleEngine.evaluate(request, riskLevel);
    }
}
