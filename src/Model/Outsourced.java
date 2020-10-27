package Model;

/**
 *
 * @author Hamza Jama
 */
/**
 * This is the Outsourced class that extends the Part abstract class.
 */
public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This method allows the company to be changed according to the input.
     *
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This will return the company name.
     *
     * @return the companyName
     */
    public String getCompanyName() {
        return this.companyName;
    }
}
