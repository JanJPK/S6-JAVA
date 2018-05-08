package genotype;

import genotype.db.DatabaseController;
import genotype.db.model.Examined;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GenotypeAppController
{
    //<editor-fold desc="variables">

    public TextField textFieldGenotype;
    public TextArea textAreaHistory;
    public TextArea textAreaTransaction;
    public TextField textFieldDatabase;

    private DatabaseController controller;
    private GenotypeAnalyzer analyzer;
    private List<String> genotypes;
    private List<Examined> examinedGenotypes;

    //</editor-fold>

    public void initialize()
    {
        genotypes = new ArrayList<>();
        examinedGenotypes = new ArrayList<>();
        textFieldDatabase.setText("D:/pwr/S6-JAVA/S6-JAVA-5/genotypedb.db");
    }

    //<editor-fold desc="genotype">

    public void addGenotype()
    {
        if (controller == null || analyzer == null || textFieldGenotype.getText().length() != 6)
            return;

        Examined examined = analyzer.analyze(textFieldGenotype.getText());
        controller.addSingleExamined(examined);

        textFieldGenotype.clear();
        textAreaHistory.appendText(examined + "\n");

        examinedGenotypes.add(examined);
    }

    public void addGenotypeToTransaction()
    {
        if (controller == null || analyzer == null || textFieldGenotype.getText().length() != 6)
            return;

        String genotype = textFieldGenotype.getText();
        if (!genotypes.contains(genotype))
        {
            genotypes.add(genotype);
            textAreaTransaction.appendText(genotype + "\n");
        }
        textFieldGenotype.clear();
    }

    public void commitTransaction()
    {
        if (controller == null || analyzer == null)
            return;

        List<Examined> examinedList = new ArrayList<>();
        for (String genotype : genotypes)
        {
            examinedList.add(analyzer.analyze(genotype));
        }

        controller.addMultipleExamined(examinedList);

        textAreaTransaction.clear();
        for (Examined examined : examinedList)
        {
            textAreaHistory.appendText(examined + "\n");
        }

        examinedGenotypes.addAll(examinedList);
    }


    //</editor-fold>

    //<editor-fold desc="database">

    public void connect()
    {
        controller = new DatabaseController();
        controller.connect(textFieldDatabase.getText());
        analyzer = new GenotypeAnalyzer(controller.getAllFlagella(), controller.getAllToughness());
    }

    public void disconnect()
    {
        controller.disconnect();
        controller = null;
        analyzer = null;
    }

    //</editor-fold>

    public void exportHistoryToXML()
    {
        if (controller == null || analyzer == null || examinedGenotypes.size() == 0)
            return;

        try
        {
            File file = new File("examined.xml");
            JAXBContext context = JAXBContext.newInstance(Examined.class, ExaminedCollection.class);
            Marshaller marshaller = context.createMarshaller();

            ExaminedCollection examinedCollection = new ExaminedCollection(examinedGenotypes);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(examinedCollection, file);
        } catch (JAXBException ex)
        {
            System.out.println("Exception at exportHistoryToXML.");
            System.out.println(ex.getMessage());
        }

    }

}
