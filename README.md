# AI Decision Platform - Health Case Study

[![Java](https://img.shields.io/badge/Java-21-blue)](https://www.oracle.com/java/) [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-green)](https://spring.io/projects/spring-boot) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## Overview

**AI Decision Platform - Health Case Study** is a **backend Spring Boot service** designed to demonstrate **technical leadership in building enterprise AI systems** with a focus on **health-related scenarios**.

It provides:

- Policy-driven AI request evaluation via a **governance rule engine**  
- **Risk assessment** abstraction for sensitive health data  
- **Audit logging** for all AI decision requests  
- A **template architecture** for building AI systems in healthcare organizations  

> This project is **not an AI model itself** — it focuses on **decision governance, compliance, and auditability** in a health case study context.

---

## Features

- REST API endpoint for submitting AI requests  
- Governance rules engine that evaluates requests based on XML-defined rules  
- Risk assessment service (pluggable, can be customized)  
- Audit logging to file (no database required)  
- Enum-based modeling for **request source** and **request context**  
- Extensible XML rules for **allow/deny decisions**  
- Fully backend-focused (Spring Boot + Java 21)
- Health-specific case study examples for **patient records and medical research**  

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
│   │   ├── AuditService.java
│   │   └── rule/GovernanceRuleEngine.java
│   ├── model/
│   │   ├── DecisionRequest.java
│   │   ├── DecisionResponse.java
│   │   ├── RequestContext.java
│   │   ├── RequestSource.java
│   │   └── GovernanceRule.java
│   └── AiDecisionPlatformApplication.java
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
        Allow internal services for general-purpose requests with low sensitivity
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

**Response:**

```json
{
  "allowed": true,
  "message": "Request allowed by governance rules",
  "assessedRisk": "LOW"
}
```

### Denied Request (Health Data Example)

```bash
curl -X POST http://localhost:8080/api/decision   -H "Content-Type: application/json"   -d '{
        "content": "This contains sensitive patient data",
        "context": "HEALTH_RECORD",
        "requestSource": "EXTERNAL_PARTNER"
      }'
```

**Response:**

```json
{
  "allowed": false,
  "message": "Request denied by governance rules",
  "assessedRisk": "HIGH"
}
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

MIT License © 2026 Moh Khandan
