package UserControls;

import Objects.Inventory;
import Objects.Part;
import Objects.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MainWindowController {
    @FXML TableView<Part> grdParts;
    @FXML TableView<Product> grdProducts;
    private final ObservableList<Part> parts = FXCollections.observableArrayList();
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    public Inventory inventory;

    /*public MainWindowController(){
        this.inventory = new Inventory();
        this.parts.setAll(this.inventory.getAllParts());
        this.products.setAll(this.inventory.getAllProducts());
    }*/

    public void btnDeletePart_Click(ActionEvent actionEvent) {
        Part selectedPart = grdParts.getSelectionModel().getSelectedItem();
        this.inventory.deletePart(selectedPart);
    }

    public void btnModPart_Click(ActionEvent actionEvent) throws Exception {
        Part selectedPart = grdParts.getSelectionModel().getSelectedItem();
        showPartWindow(true, selectedPart);
    }

    public void btnAddPart_Click(ActionEvent actionEvent) throws Exception {
        showPartWindow(false, null);
    }

    private void showPartWindow(boolean isModify, Part selectedPart) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PartWindow.fxml"));
        Parent scene = fxmlLoader.load();
        PartWindowController controller = fxmlLoader.getController();
        controller.init(this.inventory, selectedPart, isModify);
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void btnDeleteProduct_Click(ActionEvent actionEvent) {
        Product selectedProduct = grdProducts.getSelectionModel().getSelectedItem();
        this.inventory.deleteProduct(selectedProduct);
    }

    public void btnModProduct_Click(ActionEvent actionEvent) throws Exception {
        Product selectedProduct = grdProducts.getSelectionModel().getSelectedItem();
        showProductWindow(true, selectedProduct);
    }

    public void btnAddProduct_Click(ActionEvent actionEvent) throws Exception {
        showProductWindow(false, null);
    }

    private void showProductWindow(boolean isModify, Product selectedProduct) throws Exception {
        Parent scene = FXMLLoader.load(getClass().getResource("ProductWindow.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void btnExit_Click(ActionEvent actionEvent) {
        Platform.exit();
    }
}
