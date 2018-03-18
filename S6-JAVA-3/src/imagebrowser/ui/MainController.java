package imagebrowser.ui;

import imagebrowser.plugin.ExtendedClassLoader;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainController
{

    //<editor-fold desc="variables">

    public FlowPane miniaturesFlowPane;
    public StackPane treeStackPane;
    public GridPane mainGridPane;
    public Label selectedDirectoryLabel;
    public StackPane imageStackPane;
    public ImageView selectedImageView;
    private TreeView<TreeDirectory> treeView;
    private TreeDirectory selectedDirectory;
    private ExtendedImageView selectedMiniatureImageView;
    private ExtendedClassLoader classLoader;

    //</editor-fold>

    //<editor-fold desc="initialize">

    public void initialize()
    {
        classLoader = new ExtendedClassLoader();
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
                    selectedMiniatureImageView = extendedImageView;
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
        Image image = new Image("file:"
                + selectedMiniatureImageView
                .getImageSource()
                .getAbsolutePath());
        selectedImageView.setImage(image);
        selectedImageView.setFitHeight(imageStackPane.getHeight());
        selectedImageView.setFitWidth(imageStackPane.getWidth());
        selectedImageView.setPreserveRatio(true);
    }

    public void saveImage()
    {
        if (selectedImageView != null)
        {
            File file = selectedMiniatureImageView.getImageSource();
            BufferedImage bufferedImage = SwingFXUtils
                    .fromFXImage(selectedImageView.getImage(), null);
            try
            {
                ImageIO.write(bufferedImage, "png", file);
                selectedMiniatureImageView.setImageSource(file);
                selectedMiniatureImageView.reloadImage();
            } catch (IOException ex)
            {
                showDialogError("IO exception!", "Unable to save image.");
            }

        }
    }

    public void modifyImageGrayscale()
    {
        Class cl = classLoader.getClass("imagebrowser.plugin.ImageModifier");
        Image input = selectedImageView.getImage();
        Image output = classLoader.invokeImage(cl, "grayscale", input);
        selectedImageView.setImage(output);
    }

    public void modifyImageRotate()
    {
        Class cl = classLoader.getClass("imagebrowser.plugin.ImageModifier");
        Image input = selectedImageView.getImage();
        Image output = classLoader.invokeImage(cl, "rotate", input);
        selectedImageView.setImage(output);
    }

    private String getExtension(File file)
    {
        int index = file.getPath().lastIndexOf('.');
        return file.getPath().substring(index + 1);
    }

    //</editor-fold>

    //<editor-fold desc="misc-methods">

    private void showDialogError(String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //</editor-fold>
}