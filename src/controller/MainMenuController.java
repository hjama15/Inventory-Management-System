package controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Hamza Jama
 */
/**
 * This class is the controller for the main screen when the Inventory
 * Management System program is started.
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField searchIdPartTF;

    @FXML
    private Button addIdPartButton;

    @FXML
    private Button modifyIdPartButton;

    @FXML
    private TextField searchIdProductTF;

    @FXML
    private TableView<Part> PartTableView;

    @FXML
    private TableColumn<Part, Integer> PartIdCol;

    @FXML
    private TableColumn<Part, String> PartNameCol;

    @FXML
    private TableColumn<Part, Integer> PartInvCol;

    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    @FXML
    private Button addIdProductButton;

    @FXML
    private Button modifyIdProductButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private TableView<Product> ProductTableView;

    @FXML
    private TableColumn<Product, Integer> ProductIdCol;

    @FXML
    private TableColumn<Product, String> ProductNameCol;

    @FXML
    private TableColumn<Product, Integer> ProductInvCol;

    @FXML
    private TableColumn<Product, Double> ProductPriceCol;

    @FXML
    private Button deleteProductButton;

    /**
     * This event is to get the key entered into the search bar. This will store
     * the key in the string variable which is then inputted into the filter
     * method along with "Part" that will filter the parts listed in the pane.
     *
     * @param event gets text from the input field
     */
    @FXML
    void OnActionKeySearchedPartTF(ActionEvent event) {

        String searchKey = searchIdPartTF.getText();

        filter(searchKey, "Part");

    }

    /**
     * This method will filter the part or product menu for the searched value.
     * It will check the second parameter for the inputted value that matches,
     * and then it will initially empty the filtered list, check if it's an Id
     * or Name to search by then loop the original list and add to filtered list
     * for matched items and show the filtered panes at the end.
     *
     * @param searchKey what's being searched for
     * @param PartOrProduct used to meet condition in the method
     */
    public void filter(String searchKey, String PartOrProduct) {

        if (PartOrProduct.equals("Part")) {

            if (!(Inventory.getAllFilteredParts().isEmpty())) {
                Inventory.getAllFilteredParts().clear();
            }

            int id = 0;
            Boolean isInteger = true;

            try {
                id = Integer.parseInt(searchIdPartTF.getText());
            } catch (NumberFormatException e) {
                isInteger = false;
            }

            if (isInteger) {
                for (Part part : Inventory.getAllParts()) {
                    if (part.getId() == id) {
                        Inventory.addFilteredPart(part);
                    }
                }
            }

            for (Part part : Inventory.getAllParts()) {
                if (part.getName().contains(searchKey)) {
                    Inventory.addFilteredPart(part);
                }
            }

            PartTableView.setItems(Inventory.getAllFilteredParts());
            PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            PartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        } else {
            if (!(Inventory.getAllFilteredProducts().isEmpty())) {
                Inventory.getAllFilteredProducts().clear();
            }

            int id = 0;
            Boolean isInteger = true;

            try {
                id = Integer.parseInt(searchIdProductTF.getText());
            } catch (NumberFormatException e) {
                isInteger = false;
            }

            if (isInteger) {
                for (Product product : Inventory.getAllProducts()) {
                    if (product.getId() == id) {
                        Inventory.addFilteredProduct(product);
                    }
                }
            }

            for (Product product : Inventory.getAllProducts()) {
                if (product.getName().contains(searchKey)) {
                    Inventory.addFilteredProduct(product);
                }
            }

            ProductTableView.setItems(Inventory.getAllFilteredProducts());
            ProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            ProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            ProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /**
     * This event is used to open the add screen for parts.
     *
     * @param event acts on the add button being clicked
     * @throws IOException in case if the IO operation fails
     */
    @FXML
    void OnActionOpenAddPartMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * A big issue I ran into that's in the event below but also other events
     * involving opening up the part delete and product delete and modify events
     * is the passing of null selections to to the program causing it to crash.
     * I had trouble figuring out how to fix it at first until I realized
     * that the action of just clicking the buttons was passing in a null so I
     * added a conditional to not move forward if a null is passed which
     * fixed the issue.
     */
    
    /**
     * A compatible feature suitable to the application that would extend
     * functionality would be to have a warning sign or message any products
     * with associated parts that have those parts deleted, or fallen below a
     * certain level or in the real world ran out. It would serve as a warning
     * to the client that the parts need to be restocked.
     */
    
    /**
     * This event will open modify screen for the part selected in the pane. It
     * will send the selected part to the modify controller to have it
     * automatically filled in with its contents.
     *
     * @param event acts on the modify button being clicked
     * @throws IOException in case if the IO operation fails
     */
    @FXML
    void OnActionOpenModifyPartMenu(ActionEvent event) throws IOException {

        if (PartTableView.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPartController controller = loader.getController();
            controller.modifyPart(PartTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * This event delete the selected part in the pane. A confirmation window
     * will pop up to make sure user is sure they want to delete.
     *
     * @param event acts on the delete button selected
     */
    @FXML
    void OnActionDeleteSelectedPart(ActionEvent event) {

        if (PartTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete selected Part, do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(PartTableView.getSelectionModel().getSelectedItem());
            }
        }

    }

    /**
     * This even is to get the key entered into the search bar. This will store
     * the key in the string variable which is then inputted into the filter
     * method along with "Product" that will filter the products listed in the
     * pane.
     *
     * @param event gets text from the input field
     */
    @FXML
    void OnActionKeySearchedProduct(ActionEvent event) {
        String searchKey = searchIdProductTF.getText();

        filter(searchKey, "Product");
    }

    /**
     * This event is used to open the add screen for products.
     *
     * @param event acts on the add button being clicked
     * @throws IOException in case if the IO operation fails
     */
    @FXML
    void OnActionOpenAddProductMenu(ActionEvent event) throws IOException {

        Inventory.getAllTempAssociatedParts().clear();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This event will open modify screen for the product selected in the pane.
     * It will send the selected product to the modify controller to have it
     * automatically filled in with its contents.
     *
     * @param event acts on the modify button being clicked
     * @throws IOException in case if the IO operation fails
     */
    @FXML
    void OnActionOpenModifyProductMenu(ActionEvent event) throws IOException {

        if (ProductTableView.getSelectionModel().getSelectedItem() != null) {
            Inventory.getAllTempAssociatedParts().clear();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController controller = loader.getController();
            controller.modifyProduct(ProductTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * This event delete the selected product in the pane. A confirmation window
     * will pop up to make sure user is sure they want to delete.
     *
     * @param event acts on the delete button selected
     */
    @FXML
    void OnActionDeleteSelectedProduct(ActionEvent event) {

        if (ProductTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete selected Product, do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (ProductTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(ProductTableView.getSelectionModel().getSelectedItem());

                } else {
                    Alert newAlert = new Alert(Alert.AlertType.ERROR);
                    newAlert.setTitle("Error Dialog");
                    newAlert.setContentText("Unable to delete Product with associated parts");
                    newAlert.showAndWait();
                }
            }
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        PartTableView.setItems(Inventory.getAllParts());
        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductTableView.setItems(Inventory.getAllProducts());
        ProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
