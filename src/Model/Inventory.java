package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hamza Jama
 */
/**
 * This is the class that stores parts and products and allows their
 * manipulation.
 */
public class Inventory {

    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static ObservableList<Part> allFilteredParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allFilteredProducts = FXCollections.observableArrayList();
    public static ObservableList<Part> allTempAssociatedParts = FXCollections.observableArrayList();

    /**
     * This method adds newPart to the allParts list.
     *
     * @param newPart adds newPart to allParts
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds newProducts to allProducts list.
     *
     * @param newProduct adds newProduct to allProducts
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method adds newFilteredParts to the allFilteredParts.
     *
     * @param newFilteredPart adds newFilteredPart to allFilteredParts
     */
    public static void addFilteredPart(Part newFilteredPart) {
        allFilteredParts.add(newFilteredPart);
    }

    /**
     * This method adds newFilteredProducts to the allFilteredProducts.
     *
     * @param newFilteredProduct add newFilteredProduct to allFilteredProducts
     */
    public static void addFilteredProduct(Product newFilteredProduct) {
        allFilteredProducts.add(newFilteredProduct);
    }

    /**
     * This method adds parts to the tempAssociatedParts lists.
     *
     * @param part adds part to tempAssociatedParts
     */
    public static void addTempAssociatedParts(Part part) {
        allTempAssociatedParts.add(part);
    }

    /**
     * This method isn't being used.
     *
     * @param partId set to nothing
     * @return null
     */
    public static Part lookupPart(int partId) {
        return null;
    }

    /**
     * This method isn't being used.
     *
     * @param productId set to nothing
     * @return null
     */
    public static Product lookupProduct(int productId) {
        return null;
    }

    /**
     * This method isn't being used.
     *
     * @param partName set to nothing
     * @return null
     */
    public static ObservableList<Part> lookupPart(String partName) {
        return null;
    }

    /**
     * This method isn't being used.
     *
     * @param productName set to nothing
     * @return null
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        return null;
    }

    /**
     * This method loops through allParts list until it matches the
     * selectedPart's id and replaces the part.
     *
     * @param index compares inputted id to id of parts looped through AllParts
     * @param selectedPart replaces the part with matched id
     */
    public static void updatePart(int index, Part selectedPart) {

        int i = -1;

        for (Part part : getAllParts()) {

            i++;

            if (part.getId() == index) {

                getAllParts().set(i, selectedPart);

            }
        }
    }

    /**
     * This method loops through allProducts list until it matches the
     * newMrpoduct's id and replaces the product.
     *
     * @param index compares inputted id to id of parts looped through AllParts
     * @param newProduct replaces the product with matched id
     */
    public static void updateProduct(int index, Product newProduct) {

        int i = -1;

        for (Product product : getAllProducts()) {

            i++;

            if (product.getId() == index) {

                getAllProducts().set(i, newProduct);

            }
        }
    }

    /**
     * This method loops through allParts list until selectedPart's equivalent
     * is found then removed, and returns true or false depending on if it's
     * removed or not.
     *
     *
     * @param selectedPart compares to allParts by looping through till matched
     * to remove
     * @return true or false
     */
    public static boolean deletePart(Part selectedPart) {

        for (int i = 0; i < getAllParts().size(); i++) {

            Part part = getAllParts().get(i);

            if (part.equals(selectedPart)) {

                getAllParts().remove(part);
                return true;
            }
        }
        return false;
    }

    /**
     * This method loops through allProducts list until selectedProduct's
     * equivalent is found then removed, and returns true or false depending on
     * if it's removed or not.
     *
     *
     * @param selectedProduct compares to allProduct by looping through till
     * matched to remove
     * @return true or false
     */
    public static boolean deleteProduct(Product selectedProduct) {

        for (int i = 0; i < getAllProducts().size(); i++) {

            Product product = getAllProducts().get(i);

            if (product.equals(selectedProduct)) {

                getAllProducts().remove(product);
                return true;
            }
        }
        return false;
    }

    /**
     * This method loops through allTempAssociatedParts list until
     * selectedPart's equivalent is found then removed, and returns true or
     * false on if it's removed or not.
     *
     * @param selectedPart compares to AllTempAssociatedParts by looping through
     * till matched to remove
     * @return true or false
     */
    public static boolean deleteTempAssociatedPart(Part selectedPart) {

        for (int i = 0; i < getAllTempAssociatedParts().size(); i++) {

            Part part = getAllTempAssociatedParts().get(i);

            if (part.equals(selectedPart)) {

                getAllTempAssociatedParts().remove(part);
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns a list of all parts.
     *
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method returns a list of all products.
     *
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * This method returns a list of all filtered parts.
     *
     * @return allFilteredParts
     */
    public static ObservableList<Part> getAllFilteredParts() {
        return allFilteredParts;
    }

    /**
     * This method returns a list of all filtered products.
     *
     * @return allFilteredProducts
     */
    public static ObservableList<Product> getAllFilteredProducts() {
        return allFilteredProducts;
    }

    /**
     * This method returns a list of all temp associated parts.
     *
     * @return allTempAssociatedParts
     */
    public static ObservableList<Part> getAllTempAssociatedParts() {
        return allTempAssociatedParts;
    }

    /**
     * This method returns a generated unique part id.
     *
     * @return a part id
     */
    public static int generatePartId() {
        int id = 1;

        if (getAllParts() != null) {

            for (Part part : getAllParts()) {

                if (part.getId() == id) {

                    id++;
                }
            }
        }
        return id;
    }

    /**
     * This method returns a generated unique product id.
     *
     * @return a product id
     */
    public static int generateProductId() {
        int id = 1;

        if (getAllProducts() != null) {

            for (Product product : getAllProducts()) {

                if (product.getId() == id) {

                    id++;
                }
            }
        }
        return id;
    }
}
