package org.example.app.testrail.api;

import com.codepine.api.testrail.TestRail;

import java.util.Properties;

public class TestRailClient {

    private final static String URL = "http://127.0.0.1:3001/";
    private final static String USERNAME = "yuri.kas87@gmail.com";
    private final static String PASSWORD = "qwerty";
    //private final static String APPLICATIONNAME = getProperties("testrail.applicationName");
    private static Properties properties = null;

    static TestRail testRail;

    public static TestRail getInstance() {
        if (testRail == null) {
            return TestRail
                    .builder(URL
                            , USERNAME
                            , PASSWORD)
                    .applicationName("Test app")
                    .build();
        } else {
            return testRail;
        }
    }
}
