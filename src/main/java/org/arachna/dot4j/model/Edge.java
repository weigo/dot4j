/**
 *
 */
package org.arachna.dot4j.model;

/**
 * An edge in a {@link Graph}.
 * 
 * @author Dirk Weigenand
 */
public class Edge {
    /**
     * the start node of this edge.
     */
    private Node startNode;

    /**
     * the end node of this edge.
     */
    private Node endNode;

    /**
     * Attributes for this edge.
     */
    private final Attributes attributes = new Attributes();

    /**
     * Create a new edge with the given start and end node. The attributes are
     * initialized to an empty collection of attributes.
     * 
     * @param startNode
     *            start node of this edge.
     * @param endNode
     *            end node of this edge.
     */
    Edge(final Node startNode, final Node endNode) {

        if (startNode == null || endNode == null) {
            throw new IllegalArgumentException("start and end node must not be null!");
        }

        this.startNode = startNode;
        this.endNode = endNode;
    }

    /**
     * @return the startNode
     */
    public Node getStartNode() {
        return startNode;
    }

    /**
     * @return the endNode
     */
    public Node getEndNode() {
        return endNode;
    }

    /**
     * @param startNode
     *            the startNode to set
     */
    public void setStartNode(Node startNode) {
        if (startNode == null) {
            throw new IllegalArgumentException("startNode must not be null!");
        }

        this.startNode = startNode;
    }

    /**
     * @param endNode
     *            the endNode to set
     */
    public void setEndNode(Node endNode) {
        if (endNode == null) {
            throw new IllegalArgumentException("endNode must not be null!");
        }

        this.endNode = endNode;
    }

    /**
     * @return the attributes
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Edge [attributes=" + attributes + ", startNode=" + startNode + ", endNode=" + endNode + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;
        return startNode.equals(edge.startNode) && endNode.equals(edge.endNode);
    }

    @Override
    public int hashCode() {
        int result = startNode.hashCode();
        result = 31 * result + endNode.hashCode();
        return result;
    }
}
