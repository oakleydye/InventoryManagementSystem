package Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Oakley Dye
 *
 * Static class to manage the inventory. Used by all other classes to reference and modify parts and products.
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *
     * @param newPart the part to add
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     *
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     *
     * @param partId the id of the part to return
     * @return the part matching the supplied id
     */
    public static Part lookupPart(int partId){
        for (Part part : allParts){
            if (part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    /**
     *
     * @param productId the id of the product to return
     * @return the product matching the supplied id
     */
    public static Product lookupProduct(int productId){
        for (Product product : allProducts){
            if (product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    /**
     *
     * @param partName the name of the part to return
     * @return the part matching the supplied name
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for (Part part : allParts){
            if (part.getName().equals(partName)){
                parts.add(part);
            }
        }
        return parts;
    }

    /**
     *
     * @param productName the name of the product to return
     * @return the product matching the supplied name
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> products = FXCollections.observableArrayList();
        for (Product product : allProducts){
            if (product.getName().equals(productName)){
                products.add(product);
            }
        }
        return products;
    }

    /**
     * This method updates a part in allParts if the part id exists in the list
     * @param index the part id
     * @param selectedPart the part
     */
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

    /**
     * This method updates a product in allProducts if the product id exists in the list
     * @param index the product id
     * @param newProduct the product
     */
    public static void updateProduct(int index, Product newProduct){
        for (Product product : allProducts){
            if (product.getId() == index){
                product.setName(newProduct.getName());
                product.setStock(newProduct.getStock());
                product.setPrice(newProduct.getPrice());
                product.setMin(newProduct.getMin());
                product.setMax(newProduct.getMax());
                for (Part part : product.getAllAssociatedParts()){
                    product.deleteAssociatedPart(part);
                }
                for (Part part : newProduct.getAllAssociatedParts()){
                    product.addAssociatedPart(part);
                }
                break;
            }
        }
    }

    /**
     *
     * @param selectedPart the part to delete
     * @return true if the delete is successful, else false
     */
    public static boolean deletePart(Part selectedPart){
        for (Part part : allParts){
            if (part == selectedPart){
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param selectedProduct product to delete
     * @return true if delete is successful, else false
     */
    public static boolean deleteProduct(Product selectedProduct){
        for (Product product : allProducts){
            if (product == selectedProduct){
                allProducts.remove(product);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return a list of all parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     *
     * @return a list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
