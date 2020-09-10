package UserControls;

import Objects.Inventory;
import Objects.Part;
import Objects.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ProductWindowController {
    @FXML Label lblAdd;
    @FXML Label lblModify;
    @FXML TextField txtName;
    @FXML TextField txtInv;
    @FXML TextField txtPrice;
    @FXML TextField txtMax;
    @FXML TextField txtMin;
    @FXML TableView<Part> grdAllParts;
    @FXML TableView<Part> grdAssociatedParts;
    public Inventory inventory = null;

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

    public void btnRemovePart_Click(ActionEvent actionEvent) {
        Part selectedPart = grdAssociatedParts.getSelectionModel().getSelectedItem();
        this.inventory.deletePart(selectedPart);
        //grdAssociatedParts.getItems().remove(selectedPart);
    }

    public void btnAddAssociatedPart_Click(ActionEvent actionEvent) {
        Part selectedPart = grdAllParts.getSelectionModel().getSelectedItem();
        grdAssociatedParts.getItems().add(selectedPart);
    }

    public void btnSave_Click(ActionEvent actionEvent) {
        ObservableList<Product> products = this.inventory.getAllProducts();
        int maxId = 0;
        for (Product product : products){
            if (product.getId() > maxId){
                maxId = product.getId();
            }
        }

        Product newProduct = new Product(
                maxId + 1,
                txtName.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtInv.getText()),
                Integer.parseInt(txtMin.getText()),
                Integer.parseInt(txtMax.getText())
        );
        this.inventory.addProduct(newProduct);
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
        Stage stage = (Stage) txtMin.getScene().getWindow();
        stage.close();
    }
}
