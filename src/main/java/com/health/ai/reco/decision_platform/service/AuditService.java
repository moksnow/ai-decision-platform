package com.health.ai.reco.decision_platform.service;

import com.health.ai.reco.decision_platform.model.DecisionRequest;
import com.health.ai.reco.decision_platform.model.DecisionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author m.khandan
 * 1/2/2026
 */

@Service
public class AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    /**
     * Records every decision for compliance and investigations.
     */
    public void logDecision(DecisionRequest request, DecisionResponse response) {

        log.info(
                "AI_DECISION | source={} | context={} | allowed={} | risk={} | reason={}",
                request.getRequestSource(),
                request.getContext(),
                response.isAllowed(),
                response.getRiskLevel(),
                response.getDecisionReason()
        );
    }
}