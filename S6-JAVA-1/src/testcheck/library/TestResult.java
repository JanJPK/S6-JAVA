package testcheck.library;

import java.util.List;

public class TestResult
{
    private List<Boolean[]> answers;
    private String studentName;
    private int studentId;


    public int getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public List<Boolean[]> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<Boolean[]> answers)
    {
        this.answers = answers;
    }

    public int CheckAnswers(List<Boolean[]> answerKeys)
    {
        float totalPoints;
        for(int i = 0; i < answers.size(); i++)
        {
            Boolean[] answer = answers.get(i);
            Boolean[] answerKey = answerKeys.get(i);
            int points;
        }
    }
}
