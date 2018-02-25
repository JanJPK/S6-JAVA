package testcheck.library;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Answer
{

    //<editor-fold desc="variables">
    @XmlElement(name = "answerText")
    private String text;
    @XmlElement(name = "answerCorrectness")
    private boolean correct;

    //</editor-fold>

    //<editor-fold desc="constructor">
    public Answer(String text, boolean correct)
    {
        this.text = text;
        this.correct = correct;
    }

    public Answer()
    {

    }
    //</editor-fold>

    //<editor-fold desc="get/set">
    public boolean isCorrect()
    {
        return correct;
    }

    public void setCorrect(boolean correct)
    {
        this.correct = correct;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
    //</editor-fold>

    //<editor-fold desc="methods">

    //</editor-fold>
}
