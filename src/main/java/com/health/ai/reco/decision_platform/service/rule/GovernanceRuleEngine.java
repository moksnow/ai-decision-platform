package com.health.ai.reco.decision_platform.service.rule;

import com.health.ai.reco.decision_platform.model.DecisionRequest;
import com.health.ai.reco.decision_platform.model.GovernanceRule;
import com.health.ai.reco.decision_platform.model.enums.RiskLevel;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author m.khandan
 * 1/2/2026
 */
@Component
public class GovernanceRuleEngine {

    private final List<GovernanceRule> rules = new ArrayList<>();
    private final Resource rulesFile;

    public GovernanceRuleEngine(@Value("${governance.rules-file:}") Resource rulesFile) {
        this.rulesFile = rulesFile;
    }

    @PostConstruct
    public void loadRules() {
        try {
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(rulesFile.getInputStream());

            NodeList ruleNodes = document.getElementsByTagName("rule");

            for (int i = 0; i < ruleNodes.getLength(); i++) {
                Element element = (Element) ruleNodes.item(i);

                GovernanceRule rule = new GovernanceRule(
                        element.getAttribute("id"),
                        element.getAttribute("context"),
                        element.getAttribute("source"),
                        element.getAttribute("maxRisk"),
                        element.getAttribute("action"),
                        element.getElementsByTagName("description").item(0).getTextContent()
                );
                rules.add(rule);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Failed to load governance rules", e);
        }
    }

    /**
     * Returns TRUE if request is allowed to proceed to AI.
     */
    public boolean evaluate(DecisionRequest request, RiskLevel assessedRisk) {

        for (GovernanceRule rule : rules) {
            if (rule.matches(request, assessedRisk)) {
                return rule.isAllowed();
            }
        }

        // Default deny if no rule explicitly allows
        return false;
    }
}
