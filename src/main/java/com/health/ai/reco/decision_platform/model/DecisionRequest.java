package com.health.ai.reco.decision_platform.model;

import com.health.ai.reco.decision_platform.model.enums.RequestContext;
import com.health.ai.reco.decision_platform.model.enums.RequestSource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author m.khandan
 * 1/2/2026
 */

@Data
@Builder
public class DecisionRequest {

    @NotBlank
    private String content;

    @NotNull
    private RequestContext context;

    @NotNull
    private RequestSource requestSource;
}

