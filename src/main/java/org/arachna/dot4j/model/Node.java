/**
 *
 */
package org.arachna.dot4j.model;

/**
 * A graphviz node.
 * 
 * @author Dirk Weigenand
 */
public final class Node {
    /**
     * node Id.
     */
    private final Id id;

    /**
     * attributes of this node.
     */
    private final Attributes attributes = new Attributes();

    /**
     * Create a new node with the given id.
     * 
     * @param id
     *            node Id.
     */
    Node(final Id id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Id getId() {
        return id;
    }

    /**
     * Returns the attributes of this node.
     * 
     * @return attributes of this node.
     */
    public Attributes getAttributes() {
        return attributes;
    }
}
