package testcheck.library;

import javax.xml.bind.annotation.*;
import java.io.BufferedReader;
import java.io.File;
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
    private List<List<Boolean>> answers;
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

    public TestResult(File file)
    {
        answers = new ArrayList<>();
        loadTest(file);
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

    public List<List<Boolean>> getAnswers()
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

    public List<Boolean> getAnswer(int questionId)
    {
        return answers.get(questionId);
    }
    //</editor-fold>

    //<editor-fold desc="methods">

    public void grade(Test test)
    {
        answers = new ArrayList<>();
    }

    /**
     * Checks answers with answer key taken from Test object.
     *
     * @param test object containing answer key.
     */
    public void gradeTestResult(Test test)
    {
        float totalResult = 0;
        for (int i = 0; i < answers.size(); i++)
        {
            float result = 1;
            float penalty = 1 / test.getQuestion(i).getAmountOfGoodAnswers();
            List<Boolean> answerKey = test.getQuestion(i).getAnswerKey();
            List<Boolean> answer = answers.get(i);

            if(answerKey.size() != answer.size())
            {
                // error
                return;
            }

            for (int j = 0; j < answerKey.size(); j++)
            {
                if(answer.get(j) != answerKey.get(j))
                {
                    result -= penalty;
                }
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
     * @param file location of the .csv file.
     */
    private void loadTest(File file)
    {

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] splitLines = line.split(",");
                List<Boolean> answer = new ArrayList<>();
                for (int i = 0; i < splitLines.length; i++)
                {
                    answer.add(splitLines[i].equals("1"));
                }
                answers.add(answer);
            }
            br.close();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }

        String filename = file.getName();
        filename = filename.substring(0, filename.length() - 4);
        String[] splitFilename = filename.split("_");
        studentId = Integer.parseInt(splitFilename[0]);
        studentName = splitFilename[1] + " " + splitFilename[2];

    }

    @Override
    public String toString()
    {
        return studentId + " " + studentName;
    }

    //</editor-fold>

}
