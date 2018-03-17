package imagebrowser.plugin;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class ImageConverter
{
    public Image bufferedImageToImage(BufferedImage input)
    {
        return SwingFXUtils.toFXImage(input, null);
    }

    public BufferedImage imageToBufferedImage(Image input)
    {
        return SwingFXUtils.fromFXImage(input, null);
    }
}
