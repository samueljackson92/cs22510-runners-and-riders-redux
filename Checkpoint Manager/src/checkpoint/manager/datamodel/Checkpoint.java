package checkpoint.manager.datamodel;

/**
 * The Class Checkpoint.
 * Holds data about a single checkpoint (or medical checkpoint) in an event.
 * @author Samuel Jackson (slj11@aber.ac.uk)
 */
public class Checkpoint {
    
    /** The id of the checkpoint */
    private int id;
    
    /** The type of the checkpoint. */
    private CPType type;

    /**
     * Instantiates a new checkpoint.
     *
     * @param id the id of the checkpoint
     * @param type the type of the checkpoint
     */
    public Checkpoint(int id, String type) {
        this.id = id;
        this.type = CPType.valueOf(type);
    }
    
    /**
     * Gets the id of the checkpoint.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the type type of the checkpoint.
     *
     * @return the type
     */
    public CPType getType() {
        return type;
    }
}
