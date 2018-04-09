package space.bean;

import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Bean extends GridPane
{
    public final TextField textFieldAim1 = new TextField();
    public final TextField textFieldAim2 = new TextField();
    public final TextField textFieldBoost = new TextField();
    public final Slider sliderCalibrate = new Slider();
    public final TextField textFieldCalibrate = new TextField();
    public final Button buttonSetResult = new Button();
    public final Button buttonSendResult = new Button();

    private final StringProperty valueAim1 = new SimpleStringProperty("");
    private final StringProperty valueAim2 = new SimpleStringProperty("");
    private final StringProperty valueBoost = new SimpleStringProperty("");
    private final IntegerProperty valueCalibrate = new SimpleIntegerProperty(0);

    private final BooleanProperty modeAim = new SimpleBooleanProperty(false);
    private final BooleanProperty modeCalibrate = new SimpleBooleanProperty(false);
    private final BooleanProperty modeBoost = new SimpleBooleanProperty(false);

    public Bean()
    {
        prefHeight(600);
        prefWidth(400);
        initializeUI();
        addListeners();
        disableUI();
    }

    //<editor-fold desc="methods">

    private void initializeUI()
    {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        add(new Label("X:"),0,0);
        add(textFieldAim1, 1, 0);
        add(new Label("Y:"),2,0);
        add(textFieldAim2, 3, 0);

        add(new Label("Boost"),0,1);
        add(textFieldBoost,1,1);

        add(new Label("Calibrate"),0,2);
        add(sliderCalibrate,1,2);
        add(textFieldCalibrate,3,2);
        textFieldCalibrate.setEditable(false);
        sliderCalibrate.setMin(0);
        sliderCalibrate.setMax(100);
        sliderCalibrate.setShowTickMarks(true);
        sliderCalibrate.setBlockIncrement(10);
        sliderCalibrate.setMinorTickCount(5);

        buttonSetResult.setText("Set");
        add(buttonSetResult,1,3);
        buttonSendResult.setText("Send");
        add(buttonSendResult,3,3);

    }

    private void addListeners()
    {

        buttonSetResult.setOnAction(event ->
        {
            if(modeAim.getValue())
            {
                valueAim1.setValue(textFieldAim1.getText());
                valueAim2.setValue(textFieldAim1.getText());
            }
            if(modeBoost.getValue())
            {
                valueBoost.setValue(textFieldBoost.getText());
            }
            if(modeCalibrate.getValue())
            {
                valueCalibrate.setValue((int)sliderCalibrate.getValue());
            }

        });

        sliderCalibrate.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            int castedValue = newValue.intValue();
            sliderCalibrate.setValue(castedValue);
            textFieldCalibrate.setText(String.valueOf(castedValue));
        });

        modeAim.addListener((observable, oldValue, newValue) ->
        {
            textFieldAim1.setDisable(!newValue);
            textFieldAim2.setDisable(!newValue);
        });

        modeBoost.addListener((observable, oldValue, newValue) ->
        {
            textFieldBoost.setDisable(!newValue);
        });

        modeCalibrate.addListener((observable, oldValue, newValue) ->
        {
            sliderCalibrate.setDisable(!newValue);
            textFieldCalibrate.setDisable(!newValue);
        });
    }

    private void disableUI()
    {
        textFieldAim1.setDisable(!modeAim.get());
        textFieldAim2.setDisable(!modeAim.get());
        textFieldBoost.setDisable(!modeBoost.getValue());
        sliderCalibrate.setDisable(!modeCalibrate.getValue());
        textFieldCalibrate.setDisable(!modeCalibrate.getValue());
    }

    //</editor-fold>

    //<editor-fold desc="get/set">

    public String getValueAim1()
    {
        return valueAim1.get();
    }

    public StringProperty valueAim1Property()
    {
        return valueAim1;
    }

    public void setValueAim1(String valueAim1)
    {
        this.valueAim1.set(valueAim1);
    }

    public String getValueAim2()
    {
        return valueAim2.get();
    }

    public StringProperty valueAim2Property()
    {
        return valueAim2;
    }

    public void setValueAim2(String valueAim2)
    {
        this.valueAim2.set(valueAim2);
    }

    public String getValueBoost()
    {
        return valueBoost.get();
    }

    public StringProperty valueBoostProperty()
    {
        return valueBoost;
    }

    public void setValueBoost(String valueBoost)
    {
        this.valueBoost.set(valueBoost);
    }

    public int getValueCalibrate()
    {
        return valueCalibrate.get();
    }

    public IntegerProperty valueCalibrateProperty()
    {
        return valueCalibrate;
    }

    public void setValueCalibrate(int valueCalibrate)
    {
        this.valueCalibrate.set(valueCalibrate);
    }

    public boolean isModeAim()
    {
        return modeAim.get();
    }

    public BooleanProperty modeAimProperty()
    {
        return modeAim;
    }

    public void setModeAim(boolean modeAim)
    {
        this.modeAim.set(modeAim);
    }

    public boolean isModeCalibrate()
    {
        return modeCalibrate.get();
    }

    public BooleanProperty modeCalibrateProperty()
    {
        return modeCalibrate;
    }

    public void setModeCalibrate(boolean modeCalibrate)
    {
        this.modeCalibrate.set(modeCalibrate);
    }

    public boolean isModeBoost()
    {
        return modeBoost.get();
    }

    public BooleanProperty modeBoostProperty()
    {
        return modeBoost;
    }

    public void setModeBoost(boolean modeBoost)
    {
        this.modeBoost.set(modeBoost);
    }

    //</editor-fold>

}
