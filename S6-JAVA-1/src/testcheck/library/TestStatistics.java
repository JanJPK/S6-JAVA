package testcheck.library;

import javafx.scene.chart.XYChart;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The type TestStatistics contains various data used in graphs.
 * It also connects Test with TestResults and calls interaction between them.
 */
public class TestStatistics
{
    //<editor-fold desc="variables">

    private List<TestResult> testResults;
    private Test test;
    private XYChart.Series dataGradesHistogram;
    private XYChart.Series dataPointsHistogram;
    private float dataAveragePoints;
    private float dataMedianPoints;
    private String dataMedianGrade;

    //</editor-fold>

    //<editor-fold desc="constructor">

    /**
     * Instantiates a new Test statistics.
     *
     * @param test the test
     */
    public TestStatistics(Test test)
    {
        this.test = test;
    }

    /**
     * Instantiates a new Test statistics.
     */
    public TestStatistics()
    {

    }

    //</editor-fold>

    //<editor-fold desc="get/set">

    /**
     * Gets test.
     *
     * @return the test
     */
    public Test getTest()
    {
        return test;
    }

    /**
     * Sets test.
     *
     * @param test the test
     */
    public void setTest(Test test)
    {
        this.test = test;
    }

    /**
     * Gets test results.
     *
     * @return the test results
     */
    public List<TestResult> getTestResults()
    {
        return testResults;
    }

    /**
     * Sets test results.
     *
     * @param testResults the test results
     */
    public void setTestResults(List<TestResult> testResults)
    {
        this.testResults = testResults;
    }

    /**
     * Gets a single test result.
     *
     * @param i the index
     * @return the test result
     */
    public TestResult getTestResult(int i)
    {
        return testResults.get(i);
    }

    /**
     * Gets median grade.
     *
     * @return the median grade
     */
    public String getDataMedianGrade()
    {
        return dataMedianGrade;
    }

    /**
     * Sets median grade.
     *
     * @param dataMedianGrade the median grade
     */
    public void setDataMedianGrade(String dataMedianGrade)
    {
        this.dataMedianGrade = dataMedianGrade;
    }

    /**
     * Gets median points.
     *
     * @return the median points
     */
    public float getDataMedianPoints()
    {
        return dataMedianPoints;
    }

    /**
     * Sets median points.
     *
     * @param dataMedianPoints the median points
     */
    public void setDataMedianPoints(float dataMedianPoints)
    {
        this.dataMedianPoints = dataMedianPoints;
    }

    /**
     * Gets average points.
     *
     * @return the average points
     */
    public float getDataAveragePoints()
    {
        return dataAveragePoints;
    }

    /**
     * Sets average points.
     *
     * @param dataAveragePoints the average points
     */
    public void setDataAveragePoints(float dataAveragePoints)
    {
        this.dataAveragePoints = dataAveragePoints;
    }

    /**
     * Gets points histogram.
     *
     * @return the points histogram
     */
    public XYChart.Series getDataPointsHistogram()
    {
        return dataPointsHistogram;
    }

    /**
     * Sets points histogram.
     *
     * @param dataPointsHistogram the points histogram
     */
    public void setDataPointsHistogram(XYChart.Series dataPointsHistogram)
    {
        this.dataPointsHistogram = dataPointsHistogram;
    }

    /**
     * Gets grades histogram.
     *
     * @return the grades histogram
     */
    public XYChart.Series getDataGradesHistogram()
    {
        return dataGradesHistogram;
    }

    /**
     * Sets data grades histogram.
     *
     * @param dataGradesHistogram the data grades histogram
     */
    public void setDataGradesHistogram(XYChart.Series dataGradesHistogram)
    {
        this.dataGradesHistogram = dataGradesHistogram;
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    /**
     * Load test results.
     *
     * @param files the files containing paths
     */
    public void loadTestResults(List<File> files)
    {
        testResults = new ArrayList<>();
        for (File file : files)
        {
            testResults.add(new TestResult(file));
        }
    }

    /**
     * Grade test results.
     */
    public void gradeTestResults()
    {
        if (testResults.size() > 0 && test != null)
        {
            for (TestResult testResult : testResults)
            {
                testResult.grade(test);
            }
        }

    }

    /**
     * Calculate statistics data.
     */
    public void calculateStatisticsData()
    {
        calculateGradesHistogram();
        calculatePointsHistogram();
        calculateAveragesAndMedians();
    }

    //</editor-fold>

    //<editor-fold desc="data-methods">

    /**
     * Calculates grade histogram data.
     */
    private void calculateGradesHistogram()
    {
        dataGradesHistogram = new XYChart.Series<String, Integer>();
        List<String> grades = Arrays.asList(test.getGrades());
        grades = new ArrayList<>(grades);
        grades.add(0, "2.0");
        int[] gradeCount = new int[grades.size()];

        for (TestResult testResult : testResults)
        {
            gradeCount[grades.indexOf(testResult.getGrade())]++;
        }

        for (int i = 0; i < grades.size(); i++)
        {
            //dataGradesHistogram.add(new Pair<>(grades.get(i), gradeCount[i]));
            dataGradesHistogram
                    .getData()
                    .add(new XYChart.Data<>(grades.get(i), gradeCount[i]));
            dataGradesHistogram.setName(test.getName());
        }
    }

    /**
     * Calculates points histogram data.
     */
    private void calculatePointsHistogram()
    {
        dataPointsHistogram = new XYChart.Series<String, Float>();
        List<Float> points = new ArrayList<>();
        float pointCount = 0;
        while (pointCount <= test.getPointsMax())
        {
            points.add(pointCount);
            pointCount += 0.5;
        }

        int[] pointsCount = new int[points.size()];

        for (TestResult testResult : testResults)
        {
            pointsCount[points.indexOf(testResult.getPointsEarned())]++;
        }

        for (int i = 0; i < points.size(); i++)
        {
            dataPointsHistogram
                    .getData()
                    .add(new XYChart.Data<>(points.get(i).toString(), pointsCount[i]));
            dataPointsHistogram.setName(test.getName());
        }
    }

    /**
     * Calculates averages and medians.
     */
    private void calculateAveragesAndMedians()
    {
        List<Float> points = new ArrayList<>();
        float totalPoints = 0;
        for (TestResult testResult : testResults)
        {
            totalPoints += testResult.getPointsEarned();
            points.add(testResult.getPointsEarned());
        }

        dataAveragePoints = totalPoints / testResults.size();

        Collections.sort(points);
        int pointsSize = points.size();
        if (pointsSize % 2 == 0)
        {
            dataMedianPoints = (points.get(pointsSize / 2) + points.get(pointsSize / 2 - 1)) / 2;
        } else
        {
            dataMedianPoints = points.get(pointsSize / 2);
        }

        dataMedianGrade = test.getGrade(dataMedianPoints);
    }

    //</editor-fold>
}
