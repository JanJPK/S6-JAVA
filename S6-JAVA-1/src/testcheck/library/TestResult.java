package testcheck.library;

import javax.xml.bind.annotation.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type TestResult contains answers of students.
 */
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
    private String grade;

    //</editor-fold>

    //<editor-fold desc="constructor">

    /**
     * Instantiates a new Test result.
     *
     * @param file the file from which answers must be loaded
     */
    public TestResult(File file)
    {
        answers = new ArrayList<>();
        load(file);
    }

    //</editor-fold>

    //<editor-fold desc="get/set">

    /**
     * Gets student id.
     *
     * @return the student id
     */
    public int getStudentId()
    {
        return studentId;
    }

    /**
     * Sets student id.
     *
     * @param studentId the student id
     */
    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    /**
     * Gets student name.
     *
     * @return the student name
     */
    public String getStudentName()
    {
        return studentName;
    }

    /**
     * Sets student name.
     *
     * @param studentName the student name
     */
    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    /**
     * Gets answers.
     *
     * @return the answers
     */
    public List<List<Boolean>> getAnswers()
    {
        return answers;
    }

    /**
     * Gets grade.
     *
     * @return the grade
     */
    public String getGrade()
    {
        return grade;
    }

    /**
     * Sets grade.
     *
     * @param grade the grade
     */
    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    /**
     * Gets answer.
     *
     * @param questionId the question id
     * @return the answer
     */
    public List<Boolean> getAnswer(int questionId)
    {
        return answers.get(questionId);
    }

    /**
     * Gets points earned.
     *
     * @return the points earned
     */
    public float getPointsEarned()
    {
        return pointsEarned;
    }

    /**
     * Sets points earned.
     *
     * @param pointsEarned the points earned
     */
    public void setPointsEarned(float pointsEarned)
    {
        this.pointsEarned = pointsEarned;
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    /**
     * Checks answers with answer key taken from Test object.
     *
     * @param test object containing answer key
     */
    public void grade(Test test)
    {
        float result = 0;
        for (int i = 0; i < answers.size(); i++)
        {
            float partialResult = 1;
            List<Boolean> answerKey = test.getQuestion(i).getAnswerKey();
            List<Boolean> answer = answers.get(i);

            for (int j = 0; j < answerKey.size(); j++)
            {
                if (answer.get(j) != answerKey.get(j))
                {
                    partialResult -= 0.5;
                }
            }

            if(partialResult > 0)
            {
                result += partialResult;
            }
        }
        pointsEarned = result;

        grade = test.getGrade(pointsEarned);
    }

    /**
     * Loads answers from .csv.
     * Reads file line by line and converts them to arrays of boolean.
     *
     * @param file location of the .csv file.
     */
    private void load(File file)
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
