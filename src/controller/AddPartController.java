package controller;

import Model.InHouse;
import Model.Inventory;
import static Model.Inventory.generatePartId;
import Model.Outsourced;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author Hamza Jama
 */
/**
 * This class is the controller for the add part screen
 */
public class AddPartController implements Initializable {

    @FXML
    private ToggleGroup InOrOutAddTG;

    @FXML
    private RadioButton InHouseIdAddPartRB;

    @FXML
    private RadioButton OutSourcedIdAddPartRB;

    @FXML
    private TextField AutoGenIdAddPartTF;

    @FXML
    private TextField NameIdAddPartTF;

    @FXML
    private TextField InvIdAddPartTF;

    @FXML
    private TextField PriceIdAddPartTF;

    @FXML
    private TextField MaxIdAddPartTF;

    @FXML
    private TextField MachineIdAddPartTF;

    @FXML
    private TextField MinIdAddPartTF;

    @FXML
    private Button SaveIdAddPartButton;

    @FXML
    private Button CancelIdAddPartButton;

    @FXML
    private Label MachineIdAddLabel;

    /**
     * This event will check if the machineId radio button is selected and
     * change the label to "Machine ID".
     *
     * @param event checks for radio button if selected
     */
    @FXML
    void OnActionChangeToInHouseAddPartForm(ActionEvent event) {
        RadioButton radiobutton = (RadioButton) event.getSource();
        if (radiobutton.isSelected()) {
            MachineIdAddLabel.setText("Machine ID");
        }
    }

    /**
     * This event will check if the companyNam radio button is selected and
     * change the label to "Company Name".
     *
     * @param event checks for radio button if selected
     */
    @FXML
    void OnActionChangeToOutsourcedAddPartForm(ActionEvent event) {
        RadioButton radiobutton = (RadioButton) event.getSource();
        if (radiobutton.isSelected()) {
            MachineIdAddLabel.setText("Company Name");
        }
    }

    /**
     * This event will create a new part from the entered values as long as
     * they're inputted correctly. If incorrect values that throw exceptions are
     * entered the user is told to enter the valid values.
     *
     * @param event acts on the save button being clicked
     * @throws IOException in case if the IO operation fails
     */
    @FXML
    void OnActionSaveAddPart(ActionEvent event) throws IOException {

        try {
            int id = generatePartId();
            String name;
            if (NameIdAddPartTF.getText().isEmpty()) {
                throw new NumberFormatException();
            } else {
                name = NameIdAddPartTF.getText();
            }
            int stock = Integer.parseInt(InvIdAddPartTF.getText());
            double price = Double.parseDouble(PriceIdAddPartTF.getText());
            int max = Integer.parseInt(MaxIdAddPartTF.getText());
            int min = Integer.parseInt(MinIdAddPartTF.getText());
            int machineId;
            String companyName;

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

            if (InHouseIdAddPartRB.isSelected()) {
                machineId = Integer.parseInt(MachineIdAddPartTF.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            } else {
                companyName = MachineIdAddPartTF.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }

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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AutoGenIdAddPartTF.setEditable(false);
        AutoGenIdAddPartTF.setMouseTransparent(true);
        AutoGenIdAddPartTF.setFocusTraversable(false);
        AutoGenIdAddPartTF.setDisable(true);

        InHouseIdAddPartRB.setSelected(true);
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
