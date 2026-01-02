package com.health.ai.reco.decision_platform.service.ai;

import com.health.ai.reco.decision_platform.model.DecisionRequest;

/**
 * @author m.khandan
 * 1/2/2026
 */
public interface AiProvider {

    String callAI(DecisionRequest request);

}
