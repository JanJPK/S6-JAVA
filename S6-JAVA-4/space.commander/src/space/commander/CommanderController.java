package space.commander;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import space.common.SpaceCommand;
import space.common.SpaceCommandType;
import space.common.SpaceInterfaceServer;

import java.util.ArrayList;
import java.util.List;

public class CommanderController
{

    //<editor-fold desc="variables">

    public TextField textFieldAim1;
    public TextField textFieldAim2;
    public TextField textFieldBoost;
    public TextField textFieldCalibrate;
    public TextField textFieldScore;
    public TextArea textAreaPlayers;

    private SpaceCommander commander;
    private SpaceInterfaceServer server;
    private int totalScore;

    //</editor-fold>

    //<editor-fold desc="initialize">

    public void initialize()
    {
        try
        {
            commander = new SpaceCommander("Kapitan Test", this);
            server = commander.getServer();
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        totalScore = 0;
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    public void broadcastCommandAim()
    {
        if (commander != null)
        {
            List<Integer> parameters = new ArrayList<Integer>();
            parameters.add(Integer.valueOf(textFieldAim1.getText()));
            parameters.add(Integer.valueOf(textFieldAim2.getText()));
            broadcastCommand(SpaceCommandType.AIM, parameters);
        }
    }

    public void broadcastCommandBoost()
    {
        if (commander != null)
        {
            List<Integer> parameters = new ArrayList<Integer>();
            parameters.add(Integer.valueOf(textFieldBoost.getText()));
            broadcastCommand(SpaceCommandType.BOOST, parameters);
        }
    }

    public void broadcastCommandCalibrate()
    {
        if (commander != null)
        {
            List<Integer> parameters = new ArrayList<Integer>();
            parameters.add(Integer.valueOf(textFieldCalibrate.getText()));
            broadcastCommand(SpaceCommandType.CALIBRATE, parameters);
        }
    }

    private void broadcastCommand(SpaceCommandType type, List<Integer> parameters)
    {
        try
        {
            server.broadcastCommand(new SpaceCommand(type, parameters));
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void updatePlayerList(List<String> players)
    {
        textAreaPlayers.setText("");
        for (String player : players)
        {
            textAreaPlayers.appendText(player + "\n");
        }
    }

    public void updateScore(int score)
    {
        totalScore += score;
        textFieldScore.setText(String.valueOf(totalScore));
    }

    //</editor-fold>

}
