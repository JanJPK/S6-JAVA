package testcheck.library;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Test
{

    //<editor-fold desc="variables">
    @XmlElementWrapper(name = "questions")
    @XmlElement(name = "question")
    private List<Question> questions;
    @XmlElement
    private int pointsMax;
    @XmlElement
    private String name;

    //</editor-fold>

    //<editor-fold desc="constructor">
    public Test()
    {
        questions = new ArrayList<>();
    }

    //</editor-fold>

    //<editor-fold desc="get/set">
    public List<Question> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }

    public boolean[] getAnswerKey(int id)
    {
        if (id >= 0 && id < questions.size())
        {
            //return questions.get(id).geta;
        }
        return null;
    }

    public Question getQuestion(int id)
    {
        if (id >= 0 && id < questions.size())
        {
            return questions.get(id);
        }
        return null;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPointsMax()
    {
        return pointsMax;
    }

    public void setPointsMax(int pointsMax)
    {
        this.pointsMax = pointsMax;
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    public void addQuestion(Question question)
    {
        questions.add(question);
        pointsMax++;
    }

    //</editor-fold>

}
