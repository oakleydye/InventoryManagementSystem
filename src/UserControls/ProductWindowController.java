package UserControls;

import Objects.Inventory;
import Objects.Part;
import Objects.Product;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author Oakley Dye
 *
 * FXML controller for ProductWindow.fxml
 */
public class ProductWindowController {
    @FXML Label lblAdd;
    @FXML Label lblModify;
    @FXML TextField txtId;
    @FXML TextField txtName;
    @FXML TextField txtInv;
    @FXML TextField txtPrice;
    @FXML TextField txtMax;
    @FXML TextField txtMin;
    @FXML TextField txtSearch;
    @FXML TableView<Part> grdAllParts = new TableView<>();
    @FXML TableView<Part> grdAssociatedParts = new TableView<>();
    public Product product = null;

    /**
     * Startup method, handles bindings and value assignment
     * @param selectedProduct product we are modifying, null if isModify = false
     * @param isModify flag to let the app know to open in modify or add mode
     */
    public void init(Product selectedProduct, boolean isModify){
        this.product = selectedProduct;

        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.getAllParts());
        txtSearch.textProperty().addListener((observable, oldItem, newItem) -> {
            filteredParts.setPredicate( part -> {
                if (newItem == null || newItem.isEmpty()){
                    return true;
                }

                String lowerCase = newItem.toLowerCase();
                if (part.getName().toLowerCase().contains(lowerCase)){
                    return true;
                } else return Integer.toString(part.getId()).contains(lowerCase);
            });
        });
        grdAllParts.setItems(filteredParts);

        if (isModify){
            txtId.setText(Integer.toString(selectedProduct.getId()));
            txtName.setText(selectedProduct.getName());
            txtInv.setText(Integer.toString(selectedProduct.getStock()));
            txtPrice.setText(Double.toString(selectedProduct.getPrice()));
            txtMax.setText(Integer.toString(selectedProduct.getMax()));
            txtMin.setText(Integer.toString(selectedProduct.getMin()));
        } else {
            //dummy product to prevent errors
            this.product = new Product(0, "", 0, 0, 0, 0);
        }
        grdAssociatedParts.setItems(this.product.getAllAssociatedParts());
    }

    /**
     * Event handler, removes a selected part from the product's associated parts.
     * @param actionEvent
     */
    public void btnRemovePart_Click(ActionEvent actionEvent) {
        Part selectedPart = grdAssociatedParts.getSelectionModel().getSelectedItem();
        if (!this.product.deleteAssociatedPart(selectedPart)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inventory Management System");
            alert.setHeaderText(null);
            alert.setContentText("Error removing part");
            alert.showAndWait();
        }
    }

    /**
     * Event handler, adds a part to the product's list of associated parts
     * @param actionEvent
     */
    public void btnAddAssociatedPart_Click(ActionEvent actionEvent) {
        try{
            Part selectedPart = grdAllParts.getSelectionModel().getSelectedItem();
            this.product.addAssociatedPart(selectedPart);
        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inventory Management System");
            alert.setHeaderText(null);
            alert.setContentText("Error saving the product, please verify that all values are correct.\r\n" + ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Event handler, saves the new product or updates the existing one.
     * @param actionEvent
     */
    public void btnSave_Click(ActionEvent actionEvent) {
        try{
            ObservableList<Product> products = Inventory.getAllProducts();
            int maxId = 0;
            for (Product product : products){
                if (product.getId() > maxId){
                    maxId = product.getId();
                }
            }

            if (lblAdd.isVisible()){
                Product newProduct = new Product(
                        maxId + 1,
                        txtName.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        Integer.parseInt(txtInv.getText()),
                        Integer.parseInt(txtMin.getText()),
                        Integer.parseInt(txtMax.getText())
                );

                for (Part part : this.product.getAllAssociatedParts()){
                    newProduct.addAssociatedPart(part);
                }

                Inventory.addProduct(newProduct);
            } else {

                this.product.setId(Integer.parseInt(txtId.getText()));
                this.product.setName(txtName.getText());
                this.product.setPrice(Double.parseDouble(txtPrice.getText()));
                this.product.setStock(Integer.parseInt(txtInv.getText()));
                this.product.setMin(Integer.parseInt(txtMin.getText()));
                this.product.setMax(Integer.parseInt(txtMax.getText()));

                Inventory.updateProduct(Integer.parseInt(txtId.getText()), this.product);
            }
            closeWindow();
        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inventory Management System");
            alert.setHeaderText(null);
            alert.setContentText("Error saving the product, please verify that all values are correct.\r\n" + ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Event handler, closes the current window
     * @param actionEvent
     */
    public void btnCancel_Click(ActionEvent actionEvent) {
        closeWindow();
    }

    /**
     * Method to close the current window and return focus to the main window.
     */
    private void closeWindow(){
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
