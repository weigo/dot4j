/**
 *
 */
package org.arachna.dot4j.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A GraphViz graph.
 * 
 * @author Dirk Weigenand
 * 
 */
public class Graph {
    /**
     * Id of this cluster.
     */
    private final Id id;

    /**
     * if this is not null, this graph is a subgraph or cluster.
     */
    private final Graph parent;

    /**
     * attributes for this graph.
     */
    private final Attributes attributes = new Attributes();

    /**
     * common attributes for this graph's nodes.
     */
    private final Attributes nodeAttributes = new Attributes();

    /**
     * common attributes for this graph's edges.
     */
    private final Attributes edgeAttributes = new Attributes();

    /**
     * factory for cluster ids.
     */
    private final IdFactory clusterIdFactory;

    /**
     * factory for node ids.
     */
    private IdFactory nodeIdFactory;

    /**
     * Clusters contained in this graph.
     */
    private final Collection<Graph> clusters = new ArrayList<Graph>();

    /**
     * nodes contained in this graph/cluster.
     */
    private final Collection<Node> nodes = new ArrayList<Node>();

    /**
     * edges in this graph.
     */
    private final Collection<Edge> edges = new ArrayList<Edge>();

    /**
     * Create a subgraph or cluster with the given parent graph.
     * 
     * @param parent
     *            parent graph
     */
    private Graph(final Graph parent) {
        this.parent = parent;

        if (this.parent != null) {
            clusterIdFactory = parent.getClusterIdFactory();
            nodeIdFactory = parent.getNodeIdFactory();
        }
        else {
            clusterIdFactory = new IdFactory();
            nodeIdFactory = new IdFactory();
        }

        id = clusterIdFactory.nextId();
    }

    /**
     * Create a top level graph object.
     */
    public Graph() {
        this(null);
    }

    /**
     * Create a new subgraph/cluster.
     * 
     * @return a new subgraph/cluster.
     */
    public Graph newGraph() {
        final Graph child = new Graph(this);

        clusters.add(child);

        return child;
    }

    /**
     * Create a node and associate it with this graph/cluster.
     * 
     * @return creates a new node object and associates it with this cluster.
     */
    public Node newNode() {
        final Node node = new Node(nodeIdFactory.nextId());

        nodes.add(node);

        return node;
    }

    /**
     * Create a new edge using the given start and end nodes.
     * 
     * @param startNode
     *            start node of the new edge.
     * @param endNode
     *            end node of the new edge.
     * 
     * @return a new edge between start and end node.
     */
    public Edge newEdge(final Node startNode, final Node endNode) {
        final Edge edge = new Edge(startNode, endNode);

        edges.add(edge);

        return edge;
    }

    /**
     * Returns the clusters (subgraphs) of this graph.
     * 
     * @return the clusters
     */
    public Collection<Graph> getClusters() {
        return Collections.unmodifiableCollection(clusters);
    }

    /**
     * Returns the nodes contained in this graph.
     * 
     * @return the nodes contained in this graph.
     */
    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes);
    }

    /**
     * Returns the edges contained in this graph.
     * 
     * @return the edges contained in this graph.
     */
    public Collection<Edge> getEdges() {
        return Collections.unmodifiableCollection(edges);
    }

    /**
     * Returns the factory for cluster ids.
     * 
     * @return the clusterIdFactory
     */
    private IdFactory getClusterIdFactory() {
        return clusterIdFactory;
    }

    /**
     * Returns the factory for node ids.
     * 
     * @return the nodeIdFactory
     */
    private IdFactory getNodeIdFactory() {
        return nodeIdFactory;
    }

    /**
     * Returns the id of this graph.
     * 
     * @return the id of this graph.
     */
    public Id getId() {
        return id;
    }

    /**
     * Returns the attributes of this graph.
     * 
     * @return the attributes of this graph.
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * Returns the common attributes for the edges contained in this graph.
     * 
     * @return the common attributes for the edges contained in this graph.
     */
    public Attributes getEdgeAttributes() {
        return edgeAttributes;
    }

    /**
     * Returns the common attributes for the nodes contained in this graph.
     * 
     * @return the common attributes for the nodes contained in this graph.
     */
    public Attributes getNodeAttributes() {
        return nodeAttributes;
    }

    /**
     * Factory for Ids.
     * 
     * @author Dirk Weigenand
     */
    protected final class IdFactory {
        /**
         * counter for ids.
         */
        private long currentId;

        /**
         * Returns the current id and increments the internal id counter.
         * 
         * @return next id value.
         */
        synchronized Id nextId() {
            return new Id(currentId++);
        }
    }
}
