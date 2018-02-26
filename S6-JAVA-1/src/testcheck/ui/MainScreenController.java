package testcheck.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainScreenController
{

    //<editor-fold desc="variables">

    //<editor-fold desc="ui">

    public static final ObservableList questions = FXCollections.observableArrayList();
    public static final ObservableList testResults = FXCollections.observableArrayList();
    public GridPane mainGrid;
    public ListView questionListView;
    public Button saveQuestionAnswersButton;
    public Button saveTestResultAnswersSaveButton;
    public GridPane resultsGrid;
    public GridPane questionsGrid;
    public GridPane gradingGrid;
    public ListView testResultsListView;
    public VBox testResultAnswersVBox;
    public VBox questionAnswersVBox;
    public VBox gradingVBox;
    public Label testNameGradingLabel;

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
            populateGradingVBox();
            showDialogInformation("Test has been loaded.","");
        } else
        {
            showDialogError("Incorrect file extension!",
                    "File must be of .xml type.");
        }
    }


    /**
     * Called from start tab; moves the user to test edit tab.
     */
    public void newTest()
    {

    }

    //</editor-fold>

    //<editor-fold desc="results-methods">

    public void loadTestResults()
    {
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
                    "Directory does not contain files in required format.");
        }
    }

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
        testResultAnswersVBox.getChildren().addAll(hBoxes);
        //initializeTestResultAnswersSaveButton();
    }

    public void createTestResult()
    {

    }

    public void initializeTestResultAnswersSaveButton()
    {
        if (saveTestResultAnswersSaveButton == null)
        {
            saveTestResultAnswersSaveButton = new Button();
            saveTestResultAnswersSaveButton.setText("Save changes");
            saveTestResultAnswersSaveButton.setOnAction(event -> saveTestResultAnswers());
        }
        testResultAnswersVBox.getChildren().add(saveTestResultAnswersSaveButton);
    }

    public void saveTestResultAnswers()
    {

    }
    
    public void checkTestResults()
    {
        
    }

    //</editor-fold>

    //<editor-fold desc="statistics-methods">


    //</editor-fold>

    //<editor-fold desc="questions-methods">

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

    public void loadQuestionListView()
    {
        questions.addAll(test.getQuestions());
        questionListView.getItems().clear();
        questionListView.setItems(questions);
    }


    //</editor-fold>
    
    //<editor-fold desc="grading-methods">

    public void populateGradingVBox()
    {
        if(gradingVBox.getChildren().isEmpty() && test != null)
        {
            testNameGradingLabel.setText(test.getName());
            String[] grades = new String[] {"3.0", "3.5", "4.0", "4.5", "5.0"};
            int[] pointThresholds = test.getPointThresholds();
            for(int i = 0; i < grades.length;i++)
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
    
    //<editor-fold desc="methods">

    private void initializeSaveQuestionAnswersButton()
    {
        if (saveQuestionAnswersButton == null)
        {
            saveQuestionAnswersButton = new Button();
            saveQuestionAnswersButton.setText("Save changes");
            saveQuestionAnswersButton.setOnAction(event -> loadQuestionAnswers());
        }
    }

    public void createTest()
    {

    }

    public void createFakeTest()
    {
        initializeSaveQuestionAnswersButton();
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

        //loadQuestionListView(test);
        marshalTest(test);
    }


    public void testToXML(Test test)
    {
        StringWriter sw = new StringWriter();
        JAXB.marshal(test, sw);
        String testXML = sw.toString();

        try
        {
            PrintWriter out = new PrintWriter("test.xml");
            out.print(testXML);
            out.close();
        } catch (FileNotFoundException ex)
        {

        }
    }

    public void xmlToTest(String path)
    {

    }



    //</editor-fold>

    //<editor-fold desc="marshal-methods">

    private void marshalTest(Test test)
    {
        StringWriter sw = new StringWriter();
        JAXB.marshal(test, sw);
        String testXML = sw.toString();

        try
        {
            PrintWriter out = new PrintWriter("test.xml");
            out.print(testXML);
            out.close();
        } catch (FileNotFoundException ex)
        {
            showDialogError("Write error", "Incorrect filename.");
        }
    }

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

    private void showDialogError(String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

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
