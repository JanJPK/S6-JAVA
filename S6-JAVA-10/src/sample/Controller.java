package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Controller
{
    public VBox vbox;
    public Button buttonDifficulty;
    private List<CustomButton> buttons;
    private List<Integer> availableButtons;
    private Difficulty difficulty;
    private int size = 5;

    private enum Difficulty
    {
        EASY, HARD
    }

    public void initialize_grid()
    {

        buttons = new ArrayList<>();
        for(int i = 0; i < size*size; i++)
        {
            CustomButton button = new CustomButton();
            button.setPrefHeight(100);
            button.setPrefWidth(100);
            button.setPosition(i);
            button.setOnAction(event -> handlePlayer(button));
            button.setState(CustomButtonState.NONE);
            buttons.add(button);
        }

        for(int i = 0; i < buttons.size(); i+=size)
        {
            HBox hbox = new HBox();
            for(int j = 0; j < size; j++)
            {
                buttons.get(i+j).setParentBox(hbox);
                hbox.getChildren().add(buttons.get(i+j));
            }
            vbox.getChildren().add(hbox);
        }

    }

    public void initialize()
    {
        difficulty = Difficulty.EASY;
        buttonDifficulty.setText("EASY");

        availableButtons = new LinkedList<>();
        for(int i = 0; i < size*size; i++)
        {
            availableButtons.add(i);
        }

        initialize_grid();
    }

    public void handleButtonDifficulty()
    {
        if(difficulty == Difficulty.EASY)
        {
            difficulty = Difficulty.HARD;
            buttonDifficulty.setText("HARD");
        }
        else
        {
            difficulty = Difficulty.EASY;
            buttonDifficulty.setText("EASY");
        }
    }

    private void victory(CustomButtonState state)
    {
        System.out.println("victory: " + state);
    }

    private void loss()
    {
        System.out.println("loss");
    }

    private void checkWinCondition(CustomButton button)
    {
        if(availableButtons.size() == 0)
        {
            loss();
            return;
        }

        int rowCounter = 0;
        int xPosition = button.getPosition();
        while(xPosition >= size)
            xPosition -= size;

        for(int i = 0; i < size; i++)
        {
            int index = xPosition + (i * size);
            if(i == (size - 1) && buttons.get(index).getState() == button.getState())
               rowCounter += size;
            if (buttons.get(index).getState() != button.getState())
                break;
        }
        if(rowCounter >= size)
        {
            victory(button.getState());
            return;
        }
        rowCounter = 0;

        for(Node n : button.getParentBox().getChildren())
        {
            CustomButton cb = (CustomButton)n;
            if(cb.getState() == button.getState())
            {
                rowCounter++;
            }

        }
        if(rowCounter >= size)
        {
            victory(button.getState());
            return;
        }
        rowCounter = 0;


        for(int i = 0; i < size*size; i+=(size+1))
        {
            if(buttons.get(i).getState() == button.getState())
                rowCounter++;
        }
        if(rowCounter >= size)
        {
            victory(button.getState());
            return;
        }
        rowCounter = 0;

        for(int i = size - 1; i <= size*(size-1); i+=(size-1))
        {
            if(buttons.get(i).getState() == button.getState())
                rowCounter++;
        }
        if(rowCounter >= size)
        {
            victory(button.getState());
            return;
        }
    }

    private void handlePlayer(CustomButton button)
    {
        System.out.println("player: " + button.getPosition());
        if(button.getState() == CustomButtonState.NONE)
        {
            button.setState(CustomButtonState.PLAYER);
            button.setText("X");
            for(Integer i : availableButtons)
            {
                if(i == button.getPosition())
                {
                    availableButtons.remove(i);
                    break;
                }
            }
            checkWinCondition(button);
            handleAI(button.getPosition());
        }
    }

    private void handleAI(int playerMove)
    {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try
        {
            engine.eval(new FileReader("ai//"+difficulty+".js"));
            Invocable invocable = (Invocable)engine;
            int aiMove = 0;
            switch (difficulty)
            {
                case EASY:
                {
                    aiMove = (int) invocable.invokeFunction("act", availableButtons);
                    break;
                }
                case HARD:
                {
                    aiMove = (int)invocable.invokeFunction("act", availableButtons, playerMove);
                    break;
                }
            }

            System.out.println("ai: " + aiMove);
            CustomButton button = buttons.get(aiMove);
            button.setState(CustomButtonState.AI);
            button.setText("O");
            for(Integer i : availableButtons)
            {
                if(i == button.getPosition())
                {
                    availableButtons.remove(i);
                    break;
                }
            }
            checkWinCondition(button);

        } catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return;
        }
    }


}


