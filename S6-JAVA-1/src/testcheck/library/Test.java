package testcheck.library;

import java.util.ArrayList;
import java.util.List;

public class Test
{
    private List<Question> questions;



    public List<Question> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }

    public List<Boolean[]> GenerateAnswerKey()
    {
        List<Boolean[]> answerKeys = new ArrayList<>();
        for(Question question : questions)
        {
            Boolean[] answerKey = new Boolean[question.getAnswers().size()];
            for(int i = 0; i < answerKey.length; i++)
            {
                answerKey[i] = question.getAnswer(i).isCorrect();
            }
            answerKeys.add(answerKey);
        }
        return answerKeys;
    }
}
