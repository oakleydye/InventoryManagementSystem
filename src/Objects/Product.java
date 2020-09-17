package Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Oakley Dye
 *
 * Object that represents a product used by a company
 */
public class Product {
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor
     * @param id the product id
     * @param name the product name
     * @param price the product price
     * @param stock the product stock
     * @param min the product min
     * @param max the product max
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @return the product id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the product id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param price the product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return the product stock
     */
    public int getStock() {
        return stock;
    }

    /**
     *
     * @param stock the product stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return the product min
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @param min the product min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return the product max
     */
    public int getMax() {
        return max;
    }

    /**
     *
     * @param max the product max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @param part part to add to the list of parts associated with the product
     */
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }

    /**
     *
     * @param part the part to delete from the list of associated parts
     * @return true if the delete was successful, else false
     */
    public boolean deleteAssociatedPart(Part part){
        for (Part partFromList : getAllAssociatedParts()){
            if (partFromList.getId() == part.getId()){
                getAllAssociatedParts().remove(partFromList);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return a list of all parts associated with a product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
