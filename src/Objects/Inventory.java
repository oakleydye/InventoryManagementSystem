package Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public void addPart(Part newPart){
        allParts.add(newPart);
    }

    public void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public Part lookupPart(int partId){
        for (Part part : allParts){
            if (part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    public Product lookupProduct(int productId){
        for (Product product : allProducts){
            if (product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    public void updatePart(int index, Part selectedPart){

    }

    public void updateProduct(int index, Product newProduct){

    }

    public boolean deletePart(Part selectedPart){
        for (Part part : allParts){
            if (part == selectedPart){
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(Product selectedProduct){
        for (Product product : allProducts){
            if (product == selectedProduct){
                allProducts.remove(product);
                return true;
            }
        }
        return false;
    }

    public ObservableList<Part> getAllParts(){
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
