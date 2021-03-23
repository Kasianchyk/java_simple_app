package org.example.extension;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Result;
import org.example.app.testrail.api.Status;
import org.example.app.testrail.api.TestRailAPI;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.opentest4j.TestSkippedException;

public class RootExtension implements AfterTestExecutionCallback {

    private String runId = System.getProperty("runId");
    //private String runId = "1";
    private TestRailAPI testRailAPI = new TestRailAPI();

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (runId != null && !runId.equals("")) {
            String title = context.getDisplayName();
            if (testRailAPI.getCurrentStatus(runId, testRailAPI.getCaseIdsByTitle(runId, title)) != Status.FAILED ||
                    context.getExecutionException().isPresent()) {
                Result result = new Result();
                if (context.getExecutionException().isPresent() && context.getExecutionException().get() instanceof TestSkippedException) {
                    result.setComment("Automation test skipped")
                            .setStatusId(Status.SKIP.getStatus());
                } else if (!context.getExecutionException().isPresent()) {
                    result.setComment("Automation test passed successfully")
                            .setStatusId(Status.PASSED.getStatus());
                } else if (context.getExecutionException().isPresent()) {
                    String message = context.getExecutionException().get().getMessage();
                    result.setComment("Automation test failed. " + message)
                            .setStatusId(Status.FAILED.getStatus());
                }
                testRailAPI.updateResult(runId, title, result);
            }
        }
    }
}
