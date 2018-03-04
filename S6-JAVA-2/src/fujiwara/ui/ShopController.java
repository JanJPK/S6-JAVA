package fujiwara.ui;

import fujiwara.model.ShopItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShopController
{
    //<editor-fold desc="variables">

    private static final ObservableList<ShopItem> shopItems = FXCollections.observableArrayList();
    private static Locale usLocale = new Locale("en", "US");
    private static Locale plLocale = new Locale("pl", "PL");
    private static Locale currentLocale;
    private static DateFormat dateFormatter;
    private static NumberFormat priceFormatter;
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
    public Image detailViewImage;
    public ImageView detailViewImageView;
    public TextField nameTextField;
    private File directory;
    private ShopItem selectedItem;

    //</editor-fold>

    //<editor-fold desc="initialize">

    public void initialize()
    {
        setLocaleUS();
    }

    //</editor-fold>

    //<editor-fold desc="detail-view-methods">

    private void initializeDetailView()
    {
        dateTextField.setText(dateFormatter.format(selectedItem.getDateOfLastShipment()));
        priceTextField.setText(priceFormatter.format(selectedItem.getPrice()));
        nameTextField.setText(selectedItem.getName());

        if (selectedItem.getImagePath() == null)
        {
            detailViewImageView.setImage(new Image(Main.class.getResourceAsStream("/shop_default_image.png")));
        } else
        {
            String path = "file:" + selectedItem.getImagePath();
            detailViewImage = new Image(path);
            detailViewImageView.setImage(detailViewImage);
        }

    }

    public void createShopItem()
    {
        selectedItem = new ShopItem();
        selectedItem.setDateOfLastShipment(new Date());
        selectedItem.setPrice(0);
        selectedItem.setName("Product");
        initializeDetailView();
    }

    public void selectShopItemImage()
    {

    }

    public void saveShopItem()
    {
        try
        {
            selectedItem.setDateOfLastShipment(dateFormatter.parse(dateTextField.getText()));
        } catch (ParseException ex)
        {
            showDialogError("parse_header", "parse_date");
            return;
        }

        try
        {
            selectedItem.setPrice((long) priceFormatter.parse(priceTextField.getText()));
        } catch (ParseException ex)
        {
            showDialogError("parse_header", "parse_price");
            return;
        }

        if (nameTextField.getText().equals(""))
        {
            showDialogError("parse_header", "parse_name");
            return;
        }
        selectedItem.setName(nameTextField.getText());

        if (!shopItems.contains(selectedItem))
        {
            shopItems.add(selectedItem);
        }

        marshal(selectedItem, selectedItem.getName());
        shopItems.remove(selectedItem);
        shopItems.add(selectedItem);
        shopItemListView.setItems(shopItems);
        initializeDetailView();
    }

    public void removeShopItem()
    {
        File file = new File(directory + "\\" + selectedItem.getName() + ".xml");
        if (file.exists())
        {
            try
            {
                Files.delete(file.toPath());
            } catch (IOException ignored)
            {

            }

        }
        shopItems.remove(selectedItem);
        if(shopItems.isEmpty())
        {
            createShopItem();
        }
        else
        {
            selectedItem = shopItems.get(0);
            initializeDetailView();
        }
    }

    //</editor-fold>

    //<editor-fold desc="list-view-methods">

    private void initializeListView(List<ShopItem> items)
    {
        shopItems.addAll(items);
        //shopItemListView.getItems().clear();
        shopItemListView.setItems(shopItems);
    }

    public void openDetailView()
    {
        //int index = shopItemListView.getSelectionModel().getSelectedIndex();
        //selectedItem = shopItems.get(index);
        selectedItem = (ShopItem) shopItemListView.getSelectionModel().getSelectedItem();
        initializeDetailView();
    }

    public void selectDirectory()
    {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choose the directory containing objects to load");
        directory = dc.showDialog(mainGrid.getScene().getWindow());
        if (directory == null)
        {
            showDialogError("load_header", "load_null");
            return;
        }

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
            showDialogError("load_header",
                    "load_empty");
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
            PrintWriter out;
            if (directory == null)
            {
                out = new PrintWriter(filename + ".xml");
            } else
            {
                out = new PrintWriter(directory.getAbsolutePath() + "\\" + filename + ".xml");
            }
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
        ResourceBundle bundle = ResourceBundle.getBundle("fujiwara.internationalization.Errors", currentLocale);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(bundle.getString(header));
        alert.setContentText(bundle.getString(content));
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

    //<editor-fold desc="debug-methods">

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
            item.setName("4A-GE CGS");
            item.setImagePath("F:/4age/4age_cam_gear_set.jpeg");
            items.add(item);

            item = new ShopItem();
            item.setPrice(200);
            item.setDateOfLastShipment(format.parse("February 25, 2018"));
            item.setName("4A-GE Head");
            item.setImagePath("F:/4age/4age_head.jpeg");
            items.add(item);

            item = new ShopItem();
            item.setPrice(100);
            item.setDateOfLastShipment(format.parse("May 16, 2017"));
            item.setName("4A-GE Stroker");
            item.setImagePath("F:/4age/4age_stroker_kit.png");
            items.add(item);

            item = new ShopItem();
            item.setPrice(500);
            item.setDateOfLastShipment(format.parse("December 21, 2017"));
            item.setName("4A-GE ECU");
            item.setImagePath("F:/4age/4age_ecu.jpeg");
            items.add(item);

            initializeListView(items);

        } catch (ParseException ex)
        {

        }

    }

    public void testErrorDialog()
    {
        showDialogError("parse_header", "parse_price");
        showDialogError("parse_header", "parse_date");
        showDialogError("parse_header", "parse_name");
    }

    //</editor-fold>

    //<editor-fold desc="internationalization-methods">

    public void setLocaleUS()
    {
        currentLocale = usLocale;
        updateGUI();
    }

    public void setLocalePL()
    {
        currentLocale = plLocale;
        updateGUI();
    }

    private void updateGUI()
    {
        adjustFormatters();
        updateButtons();
        updateLabels();
        if (selectedItem != null)
        {
            initializeDetailView();
        }
    }

    private void updateButtons()
    {
        ResourceBundle bundle = ResourceBundle.getBundle("fujiwara.internationalization.Buttons", currentLocale);
        selectDirectoryButton.setText(bundle.getString("select_directory"));
        newShopItemButton.setText(bundle.getString("new"));
        saveShopItemButton.setText(bundle.getString("save"));
        removeShopItemButton.setText(bundle.getString("remove"));
        selectImageButton.setText(bundle.getString("select_image"));
    }

    private void updateLabels()
    {
        ResourceBundle bundle = ResourceBundle.getBundle("fujiwara.internationalization.Labels", currentLocale);
        dateLabel.setText(bundle.getString("date"));
        priceLabel.setText(bundle.getString("price"));
    }

    private void adjustFormatters()
    {
        Currency currency = Currency.getInstance(currentLocale);
        priceFormatter = NumberFormat.getCurrencyInstance(currentLocale);
        dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, currentLocale);
    }

    //</editor-fold>
}