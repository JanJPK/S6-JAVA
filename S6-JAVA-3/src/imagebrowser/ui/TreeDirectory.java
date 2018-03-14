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
    private List<File> images;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public TreeDirectory(File directory)
    {
        this.directory = directory;
        images = new ArrayList<>();
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

    public List<File> getImages()
    {
        return images;
    }

    public void setImages(List<File> images)
    {
        this.images = images;
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
                    images.add(file);
            }
        }
        return childrenDirectories;
    }

    //</editor-fold>

}
