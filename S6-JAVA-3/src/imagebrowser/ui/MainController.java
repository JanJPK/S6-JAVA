package imagebrowser.ui;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.List;

public class MainController
{

    public FlowPane miniaturesFlowPane;
    public StackPane treeStackPane;
    public GridPane mainGridPane;

    public void initialize()
    {

    }


    public void selectRootDirectory()
    {
        File rootDirectory;
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choose the root directory");
        rootDirectory = dc.showDialog(mainGridPane.getScene().getWindow());
        if (rootDirectory.isDirectory())
        {
            initializeTreeView(rootDirectory);
        }
    }

    private void initializeTreeView(File rootDirectory)
    {
        treeStackPane.getChildren().clear();
        TreeItem<TreeDirectory> rootTreeItem = new TreeItem<>(new TreeDirectory(rootDirectory));
        initializeTreeItem(rootTreeItem);
        treeStackPane.getChildren().add(new TreeView<>(rootTreeItem));
    }

    private void initializeTreeItem(TreeItem<TreeDirectory> treeItem)
    {
        TreeDirectory directory = treeItem.getValue();
        List<File> subdirectories = directory.processDirectory();
        for (File subdirectory : subdirectories)
        {
            TreeItem<TreeDirectory> child = new TreeItem<>(new TreeDirectory(subdirectory));
            initializeTreeItem(child);
            treeItem.getChildren().add(child);
        }
    }

}
