package Objects;

/**
 * @author Oakley Dye
 *
 * Class extends {@link Part} and is used to create/modify parts produced by the company
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * Constructor
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the part stock
     * @param min the part min
     * @param max the part max
     * @param machineId the machine id
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @return the machine id that the product is produced on
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     *
     * @param machineId the id to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}