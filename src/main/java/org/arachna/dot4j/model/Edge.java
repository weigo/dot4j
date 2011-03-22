/**
 *
 */
package org.arachna.dot4j.model;

/**
 * @author Dirk Weigenand
 */
public class Edge {
    private final Node startNode;
    private final Node endNode;
    private final Attributes attributes = new Attributes();

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
     * @return the attributes
     */
    public Attributes getAttributes() {
        return attributes;
    }
}
