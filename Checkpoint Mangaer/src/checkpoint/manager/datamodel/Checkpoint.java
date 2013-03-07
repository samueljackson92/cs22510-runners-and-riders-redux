package checkpoint.manager.datamodel;

public class Checkpoint {
    private int id;
    private CPType type;

    public Checkpoint(int id, String type) {
        this.id = id;
        this.type = CPType.valueOf(type);
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the type
     */
    public CPType getType() {
        return type;
    }
}
