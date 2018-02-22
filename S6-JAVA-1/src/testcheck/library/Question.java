package testcheck.library;

import java.util.ArrayList;
import java.util.List;

public class Question
{
    private int id;
    private String text;
    private List<Answer> answers;
    private int goodAnswers;
    private boolean[] answerKey;

    public Question()
    {
        answers = new ArrayList<>();
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
        if (id >= 0 && id < answers.size())
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

    public int getGoodAnswers()
    {
        return goodAnswers;
    }

    public void setGoodAnswers(int goodAnswers)
    {
        this.goodAnswers = goodAnswers;
    }

    public boolean[] getAnswerKey()
    {
        return answerKey;
    }

    private void setAnswerKey()
    {
        answerKey = new boolean[answers.size()];
        goodAnswers = 0;
        for (int i = 0; i < answers.size(); i++)
        {
            Answer answer = answers.get(i);
            answerKey[i] = answer.isCorrect();
            goodAnswers++;
        }

        if (goodAnswers == 0)
        {
            // TODO: Exception
        }
    }

    public void addAnswer(String text, boolean correct)
    {
        if (!text.equals(""))
        {
            answers.add(new Answer(text, correct));
        }
    }


}
