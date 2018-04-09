package space.bean;

import javafx.scene.layout.VBox;

public class BeanTestController
{

    public VBox vbox;

    public void initialize()
    {
        vbox.getChildren().add(new Bean());
    }
}
