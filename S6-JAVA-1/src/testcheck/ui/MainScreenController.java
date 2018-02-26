package testcheck.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import testcheck.library.*;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main controller.
 */
public class MainScreenController
{

    //<editor-fold desc="variables">

    //<editor-fold desc="ui">

    /**
     * The constant questions.
     */
    public static final ObservableList questions = FXCollections.observableArrayList();
    /**
     * The constant testResults.
     */
    public static final ObservableList testResults = FXCollections.observableArrayList();
    /**
     * The Main grid.
     */
    public GridPane mainGrid;
    /**
     * The Question list view.
     */
    public ListView questionListView;
    /**
     * The Results grid.
     */
    public GridPane resultsGrid;
    /**
     * The Grading grid.
     */
    public GridPane gradingGrid;
    /**
     * The Test results list view.
     */
    public ListView testResultsListView;
    /**
     * The Test result answers v box.
     */
    public VBox testResultAnswersVBox;
    /**
     * The Question answers v box.
     */
    public VBox questionAnswersVBox;
    /**
     * The Grading v box.
     */
    public VBox gradingVBox;
    /**
     * The Test name grading label.
     */
    public Label testNameGradingLabel;
    /**
     * The Histogram h box.
     */
    public HBox histogramHBox;
    /**
     * The Average and median h box.
     */
    public HBox averageAndMedianHBox;

    //</editor-fold>

    private TestStatistics testStatistics;
    private Test test;

    //</editor-fold>

    //<editor-fold desc="start-methods">

    /**
     * Called from start tab.
     * Opens a FileChooser dialog so user can load an .xml file with marshalized Test object.
     */
    public void loadTest()
    {
        test = null;
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose the test");
        File file = fc.showOpenDialog(mainGrid.getScene().getWindow());
        if (file.getPath().endsWith("xml"))
        {
            unmarshalTest(file);
            loadQuestionListView();
            loadGradingVBox();
            showDialogInformation("Test has been loaded.", "");
        } else
        {
            showDialogError("Incorrect file extension!",
                    "File must be of .xml type.");
        }
    }

    /**
     * Create a fake test for testing the program.
     */
    public void createFakeTest()
    {
        Random rng = new Random();
        test = new Test();
        test.setName("Example exam 2018");
        int questionAmount = 15;
        for (int i = 0; i < questionAmount; i++)
        {
            Question question = new Question();
            question.setText("Question " + i);
            question.setId(i + 1);
            question.addAnswer("Answer A-" + i, rng.nextBoolean());
            question.addAnswer("Answer B-" + i, true);
            question.addAnswer("Answer C-" + i, rng.nextBoolean());
            test.addQuestion(question);
        }
        test.setPointThreshold(4, 14);
        test.setPointThreshold(3, 12);
        test.setPointThreshold(2, 10);
        test.setPointThreshold(1, 8);
        test.setPointThreshold(0, 6);

        marshalTest(test, "example_exam");
    }

    //</editor-fold>

    //<editor-fold desc="results-methods">

    /**
     * Load test results.
     */
    public void loadTestResults()
    {
        if (test == null)
        {
            showDialogError("No test loaded!", "Begin by loading a test .xml.");
            return;
        }
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choose the directory containing test results");
        File directory = dc.showDialog(mainGrid.getScene().getWindow());
        File[] allFiles = directory.listFiles();
        List<File> files = new ArrayList<>();
        for (File file : allFiles)
        {
            int i = file.getPath().lastIndexOf('.');
            String extension = file.getPath().substring(i + 1);
            if (extension.equals("csv"))
                files.add(file);
        }

        if (files.size() > 0)
        {
            testStatistics = new TestStatistics();
            testStatistics.setTest(test);
            testStatistics.loadTestResults(files);
            testStatistics.gradeTestResults();

            testResults.addAll(testStatistics.getTestResults());
            testResultsListView.getItems().clear();
            testResultsListView.setItems(testResults);
        } else
        {
            showDialogError("No files found!",
                    "Directory does not contain files  the required format.");
        }
        loadStatistics();
    }

    /**
     * Load test result answers (details).
     */
    public void loadTestResultAnswers()
    {
        int selectedIndex = testResultsListView.getSelectionModel().getSelectedIndex();
        TestResult testResult = testStatistics.getTestResult(selectedIndex);
        List<HBox> hBoxes = new ArrayList<>();

        for (int i = 0; i < testResult.getAnswers().size(); i++)
        {
            List<Boolean> answer = testResult.getAnswer(i);
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            String labelText = String.format("%-15s", "Answer " + (i + 1));
            hBox.getChildren().add(new Label(labelText));
            for (int j = 0; j < answer.size(); j++)
            {
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(answer.get(j));
                checkBox.setDisable(true);
                hBox.getChildren().add(checkBox);
            }
            hBoxes.add(hBox);

        }
        testResultAnswersVBox.getChildren().clear();
        testResultAnswersVBox
                .getChildren()
                .add(new Label("Points: " + testResult.getPointsEarned()));
        testResultAnswersVBox
                .getChildren()
                .add(new Label("Grade: " + testResult.getGrade()));
        testResultAnswersVBox.getChildren().addAll(hBoxes);
        testStatistics.gradeTestResults();
    }

    //</editor-fold>

    //<editor-fold desc="statistics-methods">

    /**
     * Load statistics.
     */
    public void loadStatistics()
    {
        if (test == null)
        {
            showDialogError("No test loaded!", "Begin by loading a test .xml.");
            return;
        }

        if (testStatistics == null || testStatistics.getTestResults().size() <= 0)
        {
            showDialogError("No test results loaded!", "Begin by loading test results from a directory.");
            return;
        }

        testStatistics.calculateStatisticsData();
        histogramHBox.getChildren().clear();
        averageAndMedianHBox.getChildren().clear();
        loadGradeBarChart();
        loadPointsBarChart();
        loadAverageAndMedian();
    }

    private void loadGradeBarChart()
    {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Grades");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Count");

        BarChart bc = new BarChart(xAxis, yAxis);
        bc.getData().add(testStatistics.getDataGradesHistogram());

        histogramHBox.getChildren().add(bc);
    }

    private void loadPointsBarChart()
    {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Points");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Count");

        BarChart bc = new BarChart(xAxis, yAxis);
        bc.getData().add(testStatistics.getDataPointsHistogram());

        histogramHBox.getChildren().add(bc);
    }

    private void loadAverageAndMedian()
    {
        averageAndMedianHBox
                .getChildren()
                .add(new Label("Average points: " + testStatistics.getDataAveragePoints()));
        averageAndMedianHBox
                .getChildren()
                .add(new Label("Median points: " + testStatistics.getDataMedianPoints()));
        averageAndMedianHBox
                .getChildren()
                .add(new Label("Median grade: " + testStatistics.getDataMedianGrade()));
    }

    //</editor-fold>

    //<editor-fold desc="questions-methods">

    /**
     * Load question answers.
     */
    public void loadQuestionAnswers()
    {
        int selectedIndex = questionListView.getSelectionModel().getSelectedIndex();
        Question question = test.getQuestion(selectedIndex);
        List<HBox> hBoxes = new ArrayList<>();

        for (int i = 0; i < question.getAnswers().size(); i++)
        {
            Answer answer = question.getAnswer(i);
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.getChildren().add(new TextField(answer.getText()));
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(answer.isCorrect());
            hBox.getChildren().add(checkBox);
            hBoxes.add(hBox);
        }
        questionAnswersVBox.getChildren().clear();
        questionAnswersVBox.getChildren().addAll(hBoxes);
        //questionAnswersVBox.getChildren().add(saveQuestionAnswersButton);
    }

    /**
     * Load question list view.
     */
    public void loadQuestionListView()
    {
        questions.addAll(test.getQuestions());
        questionListView.getItems().clear();
        questionListView.setItems(questions);
    }

    //</editor-fold>

    //<editor-fold desc="grading-methods">

    /**
     * Load grading v box.
     */
    public void loadGradingVBox()
    {
        if (gradingVBox.getChildren().isEmpty() && test != null)
        {
            testNameGradingLabel.setText(test.getName());
            String[] grades = test.getGrades();
            int[] pointThresholds = test.getPointThresholds();
            for (int i = 0; i < grades.length; i++)
            {
                Label label = new Label("   " + grades[i]);
                TextField textField = new TextField();
                textField.setText(String.valueOf(pointThresholds[i]));
                textField.setMaxWidth(50);
                HBox hBox = new HBox();
                hBox.setSpacing(15);
                hBox.getChildren().addAll(label, textField);
                label = new Label("/ " + test.getPointsMax());
                hBox.getChildren().add(label);
                gradingVBox.getChildren().add(hBox);
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="marshal-methods">

    /**
     * Marshals the Test object into .xml file with given filename.
     *
     * @param test     object to marshal
     * @param filename filename of .xml file
     */
    private void marshalTest(Test test, String filename)
    {
        if (test == null)
        {
            showDialogError("There is no test object to save!", "Load a test object first.");
            return;
        }
        StringWriter sw = new StringWriter();
        JAXB.marshal(test, sw);
        String testXML = sw.toString();

        try
        {
            PrintWriter out = new PrintWriter(filename + ".xml");
            out.print(testXML);
            out.close();
        } catch (FileNotFoundException ex)
        {
            showDialogError("Write error!", "Incorrect filename.");
        }
    }

    /**
     * Unmarshals the given file into Test object.
     *
     * @param file file containing path to proper .xml
     */
    private void unmarshalTest(File file)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(Test.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            test = (Test) unmarshaller.unmarshal(file);
        } catch (JAXBException ex)
        {
            showDialogError("Error loading .xml file.", "Is .xml describing a test?");
        }

    }

    //</editor-fold>

    //<editor-fold desc="dialog-methods">

    /**
     * Displays an error dialog.
     *
     * @param header  dialog's header
     * @param content dialog's content
     */
    private void showDialogError(String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Displays an information dialog.
     *
     * @param header  dialog's header
     * @param content dialog's content
     */
    private void showDialogInformation(String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //</editor-fold>

}
