package fujiwara.ui;

import fujiwara.model.ShopItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShopController
{
    //<editor-fold desc="variables">

    //<editor-fold desc="variables-ui">

    private static final ObservableList<ShopItem> shopItems = FXCollections.observableArrayList();
    private static Locale usLocale = new Locale("en", "US");
    private static Locale plLocale = new Locale("pl", "PL");
    public GridPane mainGrid;
    public ListView shopItemListView;
    public Button newShopItemButton;
    public Button selectDirectoryButton;
    public TextField dateTextField;
    public Label dateLabel;
    public TextField priceTextField;
    public Label priceLabel;
    public Button selectImageButton;
    public Button removeShopItemButton;
    public Button saveShopItemButton;

    //</editor-fold>


    //</editor-fold>

    //<editor-fold desc="detail-view-methods">

    public void initializeDetailView()
    {

    }

    public void createDetailView()
    {

    }

    public void saveDetailView()
    {

    }

    public void deleteDetailView()
    {

    }

    //</editor-fold>

    //<editor-fold desc="list-methods">

    public void initializeListView(List<ShopItem> items)
    {
        shopItems.addAll(items);
        //shopItemListView.getItems().clear();
        shopItemListView.setItems(shopItems);
    }

    public void openDetailView()
    {

    }

    public void selectDirectory()
    {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choose the directory containing objects to load");
        File directory = dc.showDialog(mainGrid.getScene().getWindow());
        File[] allFiles = directory.listFiles();
        List<File> files = new ArrayList<File>();
        for (File file : allFiles)
        {
            int i = file.getPath().lastIndexOf('.');
            String extension = file.getPath().substring(i + 1);
            if (extension.equals("xml"))
                files.add(file);
        }

        if (files.size() > 0)
        {
            List<ShopItem> items = new ArrayList<>();
            for (File file : files)
            {
                items.add(unmarshal(file));
            }
            initializeListView(items);
        } else
        {
            showDialogError("No files found!",
                    "Directory does not contain files in the required format.");
        }
    }

    //</editor-fold>

    //<editor-fold desc="marshal-methods">

    private void marshal(ShopItem item, String filename)
    {
        if (item == null)
        {
            showDialogError("No proper object passed!", "Load an objectfirst.");
            return;
        }

        StringWriter sw = new StringWriter();
        JAXB.marshal(item, sw);
        String testXML = sw.toString();

        try
        {
            PrintWriter out = new PrintWriter(filename + ".xml");
            out.print(testXML);
            out.close();
        } catch (FileNotFoundException ex)
        {
            showDialogError("Write error!", "Incorrect filename.");
        }
    }

    private ShopItem unmarshal(File file)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(ShopItem.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (ShopItem) unmarshaller.unmarshal(file);
        } catch (JAXBException ex)
        {
            showDialogError("Error loading .xml file.", "Is .xml describing a test?");
        }

        return null;
    }

    //</editor-fold>

    //<editor-fold desc="dialog-methods">

    /**
     * Displays an error dialog.
     *
     * @param header  dialog's header
     * @param content dialog's content
     */
    private void showDialogError(String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Displays an information dialog.
     *
     * @param header  dialog's header
     * @param content dialog's content
     */
    private void showDialogInformation(String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //</editor-fold>

    //<editor-fold desc="debig-methods">

    public void createTestData()
    {
        try
        {
            shopItems.clear();
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            ShopItem item;
            List<ShopItem> items = new ArrayList<>();

            item = new ShopItem();
            item.setPrice(800);
            item.setDateOfLastShipment(format.parse("January 31, 2018"));
            items.add(item);

            item = new ShopItem();
            item.setPrice(200);
            item.setDateOfLastShipment(format.parse("February 25, 2018"));
            items.add(item);

            item = new ShopItem();
            item.setPrice(100);
            item.setDateOfLastShipment(format.parse("May 16, 2017"));
            items.add(item);

            item = new ShopItem();
            item.setPrice(500);
            item.setDateOfLastShipment(format.parse("December 21, 2017"));
            items.add(item);

            initializeListView(items);

        } catch (ParseException ex)
        {

        }

    }

    //</editor-fold>

    //<editor-fold desc="internationalization-methods">

    public void selectLocaleUS()
    {
        updateGUI(usLocale);
    }

    public void selectLocalePL()
    {
        updateGUI(plLocale);
    }

    private void updateGUI(Locale currentLocale)
    {
        updateButtons(currentLocale);
        updateLabels(currentLocale);
    }

    private void updateButtons(Locale currentLocale)
    {
        ResourceBundle bundle = ResourceBundle.getBundle("fujiwara.internationalization.Buttons", currentLocale);
        selectDirectoryButton.setText(bundle.getString("select_directory"));
        newShopItemButton.setText(bundle.getString("new"));
        saveShopItemButton.setText(bundle.getString("save"));
        removeShopItemButton.setText(bundle.getString("remove"));
        selectImageButton.setText(bundle.getString("select_image"));
    }

    private void updateLabels(Locale currentLocale)
    {
        ResourceBundle bundle = ResourceBundle.getBundle("fujiwara.internationalization.Labels", currentLocale);
        dateLabel.setText(bundle.getString("date"));
        priceLabel.setText(bundle.getString("price"));
    }

    //</editor-fold>
}