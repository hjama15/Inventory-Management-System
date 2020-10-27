package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hamza Jama
 */
/**
 * This is the product class that will store the product's values.
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This will return the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the id to the input parameter's value.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This will return the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * This method allows the name to be changed according to the input
     * parameter.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This will return the price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method allows the price to be changed according to the input
     * parameter.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This will return the number in stock.
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method allows the stock to be changed according to the input
     * parameter.
     *
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This will return the min.
     *
     * @return min the min to set
     */
    public int getMin() {
        return min;
    }

    /**
     * This method allows the min to be changed according to the input
     * parameter.
     *
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This will return the max.
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * This method allows the max to be changed according to the input
     * parameter.
     *
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method will add the part in the parameter.
     *
     * @param part the associatedPart to add
     */
    public void addAssociatedPart(Part part) {
        getAllAssociatedParts().add(part);
    }

    /**
     * This method will delete the associated part by looping until it finds it
     * then will return a true or false.
     *
     * @param selectedAssociatedPart the selectedAssociatedPart to compare
     * @return true or false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        for (int i = 0; i < getAllAssociatedParts().size(); i++) {

            Part part = getAllAssociatedParts().get(i);

            if (part.equals(selectedAssociatedPart)) {

                getAllAssociatedParts().remove(part);
                return true;
            }
        }
        return false;
    }

    /**
     * This method will return a list of the associated parts.
     *
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
