package testcheck.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;

public class MainScreenController
{
    public Label helloWorld;
    public GridPane debugGrid;

    public void sayHelloWorld(ActionEvent actionEvent)
    {
        helloWorld.setText("Hello World!");
    }


    public void selectTestResultsFolder(ActionEvent actionEvent)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose folder with test results");
        File selectedFile = fc.showOpenDialog(debugGrid.getScene().getWindow());
    }
}
