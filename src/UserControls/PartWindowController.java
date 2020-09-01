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

public class PartWindowController {
    @FXML RadioButton radInHouse;
    @FXML RadioButton radOutsourced;
    @FXML TextField txtName;
    @FXML TextField txtInv;
    @FXML TextField txtPrice;
    @FXML TextField txtMax;
    @FXML TextField txtMin;
    @FXML TextField txtMachineId;
    @FXML TextField txtCompany;
    @FXML Label lblMachineId;
    @FXML Label lblCompany;
    public Inventory inventory = null;
    public Part selectedPart = null;

    public void init(Inventory inventory, Part part, boolean isModify){
        this.inventory = inventory;
        if (isModify){
            this.selectedPart = part;
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

    public void radInHouse_Click(ActionEvent actionEvent) {
        radOutsourced.setSelected(false);
        radInHouse.setSelected(true);
        lblCompany.setVisible(false);
        lblMachineId.setVisible(true);
        txtMachineId.setVisible(true);
        txtCompany.setVisible(false);
    }

    public void radOutsourced_Click(ActionEvent actionEvent) {
        radOutsourced.setSelected(true);
        radInHouse.setSelected(false);
        lblCompany.setVisible(true);
        lblMachineId.setVisible(false);
        txtCompany.setVisible(true);
        txtMachineId.setVisible(false);
    }

    public void btnSave_Click(ActionEvent actionEvent) {
        try{
            if (radInHouse.isSelected()){
                InHouse part = new InHouse(
                        0,
                        txtName.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        Integer.parseInt(txtInv.getText()),
                        Integer.parseInt(txtMin.getText()),
                        Integer.parseInt(txtMax.getText()),
                        Integer.parseInt(txtMachineId.getText())
                );
                //todo: make sure this actually updates in the grid
                this.inventory.addPart(part);
            }
            else{
                Outsourced part = new Outsourced(
                        0,
                        txtName.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        Integer.parseInt(txtInv.getText()),
                        Integer.parseInt(txtMin.getText()),
                        Integer.parseInt(txtMax.getText()),
                        txtCompany.getText()
                );
                this.inventory.addPart(part);
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

    public void btnCancel_Click(ActionEvent actionEvent) {
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
}
