package com.health.ai.reco.decision_platform.model;

import com.health.ai.reco.decision_platform.model.enums.RiskLevel;

/**
 * @author m.khandan
 * 1/2/2026
 */
public record GovernanceRule(
        String id,
        String context,
        String source,
        String maxRisk,
        String action,
        String description
) {

    public boolean matches(DecisionRequest request, RiskLevel assessedRisk) {

        if (!context.equals("ANY") && !context.equals(request.getContext().name())) {
            return false;
        }

        if (!source.equals("ANY") && !source.equals(request.getRequestSource().name())) {
            return false;
        }

        RiskLevel allowedRisk = RiskLevel.valueOf(maxRisk);
        return assessedRisk.ordinal() <= allowedRisk.ordinal();
    }

    public boolean isAllowed() {
        return "ALLOW".equalsIgnoreCase(action);
    }
}
