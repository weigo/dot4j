package org.arachna.dot4j;

import org.arachna.dot4j.model.Attributes;
import org.arachna.dot4j.model.Edge;
import org.arachna.dot4j.model.Graph;
import org.arachna.dot4j.model.Node;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for implementing a generator of graphviz .dot models (graphs).
 */
public abstract class AbstractDotFileGenerator {
    /**
     * Global graph attributes.
     */
    private final DotFileGeneratorConfiguration globalConfig = new DotFileGeneratorConfiguration();
    /**
     * housekeeping of node names already generated.
     */
    private final Map<String, Node> nodeNames = new HashMap<>();
    private final Map<String, Graph> groupNames = new HashMap<>();
    /**
     * Model for dependency graph.
     */
    private Graph graph;

    /**
     * {@inheritDoc}
     */
    public void generate(Writer writer) throws IOException {
        graph = new Graph();
        final Attributes attributes = graph.getAttributes();
        attributes.setAttribute("shape", "record");
        attributes.setAttribute("rankdir", globalConfig.getRankDirection());
        attributes.setAttribute("ranksep", "equally");
        attributes.setAttribute("compound", "true");
        attributes.setAttribute("newrank", "true");
        attributes.setAttribute("fontsize", Integer.toString(globalConfig.getFontSize()));

        graph.getNodeAttributes().setAttribute("shape", "record");

        generateInternal();

        final DotGenerator generator = new DotGenerator(graph);
        generator.generate(writer);
    }

    protected final Graph getCluster(final String groupName, final Graph parent) {
        return groupNames.computeIfAbsent(groupName, key -> {
            Graph group = parent == null ? graph.newGraph() : parent.newGraph();
            final Attributes attributes = group.getAttributes();
            attributes.setAttribute("label", key);

            return group;
        });
    }

    /**
     * Get the node created with the given name.
     *
     * @param nodeName name under which the node can be found.
     * @return node indexed with the given node name.
     */
    protected final Node getNode(final String nodeName) {
        return nodeNames.get(nodeName);
    }

    /**
     * Add a new node to this graph.
     *
     * @param rank        key for grouping nodes into the same rank
     * @param identifier  the name the new node should be registered with.
     * @param clusterName the name of the parent cluster it should be registered with.
     * @return the created node.
     */
    protected final Node addNode(String rank, final NodeIdentifier identifier, final String clusterName) {
        return nodeNames.computeIfAbsent(identifier.getNodeName(), k -> {
            final Graph cluster = clusterName != null ? getCluster(clusterName, null) : this.graph;
            final Node node = cluster.newNode();
            Attributes attributes = node.getAttributes();
            attributes.setAttribute("shape", "record");
            attributes.setAttribute("fontsize", Integer.toString(this.getGlobalConfig().getFontSize()));

            if (rank != null && !rank.isEmpty()) {
                cluster.rank(rank, node);
            }

            return node;
        });
    }

    /**
     * Create a new edge using the two given nodes.
     *
     * @param source source node for the edge.
     * @param target target node for the edge.
     * @return the newly created edge.
     */
    protected final Edge addEdge(final Node source, final Node target) {
        return graph.newEdge(source, target);
    }

    /**
     * Has to be implemented by subclasses to generate the actual graph. E.g.
     * {code}
     *
     * @Override protected void generateInternal() {
     * createNodes();
     * createEdges();
     * }
     * {code}
     * The implementation would generate the graphs nodes based on the business objects that should be visualized
     * and afterward connect those nodes with edges between those nodes.
     */
    protected abstract void generateInternal();

    /**
     * Return global configuration settings.
     *
     * @return the global configuration settings
     */
    protected final DotFileGeneratorConfiguration getGlobalConfig() {
        return globalConfig;
    }
}

