package testcheck.library;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Answer. Contains text of an answer plus a boolean value indicating whether its correct or not.
 */
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

    /**
     * Instantiates a new Answer.
     *
     * @param text    the actual answer.
     * @param correct the value specifying whether its correct.
     */
    public Answer(String text, boolean correct)
    {
        this.text = text;
        this.correct = correct;
    }

    /**
     * Instantiates a new Answer.
     */
    public Answer()
    {

    }

    //</editor-fold>


    //<editor-fold desc="get/set">

    /**
     * Is correct boolean.
     *
     * @return the boolean
     */
    public boolean isCorrect()
    {
        return correct;
    }

    /**
     * Sets correct.
     *
     * @param correct the correct
     */
    public void setCorrect(boolean correct)
    {
        this.correct = correct;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText()
    {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text)
    {
        this.text = text;
    }

    //</editor-fold>

}
