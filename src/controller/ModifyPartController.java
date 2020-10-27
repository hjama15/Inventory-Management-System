package controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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
 * FXML Controller class
 *
 * @author Hamza Jama
 */
/**
 * This class is the controller for the modify part screen
 */
public class ModifyPartController implements Initializable {

    @FXML
    private ToggleGroup InOrOutModifyTG;

    @FXML
    private RadioButton InHouseIdModifyPartRB;

    @FXML
    private RadioButton OutSourcedIdAddPartRB;

    @FXML
    private TextField AutoGenIdModifyPartTF;

    @FXML
    private TextField NameIdModifyPartTF;

    @FXML
    private TextField InvIdModifyPartTF;

    @FXML
    private TextField PriceIdModifyPartTF;

    @FXML
    private TextField MaxIdModifyPartTF;

    @FXML
    private TextField MachineIdModifyPartTF;

    @FXML
    private TextField MinIdModifyPartTF;

    @FXML
    private Button SaveIdModifyPartButton;

    @FXML
    private Button CancelModifyPartButton;

    @FXML
    private Label MachineIdModifyLabel;

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
            MachineIdModifyLabel.setText("Machine ID");
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
            MachineIdModifyLabel.setText("Company Name");
        }
    }

    /**
     * This event will create a new part from the entered values as long as
     * they're inputted correctly that will update the old part. If incorrect
     * values that throw exceptions are entered the user is told to enter the
     * valid values.
     *
     * @param event acts on the save button being clicked
     * @throws IOException in case if the IO operation fails
     */
    @FXML
    void OnActionSaveModifyPart(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(AutoGenIdModifyPartTF.getText());
            String name;
            if (NameIdModifyPartTF.getText().isEmpty()) {
                throw new NumberFormatException();
            } else {
                name = NameIdModifyPartTF.getText();
            }
            int stock = Integer.parseInt(InvIdModifyPartTF.getText());
            double price = Double.parseDouble(PriceIdModifyPartTF.getText());
            int max = Integer.parseInt(MaxIdModifyPartTF.getText());
            int min = Integer.parseInt(MinIdModifyPartTF.getText());
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
            if (InHouseIdModifyPartRB.isSelected()) {
                machineId = Integer.parseInt(MachineIdModifyPartTF.getText());
                Inventory.updatePart(id, new InHouse(id, name, price, stock, min, max, machineId));
            } else {
                companyName = MachineIdModifyPartTF.getText();
                Inventory.updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName));
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
     * This method receives the selected part from the MainMenuController's part
     * pane. It will automatically populate the fields accordingly if it's an
     * instance of an InHouse or Outsourced part with the correct radio button
     * selected.
     *
     * @param part receives the part passed on and checks to see the instance of
     * it
     */
    public void modifyPart(Part part) {
        InHouse inHouse;
        Outsourced outsourced;

        if (part instanceof InHouse) {
            inHouse = (InHouse) part;
            InHouseIdModifyPartRB.setSelected(true);
            AutoGenIdModifyPartTF.setText(String.valueOf(inHouse.getId()));
            NameIdModifyPartTF.setText(inHouse.getName());
            InvIdModifyPartTF.setText(String.valueOf(inHouse.getStock()));
            PriceIdModifyPartTF.setText(String.valueOf(inHouse.getPrice()));
            MaxIdModifyPartTF.setText(String.valueOf(inHouse.getMax()));
            MinIdModifyPartTF.setText(String.valueOf(inHouse.getMin()));
            MachineIdModifyPartTF.setText(String.valueOf(inHouse.getMachineId()));
        } else {
            outsourced = (Outsourced) part;
            OutSourcedIdAddPartRB.setSelected(true);
            AutoGenIdModifyPartTF.setText(String.valueOf(outsourced.getId()));
            NameIdModifyPartTF.setText(outsourced.getName());
            InvIdModifyPartTF.setText(String.valueOf(outsourced.getStock()));
            PriceIdModifyPartTF.setText(String.valueOf(outsourced.getPrice()));
            MaxIdModifyPartTF.setText(String.valueOf(outsourced.getMax()));
            MinIdModifyPartTF.setText(String.valueOf(outsourced.getId()));
            MachineIdModifyPartTF.setText(String.valueOf(outsourced.getCompanyName()));
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AutoGenIdModifyPartTF.setEditable(false);
        AutoGenIdModifyPartTF.setMouseTransparent(true);
        AutoGenIdModifyPartTF.setFocusTraversable(false);
        AutoGenIdModifyPartTF.setDisable(true);
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
