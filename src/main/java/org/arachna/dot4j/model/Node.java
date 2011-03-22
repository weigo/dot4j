/**
 *
 */
package org.arachna.dot4j.model;

/**
 * @author Dirk Weigenand
 */
public final class Node {
    /**
     * node Id.
     */
    private final Id id;

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

    public Attributes getAttributes() {
        return attributes;
    }
}
