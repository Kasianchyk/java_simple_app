package org.example.app.testrail.api;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.*;
import org.example.app.testrail.api.Status;
import org.example.app.testrail.api.TestRailClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class TestRailAPI {
    //private final static PropertiesUtils propertiesUtils = new PropertiesUtils("testrail.properties");
    private final static Integer SUITE_ID = 34;
    private final static Integer PROJECT_ID = 134;
    private final static String AUTOMATION_TYPE = "automation_type";

    //Logger logger = LoggerFactory.getLogger(TestRailAPI.class);

    public Status getCurrentStatus(String runId, String caseId){
        TestRail testRail = TestRailClient.getInstance();
        List<ResultField> resultFields = testRail.resultFields().list().execute();
        List<Result> statusHistory = testRail.results()
                .listForCase(Integer.parseInt(runId), Integer.parseInt(caseId), resultFields)
                .execute();
        if (statusHistory.isEmpty())
            return Status.PASSED;
        Integer currentStatusId = statusHistory
                .stream()
                .max(Comparator.comparing(Result::getCreatedOn)).get()
                .getStatusId();
        return Status.valueOf(currentStatusId);
    }

    public void updateResult(String runId, String caseTitle, Result result) {
        //logger.info("Update status for run - {} and case - {}", runId, caseTitle);
        TestRail testRail = TestRailClient.getInstance();
        List<ResultField> customResultFields = testRail.resultFields().list().execute();
        String caseId = getCaseIdsByTitle(runId, caseTitle);
        testRail.results()
                .addForCase(parseInt(runId)
                        , parseInt(caseId)
                        , result
                        , customResultFields
                ).execute();
    }

    public List<String> getCaseIds(String runId) {
        List<Test> tests = TestRailClient.getInstance()
                .tests()
                .list(parseInt(runId))
                .execute();
        return tests.stream().map(test -> String.valueOf(test.getCaseId())).collect(Collectors.toList());
    }

    public String getCaseIdsByTitle(String runId, String title) {
        List<Test> tests = TestRailClient.getInstance()
                .tests()
                .list(parseInt(runId))
                .execute();
        return tests.stream().filter(test -> test.getTitle().equals(title)).map(test -> String.valueOf(test.getCaseId())).findFirst().get();
    }

    public List<String> getCaseTitles(String runId) {
        List<Test> tests = TestRailClient.getInstance()
                .tests()
                .list(parseInt(runId))
                .execute();
        return tests.stream().map(Test::getTitle).collect(Collectors.toList());
    }

    public List<Case> getAllCaseFromProject() {
        return TestRailClient.getInstance()
                .cases().list(PROJECT_ID, SUITE_ID, getCaseFields()).execute();
    }


    public TRAutomationType getAutomationType(String runId, String caseTitle) {
        String caseId = getCaseIdsByTitle(runId, caseTitle);
        Case testCase = TestRailClient
                .getInstance()
                .cases()
                .get(parseInt(caseId), getCaseFields())
                .execute();
        return TRAutomationType.parse(parseInt(testCase.getCustomField(AUTOMATION_TYPE)));
    }

    public Integer createRun(String run_name, List<Integer> caseIds) {
        Run run = new Run()
                .setName(run_name)
                .setIncludeAll(false)
                .setSuiteId(SUITE_ID)
                .setCaseIds(caseIds);

        Run newRun = TestRailClient
                .getInstance()
                .runs()
                .add(PROJECT_ID, run)
                .execute();
        return newRun.getId();

    }

    private List<CaseField> getCaseFields() {
        return TestRailClient
                .getInstance()
                .caseFields()
                .list()
                .execute();
    }


}
