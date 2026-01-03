# AI Decision Platform - Health Case Study

[![Java](https://img.shields.io/badge/Java-21-blue)](https://www.oracle.com/java/) [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-green)](https://spring.io/projects/spring-boot) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## Overview

**AI Decision Platform - Health Case Study** is a **backend Spring Boot service** designed to demonstrate **technical
leadership in building enterprise AI systems** with a focus on **health-related scenarios**.

It provides:

- Policy-driven AI request evaluation via a **governance rule engine**
- **Risk assessment** abstraction for sensitive health data
- **Internal anonymization of health-related content before AI usage**
- **Audit logging** for all AI decision requests
- A **template architecture** for building AI systems in healthcare organizations

> This project is **not an AI model itself** — it focuses on **decision governance, compliance, anonymization, and
auditability** in a health case study context.

---

## Key Concepts (Health-Focused)

### 1. Governance before AI

AI is **never called directly**. Every request is evaluated by governance rules based on:

- Request context (e.g. health record, research, generic text)
- Request source (internal service, user, external partner)
- Internally assessed risk level

### 2. Risk Assessment

The platform derives risk **internally** (never trusted from client input), based on:

- Content analysis (keywords, patterns)
- Health and financial context

### 3. Anonymization (Internal Only)

For allowed requests:

- Sensitive health data (names, emails, IDs, etc.) is **anonymized internally**
- **Only anonymized content is sent to the AI provider**
- Raw or anonymized content is **never returned** in API responses

This aligns with healthcare compliance principles such as **data minimization** and **least privilege**.

---

## Features

- REST API endpoint for submitting AI decision requests
- Governance rules engine (XML-based)
- Risk assessment service (pluggable logic)
- **Internal anonymization layer for health data**
- Audit logging to file (no database required)
- Enum-based modeling for request context and source
- Extensible XML rules for allow/deny decisions
- Fully backend-focused (Spring Boot + Java 21)
- Health-specific case study scenarios

---

## Architecture

```
ai-decision-platform
├── src/main/java/com/health/ai/reco/decision_platform
│   ├── controller/DecisionController.java
│   ├── service/
│   │   ├── DecisionService.java
│   │   ├── GovernanceService.java
│   │   ├── RiskAssessmentService.java
│   │   ├── AnonymizationService.java
│   │   ├── AuditService.java
│   │   └── rule/GovernanceRuleEngine.java
│   ├── model/
│   │   ├── DecisionRequest.java
│   │   ├── DecisionResponse.java
│   │   ├── RequestContext.java
│   │   └── RequestSource.java
│   └── DecisionPlatformApplication.java
└── src/main/resources/
    ├── application.yml
    └── governance-rules.xml
```

---

## Governance Rules

Rules are defined in `src/main/resources/governance-rules.xml`.

Example:

```xml

<rule id="R001" context="GENERIC_TEXT" source="INTERNAL_SERVICE" maxRisk="LOW" action="ALLOW">
    <description>
        Allow internal services for low-risk, non-sensitive requests
    </description>
</rule>
```

Each rule contains:

- `id` → unique identifier
- `context` → request context (`RequestContext` enum)
- `source` → origin of request (`RequestSource` enum)
- `maxRisk` → maximum risk level allowed
- `action` → ALLOW / DENY
- `description` → human-readable explanation

---

## Example Requests

### Allowed Request

```bash
curl -X POST http://localhost:8080/api/decision   -H "Content-Type: application/json"   -d '{
        "content": "This is a safe test request",
        "context": "GENERIC_TEXT",
        "requestSource": "INTERNAL_SERVICE"
      }'
```

### Denied Request (Health Data Example)

```bash
curl -X POST http://localhost:8080/api/decision   -H "Content-Type: application/json"   -d '{
        "content": "Patient John Doe, SSN 123-45-6789",
        "context": "HEALTH_RECORD",
        "requestSource": "EXTERNAL_PARTNER"
      }'
```

---

## Getting Started

### Prerequisites

- Java 21
- Maven 3.8+
- Spring Boot 3.5.9

### Build & Run

```bash
# Clone repository
git clone https://github.com/yourusername/ai-decision-platform.git
cd ai-decision-platform

# Build project
mvn clean package

# Run locally
mvn spring-boot:run
```

Application will start on `http://localhost:8080`.

---

### Configuration

`application.yml` example:

```yaml
server:
  port: 8080

governance:
  rulesFile: classpath:governance-rules.xml
```

Notes:

- Governance rules XML path must match `governance.rulesFile`
- Audit logs are written automatically to `logs/audit.log`

---

## Testing

- Use the **cURL examples** above to test allowed/denied requests
- Add **unit tests** for `DecisionService` and `GovernanceRuleEngine` for automated validation
- Health-specific test cases demonstrate patient data governance

---

## Future Improvements

- Extend **RiskAssessmentService** with AI-based content scanning
- Support **dynamic rules reload** without restarting the service
- Integrate **external audit storage** (database or cloud)
- Extend for **multi-tenant AI governance**
- Add more **health-specific scenarios** (research, anonymized datasets, patient communication)

---

## License

MIT License © 2026