package testcheck.library;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

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
    @XmlElement(name = "amountOfGoodAnswers")
    private int amountOfGoodAnswers;
    @XmlElement(name = "answerKey")
    private List<Boolean> answerKey;

    //</editor-fold>

    //<editor-fold desc="constructor">
    public Question()
    {
        answers = new ArrayList<>();
        answerKey = new ArrayList<>();
    }

    //</editor-fold>

    //<editor-fold desc="get/set">
    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Answer getAnswer(int id)
    {
        if (id >= 0 && id < answers.size())
        {
            return answers.get(id);
        }
        return null;
    }

    public List<Answer> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<Answer> answers)
    {
        this.answers = answers;
    }

    public int getAmountOfGoodAnswers()
    {
        return amountOfGoodAnswers;
    }

    public void setAmountOfGoodAnswers(int amountOfGoodAnswers)
    {
        this.amountOfGoodAnswers = amountOfGoodAnswers;
    }

    public List<Boolean> getAnswerKey()
    {
        return answerKey;
    }

    public void setAnswerKey(List<Boolean> answerKey)
    {
        this.answerKey = answerKey;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    private void setAnswerKey()
    {
        //answerKey = new boolean[answers.size()];
        amountOfGoodAnswers = 0;
        for (int i = 0; i < answers.size(); i++)
        {
            Answer answer = answers.get(i);
            //answerKey[i] = answer.isCorrect();
            amountOfGoodAnswers++;
        }

        if (amountOfGoodAnswers == 0)
        {
            // TODO: Exception
        }
    }
    //</editor-fold>

    //<editor-fold desc="methods">

    public void addAnswer(String text, boolean correct)
    {
        if (!text.equals(""))
        {
            answers.add(new Answer(text, correct));
            if(correct)
            {
                amountOfGoodAnswers++;
                answerKey.add(true);
            }
            else
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

