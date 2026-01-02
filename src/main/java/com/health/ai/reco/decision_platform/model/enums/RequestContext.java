package com.health.ai.reco.decision_platform.model.enums;

/**
 * @author m.khandan
 * 1/2/2026
 */
public enum RequestContext {
    /**
     * Patient-related clinical information.
     */
    HEALTH_RECORD,

    /**
     * Medical research or anonymized datasets.
     */
    MEDICAL_RESEARCH,

    /**
     * Customer communication (emails, chats, tickets).
     */
    CUSTOMER_SUPPORT,

    /**
     * Financial or billing-related data.
     */
    FINANCIAL_DATA,

    /**
     * Internal notes or operational documents.
     */
    INTERNAL_NOTE,

    /**
     * Non-sensitive, general-purpose text.
     */
    GENERIC_TEXT
}
