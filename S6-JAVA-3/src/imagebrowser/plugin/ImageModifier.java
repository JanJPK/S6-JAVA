package imagebrowser.plugin;

import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageModifier
{
    public Image grayscale(Image input)
    {
        ImageConverter ic = new ImageConverter();
        BufferedImage bufferedInput = ic.imageToBufferedImage(input);
        //BufferedImage bufferedInput = SwingFXUtils.fromFXImage(input, null);
        for (int i = 0; i < bufferedInput.getWidth(); i++)
        {
            for (int j = 0; j < bufferedInput.getHeight(); j++)
            {
                Color color = new Color(bufferedInput.getRGB(i, j));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int gray = (r + g + b) / 3;
                //Color newColor = new Color((int) (0.21 * r), (int) (0.72 * g), (int) (0.07 * b));
                Color newColor = new Color(gray, gray, gray);
                bufferedInput.setRGB(i, j, newColor.getRGB());
            }
        }
        return ic.bufferedImageToImage(bufferedInput);
    }

    public Image rotate(Image input, int degrees)
    {
        Image output = null;

        if (degrees < 0)
            degrees = 90;
        while (degrees > 360)
        {
            degrees -= 360;
        }

        return output;
    }
}
