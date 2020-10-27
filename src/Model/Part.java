package Model;

/**
 * Supplied class Part.java
 */
/**
 *
 * @author Hamza Jama
 */
/**
 * This is the part abstract class that will store the InHouse and Outsourced
 * will inherit from.
 */
public abstract class Part {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(int id, String name, double price, int stock, int min, int max) {
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
     * @return the min
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

}
