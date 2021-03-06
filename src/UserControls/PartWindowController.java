package UserControls;

import Objects.InHouse;
import Objects.Inventory;
import Objects.Outsourced;
import Objects.Part;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author Oakley Dye
 *
 * FXML Controller for PartWindow.fxml
 */
public class PartWindowController {
    @FXML Label lblAdd;
    @FXML Label lblModify;
    @FXML RadioButton radInHouse;
    @FXML RadioButton radOutsourced;
    @FXML TextField txtID;
    @FXML TextField txtName;
    @FXML TextField txtInv;
    @FXML TextField txtPrice;
    @FXML TextField txtMax;
    @FXML TextField txtMin;
    @FXML TextField txtMachineId;
    @FXML TextField txtCompany;
    @FXML Label lblMachineId;
    @FXML Label lblCompany;
    public Part selectedPart = null;

    /**
     * Method called on PartWindow startup, handles bindings and value assignment
     * @param part part to modify, if isModify = false, part = null
     * @param isModify flag to let the app know if the part window should open in modify or add mode
     */
    public void init(Part part, boolean isModify){
        if (isModify){
            lblModify.setVisible(true);
            lblAdd.setVisible(false);
            this.selectedPart = part;
            /**
             * RUNTIME ERROR: the application was throwing an error when saving a modification to a part.
             * This was corrected by setting the ID field equal to the ID of the part
             * being modified. The error was thrown due to the app trying to parse
             * "Auto Gen - Disabled" to an int value.
             */
            txtID.setText(Integer.toString(part.getId()));
            txtName.setText(part.getName());
            txtInv.setText(Integer.toString(part.getStock()));
            txtPrice.setText(Double.toString(part.getPrice()));
            txtMax.setText(Integer.toString(part.getMax()));
            txtMin.setText(Integer.toString(part.getMin()));
            if (part instanceof InHouse){
                radInHouse.setSelected(true);
                radOutsourced.setSelected(false);
                lblCompany.setVisible(false);
                lblMachineId.setVisible(true);
                txtCompany.setVisible(false);
                txtMachineId.setVisible(true);
                txtMachineId.setText(Integer.toString(((InHouse) part).getMachineId()));
            } else if (part instanceof Outsourced){
                radInHouse.setSelected(false);
                radOutsourced.setSelected(true);
                lblCompany.setVisible(true);
                lblMachineId.setVisible(false);
                txtMachineId.setVisible(false);
                txtCompany.setVisible(true);
                txtCompany.setText(((Outsourced) part).getCompanyName());
            }
        }
    }

    /**
     * Event handler. Changes the type of part added
     */
    public void radInHouse_Click(ActionEvent actionEvent) {
        radOutsourced.setSelected(false);
        radInHouse.setSelected(true);
        lblCompany.setVisible(false);
        lblMachineId.setVisible(true);
        txtMachineId.setVisible(true);
        txtCompany.setVisible(false);
    }

    /**
     * Event handler. Changes the type of part added
     */
    public void radOutsourced_Click(ActionEvent actionEvent) {
        radOutsourced.setSelected(true);
        radInHouse.setSelected(false);
        lblCompany.setVisible(true);
        lblMachineId.setVisible(false);
        txtCompany.setVisible(true);
        txtMachineId.setVisible(false);
    }

    /**
     * Event handler, saves the new part
     */
    public void btnSave_Click(ActionEvent actionEvent) {
        try{
            ObservableList<Part> parts = Inventory.getAllParts();
            int maxId = 0;
            for (Part part : parts){
                if (part.getId() > maxId){
                    maxId = part.getId();
                }
            }

            if (Validate()){
                if (lblAdd.isVisible()){
                    if (radInHouse.isSelected()){
                        InHouse part = new InHouse(
                                maxId + 1,
                                txtName.getText(),
                                Double.parseDouble(txtPrice.getText()),
                                Integer.parseInt(txtInv.getText()),
                                Integer.parseInt(txtMin.getText()),
                                Integer.parseInt(txtMax.getText()),
                                Integer.parseInt(txtMachineId.getText())
                        );
                        Inventory.addPart(part);
                    }
                    else{
                        Outsourced part = new Outsourced(
                                maxId + 1,
                                txtName.getText(),
                                Double.parseDouble(txtPrice.getText()),
                                Integer.parseInt(txtInv.getText()),
                                Integer.parseInt(txtMin.getText()),
                                Integer.parseInt(txtMax.getText()),
                                txtCompany.getText()
                        );
                        Inventory.addPart(part);
                    }
                }
                else{
                    if (radInHouse.isSelected()) {
                        InHouse part = new InHouse(
                                Integer.parseInt(txtID.getText()),
                                txtName.getText(),
                                Double.parseDouble(txtPrice.getText()),
                                Integer.parseInt(txtInv.getText()),
                                Integer.parseInt(txtMin.getText()),
                                Integer.parseInt(txtMax.getText()),
                                Integer.parseInt(txtMachineId.getText())
                        );
                        Inventory.updatePart(Integer.parseInt(txtID.getText()), part);
                    }
                    else{
                        Outsourced part = new Outsourced(
                                Integer.parseInt(txtID.getText()),
                                txtName.getText(),
                                Double.parseDouble(txtPrice.getText()),
                                Integer.parseInt(txtInv.getText()),
                                Integer.parseInt(txtMin.getText()),
                                Integer.parseInt(txtMax.getText()),
                                txtCompany.getText()
                        );
                        Inventory.updatePart(Integer.parseInt(txtID.getText()), part);
                    }
                }
                closeWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Inventory Management System");
                alert.setHeaderText(null);
                alert.setContentText("Error saving the part, inv must be between min and max");
                alert.showAndWait();
            }
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inventory Management System");
            alert.setHeaderText(null);
            alert.setContentText("Error saving the part, please verify that all values are correct.\r\n" + ex.getMessage());
            alert.showAndWait();
        }
    }

    private boolean Validate(){
        try {
            int max = Integer.parseInt(txtMax.getText());
            int min = Integer.parseInt(txtMin.getText());
            int inv = Integer.parseInt(txtInv.getText());
            return max > min && max >= inv && inv >= min;
        } catch (Exception ex){
            return false;
        }
    }

    /**
     * Method to close the current window and return focus to the main window.
     * Called from btnSave_Click and btnCancel_Click
     */
    private void closeWindow() {
        ObservableList<Window> windows = Window.getWindows();
        for (Window window : windows){
            if (window instanceof Stage){
                Stage stage = (Stage) window;
                String title = stage.getTitle();
                if (title.equals("Inventory Management System")){
                    window.requestFocus();
                    break;
                }
            }
        }
        Stage stage = (Stage) radInHouse.getScene().getWindow();
        stage.close();
    }

    /**
     * Event handler, closes the current window
     */
    public void btnCancel_Click(ActionEvent actionEvent) {
        closeWindow();
    }
}
