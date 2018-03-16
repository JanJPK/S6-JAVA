package imagebrowser.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.List;

public class MainController
{

    //<editor-fold desc="variables">

    public FlowPane miniaturesFlowPane;
    public StackPane treeStackPane;
    public GridPane mainGridPane;
    public Label selectedDirectoryLabel;
    public StackPane imageStackPane;
    private TreeView<TreeDirectory> treeView;
    private TreeDirectory selectedDirectory;
    private ExtendedImageView selectedImageView;

    //</editor-fold>

    //<editor-fold desc="initialize">

    public void initialize()
    {
    }

    //</editor-fold>

    //<editor-fold desc="tree-view-methods">

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

        treeView = new TreeView<>(rootTreeItem);
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::treeViewMouseClicked);

        treeStackPane.getChildren().add(treeView);
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

    private void treeViewMouseClicked(MouseEvent event)
    {
        Node node = event.getPickResult().getIntersectedNode();
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null))
        {
            selectedDirectory = (TreeDirectory) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getValue();
            selectedDirectoryLabel.setText("Now browsing: "
                    + selectedDirectory.getDirectory().getName()
                    + " | Images: "
                    + selectedDirectory.getImageViews().size());
            if (selectedDirectory.hasImages())
            {
                loadMiniatures();
            }
        }

    }

    //</editor-fold>

    //<editor-fold desc="miniatures-methods">

    private void loadMiniatures()
    {
        miniaturesFlowPane.getChildren().clear();
        for (ExtendedImageView imageView : selectedDirectory.getImageViews())
        {
            addEventHandlerToImageView(imageView);
            miniaturesFlowPane.getChildren().add(imageView);
            new Thread(imageView).start();
        }
    }

    private void addEventHandlerToImageView(ExtendedImageView extendedImageView)
    {
        extendedImageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event ->
                {
                    selectedImageView = extendedImageView;
                    loadImage();
                });

    }

    public void testMiniature()
    {
        miniaturesFlowPane.getChildren().add(new ExtendedImageView(null));
    }


    //</editor-fold>

    //<editor-fold desc="image-methods">

    private void loadImage()
    {
        imageStackPane.getChildren().clear();
        Image image = new Image("file:" + selectedImageView.getImageSource().getAbsolutePath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(imageStackPane.getHeight());
        imageView.setFitWidth(imageStackPane.getWidth());
        imageView.setPreserveRatio(true);
        imageStackPane.getChildren().add(imageView);
    }

    //</editor-fold>
}