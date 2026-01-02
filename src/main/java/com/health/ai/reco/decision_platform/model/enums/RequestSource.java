package com.health.ai.reco.decision_platform.model.enums;

/**
 * @author m.khandan
 * 1/2/2026
 */
public enum RequestSource {
    /**
     * Backend services inside the organization.
     */
    INTERNAL_SERVICE,

    /**
     * Human users (e.g. clinicians, analysts, operators).
     */
    INTERNAL_USER,

    /**
     * Trusted external partners.
     */
    EXTERNAL_PARTNER,

    /**
     * Automated jobs or batch processes.
     */
    SYSTEM_JOB
}
