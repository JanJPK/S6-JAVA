package testcheck.library;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Question. Represents a single question with collection of possible answers.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Question
{

    //<editor-fold desc="variables">

    @XmlElement
    private int id;
    @XmlElement(name = "questionText")
    private String text;
    @XmlElementWrapper(name = "answers")
    @XmlElement(name = "answer")
    private List<Answer> answers;
    @XmlElement(name = "answerKey")
    private List<Boolean> answerKey;

    //</editor-fold>

    //<editor-fold desc="constructor">

    /**
     * Instantiates a new Question.
     */
    public Question()
    {
        answers = new ArrayList<>();
        answerKey = new ArrayList<>();
    }

    //</editor-fold>

    //<editor-fold desc="get/set">

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

    /**
     * Gets a single answer at given position.
     *
     * @param i the i
     * @return the answer
     */
    public Answer getAnswer(int i)
    {
        if (i >= 0 && i < answers.size())
        {
            return answers.get(i);
        }
        return null;
    }

    /**
     * Gets answers.
     *
     * @return the answers
     */
    public List<Answer> getAnswers()
    {
        return answers;
    }

    /**
     * Sets answers.
     *
     * @param answers the answers
     */
    public void setAnswers(List<Answer> answers)
    {
        this.answers = answers;
    }

    /**
     * Gets answer key.
     *
     * @return the answer key
     */
    public List<Boolean> getAnswerKey()
    {
        return answerKey;
    }

    /**
     * Sets answer key.
     *
     * @param answerKey the answer key
     */
    public void setAnswerKey(List<Boolean> answerKey)
    {
        this.answerKey = answerKey;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    /**
     * Add answer.
     *
     * @param text    the text
     * @param correct the correct
     */
    public void addAnswer(String text, boolean correct)
    {
        if (!text.equals(""))
        {
            answers.add(new Answer(text, correct));
            if (correct)
            {
                answerKey.add(true);
            } else
            {
                answerKey.add(false);
            }

        }
    }

    //</editor-fold>

    @Override
    public String toString()
    {
        return "Question " + id;
    }
}

