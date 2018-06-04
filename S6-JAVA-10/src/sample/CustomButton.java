package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class CustomButton extends Button
{
    private int position;
    private CustomButtonState state;
    private HBox parentBox;

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public CustomButtonState getState()
    {
        return state;
    }

    public void setState(CustomButtonState state)
    {
        this.state = state;
    }

    public HBox getParentBox()
    {
        return parentBox;
    }

    public void setParentBox(HBox parentBox)
    {
        this.parentBox = parentBox;
    }
}