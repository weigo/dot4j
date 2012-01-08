/**
 *
 */
package org.arachna.dot4j.model;

/**
 * A node in a graph.
 * 
 * @author Dirk Weigenand
 */
public final class Node {
    /**
     * node Id.
     */
    private final Id id;

    /**
     * the graph containing this node.
     */
    private final Graph graph;

    /**
     * attributes of this node.
     */
    private final Attributes attributes = new Attributes();

    /**
     * Create a new node with the containing graph and given id.
     * 
     * @param graph
     *            the containing graph.
     * @param id
     *            node Id.
     */
    Node(final Graph graph, final Id id) {
        this.graph = graph;
        this.id = id;
    }

    /**
     * Gets the id of this node.
     * 
     * @return the node id
     */
    public Id getId() {
        return id;
    }

    /**
     * Get this nodes attributes.
     * 
     * @return the attributes of this node.
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * Get the graph this node belongs to.
     * 
     * @return the containing graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        return id.equals(((Node)obj).getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Node [id=" + id + ", attributes=" + attributes + "]";
    }
}
