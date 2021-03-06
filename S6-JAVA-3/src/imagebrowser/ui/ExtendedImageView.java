package imagebrowser.ui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.lang.ref.SoftReference;

public class ExtendedImageView extends StackPane implements Runnable
{

    //<editor-fold desc="variables">

    private static BackgroundFill backgroundFill = new BackgroundFill(Color.web("#BFBFBF"), CornerRadii.EMPTY, Insets.EMPTY);
    private File imageSource;
    private SoftReference<Image> image;
    private ImageView imageView;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public ExtendedImageView(File file)
    {
        this.imageSource = file;
        setPrefHeight(80);
        setPrefWidth(80);
        setMaxHeight(80);
        setMaxWidth(80);
        setBackground(new Background(backgroundFill));
        setStyle("-fx-border-color: #0096C9");
        image = new SoftReference<>(null);
        imageView = new ImageView();
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);
    }

    //</editor-fold>

    //<editor-fold desc="get/set">

    public File getImageSource()
    {
        return imageSource;
    }

    public void setImageSource(File imageSource)
    {
        this.imageSource = imageSource;
    }

    public Image getImage()
    {
        return image.get();
    }

    public void setImage(Image image)
    {
        this.image = new SoftReference<Image>(image);
    }

    public ImageView getImageView()
    {
        return imageView;
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    public void run()
    {
        if (image.get() == null)
        {
            if (imageSource.exists())
            {
                image = new SoftReference<>(new Image("file:" + imageSource.getPath()));
            }
        }
        imageView.setImage(image.get());

    }

    public void reloadImage()
    {
        image = new SoftReference<>(new Image("file:" + imageSource.getPath()));
        imageView.setImage(image.get());
    }

    //</editor-fold>
}
