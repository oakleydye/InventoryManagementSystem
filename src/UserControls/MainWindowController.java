package UserControls;

import Objects.Inventory;
import Objects.Part;
import Objects.Product;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Oakley Dye
 *
 * FXML controller for MainWindow.fxml
 */
public class MainWindowController {
    @FXML TableView<Part> grdParts = new TableView<>();
    @FXML TableView<Product> grdProducts = new TableView<>();
    @FXML TextField txtSearchPart;
    @FXML TextField txtSearchProduct;

    /**
     * method called on startup of MainWindow. Handles bindings for TableViews
     */
    public void init(){
        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.getAllParts(), p -> true);
        txtSearchPart.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredParts.setPredicate( part -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Integer.toString(part.getId()).contains(lowerCaseFilter)){
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Inventory Management System");
                    alert.setHeaderText(null);
                    alert.setContentText("No part found for search criteria");
                    alert.showAndWait();
                    return false;
                }
            });
        });
        grdParts.setItems(filteredParts);

        FilteredList<Product> filteredProducts = new FilteredList<>(Inventory.getAllProducts(), p -> true);
        txtSearchProduct.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProducts.setPredicate( product -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (product.getName().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                } else if (Integer.toString(product.getId()).contains(lowerCaseFilter)){
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Inventory Management System");
                    alert.setHeaderText(null);
                    alert.setContentText("No part found for search criteria");
                    alert.showAndWait();
                    return false;
                }
            });
        });
        grdProducts.setItems(filteredProducts);
    }

    /**
     * Event handler, deletes a selected part from inventory
     */
    public void btnDeletePart_Click(ActionEvent actionEvent) {
        Part selectedPart = grdParts.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            if (!Inventory.deletePart(selectedPart)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Inventory Management System");
                alert.setHeaderText(null);
                alert.setContentText("Error removing part");
                alert.showAndWait();
            }
        }
    }

    /**
     * Event handler, modifies a part selected in the TableView
     */
    public void btnModPart_Click(ActionEvent actionEvent) throws Exception {
        Part selectedPart = grdParts.getSelectionModel().getSelectedItem();
        showPartWindow(true, selectedPart);
    }

    /**
     * Event handler, allows user to add new parts to the inventory. Shows the AddPart window
     */
    public void btnAddPart_Click(ActionEvent actionEvent) throws Exception {
        showPartWindow(false, null);
    }

    /**
     * Opens the part window, called from btnAddPart_Click and btnModPart_Click
     * @param isModify flag to let the part window know if it should open in add mode or modify mode
     * @param selectedPart the part selected in the grid. If isModify = false, then selectedPart = null
     * @throws Exception
     */
    private void showPartWindow(boolean isModify, Part selectedPart) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PartWindow.fxml"));
        Parent scene = fxmlLoader.load();
        PartWindowController controller = fxmlLoader.getController();
        controller.init(selectedPart, isModify);
        Stage stage = new Stage();
        stage.setScene(new Scene(scene, 800, 600));
        stage.show();
    }

    /**
     * Event handler, deletes selected product
     */
    public void btnDeleteProduct_Click(ActionEvent actionEvent) {
        Product selectedProduct = grdProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (!Inventory.deleteProduct(selectedProduct)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Inventory Management System");
                alert.setHeaderText(null);
                alert.setContentText("Error removing product");
                alert.showAndWait();
            }
        }
    }

    /**
     * Event handler, allows user to modify the selected product
     * @throws Exception
     */
    public void btnModProduct_Click(ActionEvent actionEvent) throws Exception {
        Product selectedProduct = grdProducts.getSelectionModel().getSelectedItem();
        showProductWindow(true, selectedProduct);
    }

    /**
     * Event handler, allows user to add new products to the inventory
     * @throws Exception
     */
    public void btnAddProduct_Click(ActionEvent actionEvent) throws Exception {
        showProductWindow(false, null);
    }

    /**
     * Opens the product window, called from btnAddProduct_Click and btnModProduct_Click
     * @param isModify flag to let the product window know which mode to open in
     * @param selectedProduct product selected in TableView. If isModify = false, selectedProduct = null
     * @throws Exception
     */
    private void showProductWindow(boolean isModify, Product selectedProduct) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductWindow.fxml"));
        Parent scene = fxmlLoader.load();
        ProductWindowController controller = fxmlLoader.getController();
        controller.init(selectedProduct, isModify);
        Stage stage = new Stage();
        stage.setScene(new Scene(scene, 800, 600));
        stage.show();
    }

    /**
     * Closes the application
     */
    public void btnExit_Click(ActionEvent actionEvent) {
        Platform.exit();
    }
}
