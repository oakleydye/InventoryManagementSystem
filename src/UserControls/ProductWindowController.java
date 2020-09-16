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
            txtName.setText(selectedProduct.getName());
            txtInv.setText(Integer.toString(selectedProduct.getStock()));
            txtPrice.setText(Double.toString(selectedProduct.getPrice()));
            txtMax.setText(Integer.toString(selectedProduct.getMax()));
            txtMin.setText(Integer.toString(selectedProduct.getMin()));
            grdAssociatedParts.setItems(selectedProduct.getAllAssociatedParts());
        }
    }

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

    public void btnAddAssociatedPart_Click(ActionEvent actionEvent) {
        Part selectedPart = grdAllParts.getSelectionModel().getSelectedItem();
        this.product.addAssociatedPart(selectedPart);
    }

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
                Inventory.addProduct(newProduct);
            } else{
                Product newProduct = new Product(
                        Integer.parseInt(txtId.getText()),
                        txtName.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        Integer.parseInt(txtInv.getText()),
                        Integer.parseInt(txtMin.getText()),
                        Integer.parseInt(txtMax.getText())
                );
                Inventory.updateProduct(Integer.parseInt(txtId.getText()), newProduct);
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

    public void btnCancel_Click(ActionEvent actionEvent) {
        closeWindow();
    }

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
