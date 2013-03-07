package checkpoint.manager.datamodel;

import java.util.ArrayList;

public class Course {
    private char id;
    private int length;
    private ArrayList<Integer> nodes;

    /**
     * @return the id
     */
    public char getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(char id) {
        this.id = id;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the nodes
     */
    public ArrayList<Integer> getNodes() {
        return nodes;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(ArrayList<Integer> nodes) {
        this.nodes = nodes;
    }
    
    public int getNode(int index) {
        return getNodes().get(index);
    }
}
