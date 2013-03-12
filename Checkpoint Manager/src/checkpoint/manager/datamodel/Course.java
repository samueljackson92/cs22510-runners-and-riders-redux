package checkpoint.manager.datamodel;

import java.util.ArrayList;

/**
 * The Class Course.
 * Holds data about a single course
 * 
 * @author Samuel Jackson (slj11@aber.ac.uk)
 */
public class Course {
    
    /** The id of the course */
    private char id;
    
    /** The nodes in the course */
    private ArrayList<Integer> nodes;

    /**
     * Gets the id of the course.
     *
     * @return the id
     */
    public char getId() {
        return id;
    }

    /**
     * Sets the id of the course.
     *
     * @param id the id to set
     */
    public void setId(char id) {
        this.id = id;
    }

    /**
     * Gets the length.
     *
     * @return the length
     */
    public int getLength() {
        return nodes.size();
    }

    /**
     * Gets the nodes in the course.
     *
     * @return the nodes
     */
    public ArrayList<Integer> getNodes() {
        return nodes;
    }

    /**
     * Sets the nodes in the course.
     *
     * @param nodes the nodes to set
     */
    public void setNodes(ArrayList<Integer> nodes) {
        this.nodes = nodes;
    }
    
    /**
     * Gets the node.
     *
     * @param index the index of the node.
     * @return the node
     */
    public int getNode(int index) {
        return getNodes().get(index);
    }
}
