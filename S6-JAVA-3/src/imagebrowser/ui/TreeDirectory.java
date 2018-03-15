package imagebrowser.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TreeDirectory
{

    //<editor-fold desc="variables">

    private File directory;
    private List<ExtendedImageView> imageViews;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public TreeDirectory(File directory)
    {
        this.directory = directory;
        imageViews = new ArrayList<>();
    }

    //</editor-fold>

    //<editor-fold desc="get/set">

    public File getDirectory()
    {
        return directory;
    }

    public void setDirectory(File directory)
    {
        this.directory = directory;
    }

    public List<ExtendedImageView> getImageViews()
    {
        return imageViews;
    }

    public void setImageViews(List<ExtendedImageView> imageViews)
    {
        this.imageViews = imageViews;
    }

    public boolean hasImages()
    {
        return !imageViews.isEmpty();
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    @Override
    public String toString()
    {
        return directory.getName();
    }

    public List<File> processDirectory()
    {
        List<File> childrenDirectories = new ArrayList<>();
        List<File> children = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
        for (File file : children)
        {
            if (file.isDirectory())
                childrenDirectories.add(file);
            else
            {
                String path = file.getPath();
                if (path.endsWith(".png") || path.endsWith(".jpg"))
                    addImageView(file);
            }
        }
        return childrenDirectories;
    }

    private void addImageView(File file)
    {
        imageViews.add(new ExtendedImageView(file));
    }

    public void reloadImages()
    {
        imageViews.clear();
        List<File> children = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
        for (File file : children)
        {
            if (file.isFile())
                if (file.getPath().endsWith(".png") || file.getPath().endsWith(".jpg"))
                    addImageView(file);
        }
    }

    public void reloadImage(ExtendedImageView imageView)
    {
        imageViews.remove(imageView);
        addImageView(imageView.getImageSource());
    }

    //</editor-fold>

}
