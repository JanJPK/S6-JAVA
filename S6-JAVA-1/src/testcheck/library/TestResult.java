package testcheck.library;

import javax.xml.bind.annotation.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestResult
{
    //<editor-fold desc="variables">

    @XmlElementWrapper(name = "answers")
    @XmlElement(name = "answer")
    private List<boolean[]> answers;
    @XmlElement
    private String studentName;
    @XmlElement
    private int studentId;
    @XmlElement
    private float pointsEarned;
    @XmlElement
    private float grade;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public TestResult(String path)
    {
        answers = new ArrayList<>();
        loadTest(path);
    }

    //</editor-fold>

    //<editor-fold desc="get/set">

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

    public float getGrade()
    {
        return grade;
    }

    public void setGrade(float grade)
    {
        this.grade = grade;
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    public void grade(Test test)
    {

    }

    /**
     * Checks answers with answer key taken from Test object.
     *
     * @param test object containing answer key.
     */
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
                if (answer[j] != question.getAnswerKey().get(i))
                    result -= 1 / question.getAmountOfGoodAnswers();

            }
            if (result > 0)
                totalResult += result;
        }
        pointsEarned = totalResult;
    }

    /**
     * Loads answers from .csv.
     * Reads file line by line and converts them to arrays of boolean.
     *
     * @param path location of the .csv file.
     */
    private void loadTest(String path)
    {

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null)
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

    //</editor-fold>

}
