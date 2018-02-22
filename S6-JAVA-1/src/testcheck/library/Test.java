package testcheck.library;

import java.util.ArrayList;
import java.util.List;

public class Test
{
    private List<Question> questions;
    private int pointsMax;
    private List<boolean[]> answerKeys;


    public List<Question> getQuestions()
    {
        return questions;
    }

    public boolean[] getAnswerKey(int id)
    {
        if(id >= 0 && id < questions.size())
        {
            //return questions.get(id).geta;
        }
        return null;
    }

    public Question getQuestion(int id)
    {
        if(id >= 0 && id < questions.size())
        {
            return questions.get(id);
        }
        return null;
    }

    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }

    public List<boolean[]> getAnswerKeys()
    {
        return answerKeys;
    }

    private void setAnswerKeys()
    {
        answerKeys = new ArrayList<>();
        for(Question question : questions)
        {
            answerKeys.add(question.getAnswerKey());
        }
    }
}
