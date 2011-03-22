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
     * common attributes for this graphs nodes.
     */
    private final Attributes nodeAttributes = new Attributes();

    /**
     * common attributes for this graphs edges.
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
            this.clusterIdFactory = parent.getClusterIdFactory();
            this.nodeIdFactory = parent.getNodeIdFactory();
        }
        else {
            this.clusterIdFactory = new IdFactory();
            this.nodeIdFactory = new IdFactory();
        }

        this.id = this.clusterIdFactory.nextId();
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

        this.clusters.add(child);

        return child;
    }

    /**
     * Create a node and associate it with this graph/cluster.
     * 
     * @return creates a new node object and associates it with this cluster.
     */
    public Node newNode() {
        final Node node = new Node(this.nodeIdFactory.nextId());

        this.nodes.add(node);

        return node;
    }

    public Edge newEdge(final Node startNode, final Node endNode) {
        final Edge edge = new Edge(startNode, endNode);

        this.edges.add(edge);

        return edge;
    }

    /**
     * @return the clusters
     */
    public Collection<Graph> getClusters() {
        return Collections.unmodifiableCollection(clusters);
    }

    /**
     * @return the nodes
     */
    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes);
    }

    /**
     * @return the edges
     */
    public Collection<Edge> getEdges() {
        return Collections.unmodifiableCollection(edges);
    }

    /**
     * @return the clusterIdFactory
     */
    private IdFactory getClusterIdFactory() {
        return clusterIdFactory;
    }

    /**
     * @return the nodeIdFactory
     */
    private IdFactory getNodeIdFactory() {
        return this.nodeIdFactory;
    }

    /**
     * @return the id
     */
    public Id getId() {
        return id;
    }

    /**
     * @return the attributes
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * @return the edgeAttributes
     */
    public Attributes getEdgeAttributes() {
        return edgeAttributes;
    }

    /**
     * @return the nodeAttributes
     */
    public Attributes getNodeAttributes() {
        return nodeAttributes;
    }

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
