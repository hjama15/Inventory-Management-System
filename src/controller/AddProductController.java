package controller;

import Model.Inventory;
import static Model.Inventory.generateProductId;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Jama
 */
/**
 * This class is the controller for the add product screen
 */
public class AddProductController implements Initializable {

    @FXML
    private TextField AutoGenIdAddProductTF;

    @FXML
    private TextField NameIdAddProductTF;

    @FXML
    private TextField InvIdAddProductTF;

    @FXML
    private TextField PriceIdAddProductTF;

    @FXML
    private TextField MaxIdAddProductTF;

    @FXML
    private TextField MinIdAddProductTF;

    @FXML
    private TextField searchedIdPartTF;

    @FXML
    private TableView<Part> PartTableViewUpper;

    @FXML
    private TableColumn<Part, Integer> PartIdColUpper;

    @FXML
    private TableColumn<Part, String> PartNameColUpper;

    @FXML
    private TableColumn<Part, Integer> PartInvColUpper;

    @FXML
    private TableColumn<Part, Double> PartPriceColUpper;

    @FXML
    private Button addAssociatedPartButton;

    @FXML
    private TableView<Part> PartTableViewLower;

    @FXML
    private TableColumn<Part, Integer> PartIdColLower;

    @FXML
    private TableColumn<Part, String> PartNameColLower;

    @FXML
    private TableColumn<Part, Integer> PartInvColLower;

    @FXML
    private TableColumn<Part, Double> PartPriceColLower;

    @FXML
    private Button removeAssociatedPartButton;

    @FXML
    private Button SaveIdAddProductButton;

    @FXML
    private Button CancelAddProductButton;

    /**
     * This event is to get the key entered into the search bar. This will store
     * the key in the string variable which is then inputted into the filter
     * method along with "Part" that will filter the parts listed in the pane.
     *
     * @param event gets text from the input field
     */
    @FXML
    void OnActionKeySearchedPartTF(ActionEvent event) {

        String searchKey = searchedIdPartTF.getText();

        filter(searchKey);
    }

    /**
     * This method will filter the part pane for the searched value. It will
     * initially empty the filtered list, check if it's an Id or Name to search
     * by then loop the original list and add to filtered list for matched items
     * and show the filtered panes at the end.
     *
     * @param searchKey what's being searched for
     */
    public void filter(String searchKey) {

        if (!(Inventory.getAllFilteredParts().isEmpty())) {
            Inventory.getAllFilteredParts().clear();
        }

        int id = 0;
        Boolean isInteger = true;

        try {
            id = Integer.parseInt(searchedIdPartTF.getText());
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

        PartTableViewUpper.setItems(Inventory.getAllFilteredParts());
        PartIdColUpper.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColUpper.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvColUpper.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColUpper.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This event will add the associated part selected in the upper pane to a
     * temporary associated list created to hold it until the product is created
     * and saved. It will show the added associated list in the lower pane.
     *
     * @param event acts on the add button being clicked
     */
    @FXML
    void OnActionAddAssociatedPart(ActionEvent event) {

        if (PartTableViewUpper.getSelectionModel().getSelectedItem() != null) {
            Inventory.addTempAssociatedParts(PartTableViewUpper.getSelectionModel().getSelectedItem());
        }

        PartTableViewLower.setItems(Inventory.getAllTempAssociatedParts());
        PartIdColLower.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColLower.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvColLower.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColLower.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * This even will take user back to the main menu screen. It will open a
     * confirmation window to confirm user really want to go back to the main
     * menu and cancel adding a new part.
     *
     * @param event acts on the cancel button being clicked
     * @throws IOException in case if the IO operation fails
     */
    @FXML
    void OnActionDisplayMainMenu(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will take you back to the main screen, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /**
     * This event will remove the associated part selected in the lower pane. It
     * will call the a delete method of the temp associated list and the removed
     * part will no longer display in the lower pane.
     *
     * @param event acts on the remove associated part button being clicked
     */
    @FXML
    void OnActionRemoveAssociatedPart(ActionEvent event) {

        Inventory.deleteTempAssociatedPart(PartTableViewLower.getSelectionModel().getSelectedItem());

        PartTableViewLower.setItems(Inventory.getAllTempAssociatedParts());
        PartIdColLower.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColLower.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvColLower.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColLower.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * This event will create a new product from the entered values and any
     * associated parts as long as they're inputted correctly. If incorrect
     * values that throw exceptions are entered the user is told to enter the
     * valid values.
     *
     * @param event acts on the save button being clicked
     * @throws IOException in case if the IO operation fails
     */
    @FXML
    void OnActionSaveAddProduct(ActionEvent event) throws IOException {

        try {
            int id = generateProductId();
            String name;
            if (NameIdAddProductTF.getText().isEmpty()) {
                throw new NumberFormatException();
            } else {
                name = NameIdAddProductTF.getText();
            }
            int stock = Integer.parseInt(InvIdAddProductTF.getText());
            double price = Double.parseDouble(PriceIdAddProductTF.getText());
            int max = Integer.parseInt(MaxIdAddProductTF.getText());
            int min = Integer.parseInt(MinIdAddProductTF.getText());

            if (!(stock > min && stock < max)) {
                InvOutOfBoundsException exception;
                exception = new InvOutOfBoundsException("Inventory is less than minumum or more than maximum");
                throw exception;

            }

            if (max < min) {
                MaxLessThanMinException exception;
                exception = new MaxLessThanMinException("Maximum is less than minimum");
                throw exception;
            }

            Product product;
            product = new Product(id, name, price, stock, min, max);

            if (Inventory.getAllTempAssociatedParts() != null) {
                for (int i = 0; i < Inventory.getAllTempAssociatedParts().size(); i++) {
                    product.addAssociatedPart(Inventory.getAllTempAssociatedParts().get(i));
                }
            }

            Inventory.addProduct(product);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid value for each Text Field");
            alert.showAndWait();

        } catch (MaxLessThanMinException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Max cannot be less than min");
            alert.showAndWait();
        } catch (InvOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Inventory cannot be less than minumum or greater than maximum");
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        AutoGenIdAddProductTF.setEditable(false);
        AutoGenIdAddProductTF.setMouseTransparent(true);
        AutoGenIdAddProductTF.setFocusTraversable(false);
        AutoGenIdAddProductTF.setDisable(true);

        PartTableViewUpper.setItems(Inventory.getAllParts());
        PartIdColUpper.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColUpper.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvColUpper.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColUpper.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * customized exception to throw if Inv is greater than max or less than
     * min.
     */
    class InvOutOfBoundsException extends Exception {

        InvOutOfBoundsException(String str) {
            super(str);
        }
    }

    /**
     * customized exception to throw if max is less than min.
     */
    class MaxLessThanMinException extends Exception {

        MaxLessThanMinException(String str) {
            super(str);
        }
    }
}
