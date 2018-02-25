package testcheck.library;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestStatistics
{
    //<editor-fold desc="variables">

    private List<TestResult> testResults;
    private Test test;


    //</editor-fold>

    //<editor-fold desc="constructor">

    public TestStatistics(Test test)
    {
        this.test = test;
    }

    //</editor-fold>

    //<editor-fold desc="get/set">


    public Test getTest()
    {
        return test;
    }

    public void setTest(Test test)
    {
        this.test = test;
    }

    public List<TestResult> getTestResults()
    {
        return testResults;
    }

    public void setTestResults(List<TestResult> testResults)
    {
        this.testResults = testResults;
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    public void loadTestResults(File[] files)
    {
        testResults = new ArrayList<>();
        for(File file : files)
        {
            testResults.add(new TestResult(file.getPath()));
        }
    }

    public void checkTests()
    {
        if(testResults.size() > 0 && test != null)
        {
            for(TestResult testResult : testResults)
            {
                testResult.checkAnswers(test);
            }
        }

    }

    //</editor-fold>
}
