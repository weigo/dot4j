/**
 *
 */
package org.arachna.dot4j.model;

/**
 * A GraphViz graph.
 * 
 * @author Dirk Weigenand
 * 
 */
public class Graph {
    /**
     * if this is not null, this graph is a subgraph or cluster.
     */
    private final Graph parent;

    /**
     * Create a subgraph or cluster with the given parent graph.
     * 
     * @param parent
     *            parent graph
     */
    private Graph(final Graph parent) {
        this.parent = parent;
    }

    /**
     * Create a top level graph object.
     */
    public Graph() {
        parent = null;
    }

    /**
     * Create a new subgraph/cluster.
     * 
     * @return a new subgraph/cluster.
     */
    public Graph newGraph() {
        final Graph child = new Graph(this);

        return child;
    }
}
