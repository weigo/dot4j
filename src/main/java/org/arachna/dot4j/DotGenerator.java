/**
 *
 */
package org.arachna.dot4j;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.arachna.dot4j.model.*;
import org.arachna.dot4j.model.Attributes.Attribute;

/**
 * A generator for GraphViz <code>.dot</code> files.
 *
 * @author Dirk Weigenand
 */
public final class DotGenerator {

    /**
     * Graph to generate <code>.dot</code> file from.
     */
    private final Graph graph;

    /**
     * Create a dot file generator for the given graph.
     *
     * @param graph the {@link Graph} to generate a <code>.dot</code> file from.
     */
    public DotGenerator(final Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph must not be null!");
        }

        this.graph = graph;
    }

    /**
     * Generate the graph into the given {@link Writer}.
     *
     * @param writer {@link Writer} to generate the <code>.dot</code>
     *               representation of the graph into.
     * @throws IOException when writing into the given writer fails for some reason.
     */
    public void generate(final Writer writer) throws IOException {
        writer.append("digraph ");
        writer.append("{\n");
        emitGraph(graph, writer);
        writer.append("}\n");
    }

    /**
     * Create common attributes for edges in this graph.
     * <p>
     * The returned string is empty if there are no common edge attributes
     * defined.
     *
     * @throws IOException when writing fails
     */
    private void emitCommonEdgeAttributes(Writer writer) throws IOException {
        if (!graph.getEdgeAttributes().isEmpty()) {
            writer.append(String.format("edge %s;", emitAttributes(graph.getEdgeAttributes())));
        }
    }

    /**
     * Create common attributes for nodes in the graph.
     * <p>
     * The returned string is empty if there are no common node attributes
     * defined.
     *
     * @param writer to write graph into
     * @throws IOException when writing fails
     */
    private void emitCommonNodeAttributes(Writer writer) throws IOException {
        if (!graph.getEdgeAttributes().isEmpty()) {
            writer.append(String.format("node %s;", emitAttributes(graph.getNodeAttributes())));
        }
    }

    /**
     * Generate the <code>.dot</code> representation of the given graph.
     *
     * @param graph  the graph to generate the <code>.dot</code> representation
     *               from
     * @param writer the {@link Writer} object to write the graph into
     * @throws IOException when writing fails
     */
    private void emitGraph(final Graph graph, Writer writer) throws IOException {
        this.emitGraphAttributes(graph.getAttributes(), writer);
        emitCommonNodeAttributes(writer);
        emitCommonEdgeAttributes(writer);
        emitNodes(graph, writer);
        emitEdges(graph.getEdges(), writer);
        emitClusters(graph.getClusters(), writer);
    }

    /**
     * Generate the attribute representation for the given attributes.
     *
     * @param attributes attributes of a graph.
     * @throws IOException when writing fails
     */
    private void emitGraphAttributes(final Attributes attributes, Writer writer) throws IOException {
        for (final Attribute attribute : attributes) {
            writer.append(String.format("%s = \"%s\";\n", attribute.getName(), attribute.getValue()));
        }
    }

    /**
     * Emit the edges of the graph.
     *
     * @param edges edges to generate <code>.dot</code> representation for.
     * @throws IOException when writing fails
     */
    void emitEdges(final Collection<Edge> edges, Writer writer) throws IOException {
        for (final Edge edge : edges) {
            writer.append(emitEdge(edge));
        }
    }

    /**
     * Emit the <code>.dot</code> representation of a single edge.
     *
     * @param edge edge to generate <code>.dot</code> representation for.
     * @return the <code>.dot</code> representation of the given edge.
     */
    String emitEdge(final Edge edge) {
        return String.format("node%s -> node%s%s;\n", edge.getStartNode().getId(), edge.getEndNode().getId(),
                emitAttributes(edge.getAttributes()));
    }

    /**
     * Emit the clusters of the graph.
     *
     * @param clusters a collection of clusters contained in this graph.
     * @throws IOException when writing fails
     */
    void emitClusters(final Collection<Graph> clusters, Writer writer) throws IOException {
        for (final Graph cluster : clusters) {
            emitCluster(cluster, writer);
        }
    }

    /**
     * Emit the nodes of this graph.
     *
     * @param graph whose nodes to emit.
     * @param writer to write into
     * @throws IOException when writing fails
     */
    private void emitNodes(final Graph graph, Writer writer) throws IOException {
        Set<Id> emittedNodes = new HashSet<>();

        for (Collection<Node> nodes: graph.getRankedNodes().values()) {
            writer.write("\n{\nrank=same;\n");
            for (Node node: nodes) {
                emittedNodes.add(node.getId());
                writer.append(emitNode(node));
            }
            writer.write("\n}\n");
        }

        for (Node node : graph.getNodes().stream().filter(node -> !emittedNodes.contains(node.getId())).toList()) {
            writer.append(emitNode(node));
        }
    }

    /**
     * Emit a cluster of this graph.
     *
     * @param cluster cluster to emit
     * @throws IOException when writing fails
     */
    void emitCluster(final Graph cluster, Writer writer) throws IOException {
        writer.append(String.format("subgraph cluster%s {\n", cluster.getId()));
        emitGraph(cluster, writer);
        writer.append("}\n");
    }

    /**
     * Emit a node.
     *
     * @param node node to emit
     * @return the <code>.dot</code> representation of the given node.
     */
    String emitNode(final Node node) {
        return String.format("node%s%s;\n", node.getId(), emitAttributes(node.getAttributes()));
    }

    /**
     * Emit attributes.
     *
     * @param attributes the attributes to emit
     * @return the <code>.dot</code> representation of the given attributes.
     */
    StringBuilder emitAttributes(final Attributes attributes) {
        final StringBuilder result = new StringBuilder();

        if (!attributes.isEmpty()) {
            result.append(" [");

            for (final Attributes.Attribute attribute : attributes) {
                if (attribute.getValue().startsWith("<") && attribute.getValue().endsWith(">")) {
                    result.append(String.format(" %s=%s", attribute.getName(), attribute.getValue()));
                } else {
                    result.append(String.format(" %s=\"%s\"", attribute.getName(), attribute.getValue()));

                }
            }

            result.append("]");
        }

        return result;
    }
}
