package testcheck.library;

import java.util.ArrayList;
import java.util.List;

public class Question
{
    private int id;
    private String text;
    private List<Answer> answers;
    private int goodAnswers;

    public Question()
    {
        answers = new ArrayList<>();
    }

    private void CheckAmountOfGoodAnswers()
    {
        for(Answer answer : answers)
        {
            if(answer.isCorrect())
            {
                goodAnswers++;
            }
        }
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public List<Answer> getAnswers()
    {
        return answers;
    }

    public Answer getAnswer(int id)
    {
        if(id >= 0 && id < answers.size())
        {
            return answers.get(id);
        }
        return null;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void AddAnswer(String text, boolean correct)
    {
        if(!text.equals(""))
        {
            answers.add(new  Answer(text, correct));
        }
    }

    public int getGoodAnswers()
    {
        return goodAnswers;
    }

    public void setGoodAnswers(int goodAnswers)
    {
        this.goodAnswers = goodAnswers;
    }
}
