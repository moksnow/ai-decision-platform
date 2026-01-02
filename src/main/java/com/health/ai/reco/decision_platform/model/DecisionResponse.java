package com.health.ai.reco.decision_platform.model;

import com.health.ai.reco.decision_platform.model.enums.RiskLevel;
import lombok.Data;

/**
 * @author m.khandan
 * 1/2/2026
 */
@Data
public class DecisionResponse {

    /**
     * Final decision of the platform.
     */
    private boolean allowed;

    /**
     * Risk level calculated by the platform.
     */
    private RiskLevel riskLevel;

    /**
     * Human-readable explanation for auditing and debugging.
     */
    private String decisionReason;
}
