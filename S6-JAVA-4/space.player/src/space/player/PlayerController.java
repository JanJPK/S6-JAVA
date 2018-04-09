package space.player;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import space.bean.Bean;
import space.common.SpaceCommand;
import space.common.SpaceCommandType;
import space.common.SpaceInterfaceServer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class PlayerController
{
    //<editor-fold desc="variables">

    public VBox vBox;
    public TextField textField;
    public RadioButton radioButtonCALIBRATE;
    public RadioButton radioButtonBOOST;
    public RadioButton radioButtonAIM;
    public TextField textFieldName;
    public SpaceCommand CurrentCommand;

    private SpacePlayer player;
    private SpaceInterfaceServer server;
    private Bean bean;
    private SpaceCommandType clientType;
    private String playerName;


    //</editor-fold>

    //<editor-fold desc="connect">

    public void connect()
    {
        if (radioButtonAIM.isSelected())
            clientType = SpaceCommandType.AIM;
        if (radioButtonBOOST.isSelected())
            clientType = SpaceCommandType.BOOST;
        if (radioButtonCALIBRATE.isSelected())
            clientType = SpaceCommandType.CALIBRATE;
        playerName = textFieldName.getText();

        try
        {
            player = new SpacePlayer(playerName, this, clientType);
            server = player.getServer();
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        initializeBean();
    }


    private void initializeBean()
    {
        bean = new Bean();
        switch (clientType)
        {
            case AIM:
                bean.setModeAim(true);
                break;
            case BOOST:
                bean.setModeBoost(true);
                break;
            case CALIBRATE:
                bean.setModeCalibrate(true);
                break;
        }
        vBox.getChildren().add(bean);

        bean.buttonSendResult.setOnAction(event -> broadcastScore());
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    public void disconnect()
    {
        try
        {
            server.removePlayer(playerName);
        } catch (RemoteException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void broadcastScore()
    {
        if (CurrentCommand != null)
        {
            int score = CurrentCommand.verifyResults(getResults());
            try
            {
                server.broadcastScore(score);
            } catch (RemoteException ex)
            {
                System.out.println(ex.getMessage());
            }
            CurrentCommand = null;
            textField.setText("");
        }
    }

    private List<Integer> getResults()
    {
        List<Integer> results = new ArrayList<>();
        switch (clientType)
        {
            case AIM:
                results.add(Integer.valueOf(bean.getValueAim1()));
                results.add(Integer.valueOf(bean.getValueAim2()));
                break;
            case BOOST:
                results.add(Integer.valueOf(bean.getValueBoost()));
                break;
            case CALIBRATE:
                results.add(bean.getValueCalibrate());
                break;
        }
        return results;
    }

    //</editor-fold>

}
