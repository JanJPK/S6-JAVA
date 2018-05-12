package deer;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DeerController
{
    public ImageView imageView;
    public Label label;
    private Image image;
    private List<String> answers = Arrays.asList("Yes", "No", "Doesn't matter", "Chill, bro", "Too fat", "Yes, but...", "Never", "100%", "1 in 100");

    public void initialize()
    {
        image = new Image(Main.class.getResourceAsStream("/sacred_deer.png"));
        imageView.setImage(image);
    }

    public void ask()
    {
        int index = ThreadLocalRandom.current().nextInt(0, answers.size());
        label.setText(answers.get(index));
    }
}
