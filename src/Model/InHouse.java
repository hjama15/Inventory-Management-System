package Model;

/**
 *
 * @author Hamza Jama
 */
/**
 * This is the InHouse class that extends the Part abstract class.
 */
public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * This method allows the machine to be changed according to the input.
     *
     * @param machineId the machineID to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * This will return the machine id.
     *
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
}
