package Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId){
        for (Part part : allParts){
            if (part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId){
        for (Product product : allProducts){
            if (product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for (Part part : allParts){
            if (part.getName().equals(partName)){
                parts.add(part);
            }
        }
        return parts;
    }

    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> products = FXCollections.observableArrayList();
        for (Product product : allProducts){
            if (product.getName().equals(productName)){
                products.add(product);
            }
        }
        return products;
    }

    public static void updatePart(int index, Part selectedPart){
        for (Part part : allParts){
            if (part.getId() == index){
                part.setName(selectedPart.getName());
                part.setStock(selectedPart.getStock());
                part.setPrice(selectedPart.getPrice());
                part.setMin(selectedPart.getMin());
                part.setMax(selectedPart.getMax());
                break;
            }
        }
    }

    public static void updateProduct(int index, Product newProduct){
        for (Product product : allProducts){
            if (product.getId() == index){
                product.setName(newProduct.getName());
                product.setStock(newProduct.getStock());
                product.setPrice(newProduct.getPrice());
                product.setMin(newProduct.getMin());
                product.setMax(newProduct.getMax());
                break;
            }
        }
    }

    public static boolean deletePart(Part selectedPart){
        for (Part part : allParts){
            if (part == selectedPart){
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteProduct(Product selectedProduct){
        for (Product product : allProducts){
            if (product == selectedProduct){
                allProducts.remove(product);
                return true;
            }
        }
        return false;
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
