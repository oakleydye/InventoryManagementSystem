package Objects;

/**
 * @author Oakley Dye
 * class extends {@link Part}, used when a part is not manufactured by the company.
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Constructor
     * @param id the part id
     * @param name the part name
     * @param price the part prices
     * @param stock the part stock
     * @param min the part min
     * @param max the part max
     * @param comapnyName the manufacturing company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String comapnyName){
        super(id, name, price, stock, min, max);
    }

    /**
     *
     * @return the name of the company that manufactures the part
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName the name of the company that manufactures the part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}