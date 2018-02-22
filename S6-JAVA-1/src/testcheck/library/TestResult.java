package testcheck.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class TestResult
{
    private List<boolean[]> answers;
    private String studentName;
    private int studentId;
    private float pointsEarned;


    public TestResult(String path)
    {
        loadTest(path);

    }

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

    public List<boolean[]> getAnswers()
    {
        return answers;
    }

    public void checkAnswers(Test test)
    {
        float totalResult = 0;
        for (int i = 0; i < answers.size(); i++)
        {
            float result = 1;
            Question question = test.getQuestion(i);
            boolean[] answer = answers.get(i);
            for (int j = 0; j < answer.length; j++)
            {
                if (answer[j] != question.getAnswerKey()[j])
                    result -= 1 / question.getGoodAnswers();

            }
            if (result > 0)
                totalResult += result;
        }
        pointsEarned = totalResult;
    }

    /**
     * Loads answers from .csv.
     * Reads file line by line and converts them to arrays of boolean.
     * @param path location of the .csv file.
     */
    private void loadTest(String path)
    {

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null)
            {
                String[] splitLines = line.split(",");
                boolean[] answer = new boolean[splitLines.length];
                for (int i = 0; i < splitLines.length; i++)
                {
                    answer[i] = splitLines[i].equals("1");
                }
                answers.add(answer);
            }
            br.close();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

}
