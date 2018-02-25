package testcheck.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import testcheck.library.Question;
import testcheck.library.Test;

import javax.xml.bind.JAXB;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
    public FlowPane questionAnswersFlowPane;
    public Button buttonSaveQuestionAnswers;
    public GridPane resultsGrid;
    public GridPane questionsGrid;
    public GridPane gradingGrid;

    //</editor-fold>


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

        if(files.size() > 0)
        {

        }
        else
        {
            showDialogError("No files found!",
                    "Directory does not contain files in required format.");
        }
    }

    public void loadTestResultAnswers()
    {

    }

    public void createTestResult()
    {

    }

    //</editor-fold>

    //<editor-fold desc="methods">

    private void initializeSaveQuestionAnswersButton()
    {
        if(buttonSaveQuestionAnswers == null)
        {
            buttonSaveQuestionAnswers = new Button();
            buttonSaveQuestionAnswers.setText("Save changes");
            buttonSaveQuestionAnswers.setOnAction(event -> loadQuestionAnswers());
        }
    }


    public void createTest()
    {
        initializeSaveQuestionAnswersButton();
        Random rng = new Random();
        test = new Test();
        int questionAmount = 15;
        for (int i = 0; i < questionAmount; i++)
        {
            Question question = new Question();
            question.setText("Question " + i);
            question.setId(i + 1);
            question.addAnswer("Answer A-" + i, rng.nextBoolean());
            question.addAnswer("Answer B-" + i, rng.nextBoolean());
            question.addAnswer("Answer C-" + i, rng.nextBoolean());
            test.addQuestion(question);
        }
        loadQuestionListView(test);
    }

    public void loadQuestionListView(Test test)
    {
        questions.addAll(test.getQuestions());
        questionListView.getItems().clear();
        questionListView.setItems(questions);
    }

    public void loadQuestionAnswers()
    {
        int selectedIndex = questionListView.getSelectionModel().getSelectedIndex();
        //int selectedIndex = 5;
        Question question = test.getQuestion(selectedIndex);
        List<TextField> textFields = new ArrayList<>();
        for (int i = 0; i < question.getAnswers().size(); i++)
        {
            textFields.add(new TextField());
            textFields.get(i).setText(question.getAnswer(i).getText());
        }
        questionAnswersFlowPane.getChildren().clear();
        questionAnswersFlowPane.getChildren().addAll(textFields);
        questionAnswersFlowPane.getChildren().add(buttonSaveQuestionAnswers);
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

    private void showDialogError(String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //</editor-fold>

    //<editor-fold desc="marshal-methods">

    private void marshalTest(Test test)
    {

    }

    private void unmarshalTest(File file)
    {

    }

    //</editor-fold>

}
