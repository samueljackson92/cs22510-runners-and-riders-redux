package checkpoint.manager;

class Checkpoint {
    private int id;
    private cpType type;

    public Checkpoint(int id, String type) {
        this.id = id;
        this.type = cpType.valueOf(type);
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
    public cpType getType() {
        return type;
    }
}
