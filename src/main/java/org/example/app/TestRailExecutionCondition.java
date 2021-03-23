package org.example.app;


import org.example.app.testrail.api.TestRailAPI;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

public class TestRailExecutionCondition implements ExecutionCondition {

    //private String runId = System.getProperty("runId");
    private String runId = "1";
    private TestRailAPI testRailAPI = new TestRailAPI();

    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        if (runId != null && !runId.equals("")) {
        String caseTitle = context.getDisplayName();
        List<String> caseTitles = testRailAPI.getCaseTitles(runId);
        if (caseTitles.contains(caseTitle))
            return ConditionEvaluationResult.enabled("TestRail run");
        else
            return ConditionEvaluationResult.disabled("Test case is missing on TestRail run.");

        }
        return ConditionEvaluationResult.enabled("No condition run");
    }
}
