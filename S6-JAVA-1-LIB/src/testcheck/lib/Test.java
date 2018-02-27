package testcheck.lib;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type testcheck.lib.Test; collection of Questions. Also contains grading rules.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Test
{

    //<editor-fold desc="variables">

    @XmlElement
    private static String[] grades = new String[]{"3.0", "3.5", "4.0", "4.5", "5.0"};
    @XmlElementWrapper(name = "questions")
    @XmlElement(name = "question")
    private List<Question> questions;
    @XmlElement
    private int pointsMax;
    @XmlElement
    private String name;
    @XmlElement
    private int[] pointThresholds;

    //</editor-fold>

    //<editor-fold desc="constructor">

    /**
     * Instantiates a new testcheck.lib.Test.
     */
    public Test()
    {
        questions = new ArrayList<>();
        pointThresholds = new int[5];
    }

    //</editor-fold>

    //<editor-fold desc="get/set">

    /**
     * Gets questions.
     *
     * @return the questions
     */
    public List<Question> getQuestions()
    {
        return questions;
    }

    /**
     * Sets questions.
     *
     * @param questions the questions
     */
    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }


    /**
     * Gets a single question.
     *
     * @param i the index
     * @return the question
     */
    public Question getQuestion(int i)
    {
        if (i >= 0 && i < questions.size())
        {
            return questions.get(i);
        }
        return null;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets points max.
     *
     * @return the points max
     */
    public int getPointsMax()
    {
        return questions.size();
    }

    /**
     * Get point thresholds.
     *
     * @return the int[]
     */
    public int[] getPointThresholds()
    {
        return pointThresholds;
    }

    /**
     * Sets point threshold of value at i.
     *
     * @param i     the
     * @param value the value
     */
    public void setPointThreshold(int i, int value)
    {
        if (i >= 0 && i < pointThresholds.length)
        {
            if (i == pointThresholds.length - 1)
            {
                pointThresholds[i] = value;
            } else
            {
                if (value < pointThresholds[i + 1])
                {
                    pointThresholds[i] = value;
                }
            }

        }
    }

    /**
     * Gets grade.
     *
     * @param points the points
     * @return the grade
     */
    public String getGrade(float points)
    {
        if (points < pointThresholds[0])
            return "2.0";

        for (int i = 0; i < pointThresholds.length; i++)
        {
            if (points <= pointThresholds[i])
            {
                return grades[i];
            }
        }

        return null;
    }

    /**
     * Get possible grades string[].
     *
     * @return the string[]
     */
    public String[] getGrades()
    {
        return grades;
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    /**
     * Adds a new question.
     *
     * @param question the question
     */
    public void addQuestion(Question question)
    {
        questions.add(question);
        pointsMax++;
    }

    //</editor-fold>

}
