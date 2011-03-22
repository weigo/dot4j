/**
 *
 */
package org.arachna.dot4j;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import org.arachna.dot4j.model.Attributes;
import org.arachna.dot4j.model.Attributes.KeyValuePair;
import org.arachna.dot4j.model.Edge;
import org.arachna.dot4j.model.Graph;
import org.arachna.dot4j.model.Node;

/**
 * @author Dirk Weigenand
 */
public final class DotGenerator {

    /**
     * Graph to generate .dot file from.
     */
    private final Graph graph;

    public DotGenerator(final Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph must not be null!");
        }

        this.graph = graph;
    }

    public void generate(final Writer writer) throws IOException {
        writer.append("digraph ");
        writer.append("{\n");
        writer.append(this.emitGraphAttributes(this.graph.getAttributes()));
        writer.append(emitCommonNodeAttributes());
        writer.append(emitCommonEdgeAttributes());
        writer.append(emitGraph(graph));
        writer.append("}\n");
    }

    /**
     * @return
     */
    private String emitCommonEdgeAttributes() {
        final StringBuffer result = new StringBuffer();

        if (!graph.getEdgeAttributes().isEmpty()) {
            result.append(String.format("edge %s;", emitAttributes(graph.getEdgeAttributes())));
        }

        return result.toString();
    }

    /**
     * @return
     */
    private String emitCommonNodeAttributes() {
        final StringBuffer result = new StringBuffer();

        if (!graph.getEdgeAttributes().isEmpty()) {
            result.append(String.format("node %s;", emitAttributes(graph.getNodeAttributes())));
        }

        return result.toString();
    }

    /**
     * @param writer
     * @throws IOException
     */
    private String emitGraph(final Graph graph) {
        final StringBuffer result = new StringBuffer();
        result.append(emitNodes(graph.getNodes()));
        result.append(emitEdges(graph.getEdges()));
        result.append(emitClusters(graph.getClusters()));

        return result.toString();
    }

    private String emitGraphAttributes(final Attributes attributes) {
        final StringBuffer result = new StringBuffer();

        for (final KeyValuePair pair : attributes) {
            result.append(String.format("%s = \"%s\";\n", pair.getKey(), pair.getValue()));
        }

        return result.toString();
    }

    protected StringBuffer emitEdges(final Collection<Edge> edges) {
        final StringBuffer result = new StringBuffer();

        for (final Edge edge : edges) {
            result.append(emitEdge(edge));
        }

        return result;
    }

    /**
     * @param edge
     */
    protected String emitEdge(final Edge edge) {
        return String.format("node%s -> node%s%s;\n", edge.getStartNode().getId(), edge.getEndNode().getId(),
            emitAttributes(edge.getAttributes()));
    }

    /**
     * @param clusters
     * @throws IOException
     */
    protected StringBuffer emitClusters(final Collection<Graph> clusters) {
        final StringBuffer result = new StringBuffer();

        for (final Graph cluster : clusters) {
            result.append(emitCluster(cluster));
        }

        return result;
    }

    /**
     * @param writer
     * @param nodes
     * @throws IOException
     */
    protected StringBuffer emitNodes(final Collection<Node> nodes) {
        final StringBuffer result = new StringBuffer();

        for (final Node node : nodes) {
            result.append(emitNode(node));
        }

        return result;
    }

    protected StringBuffer emitCluster(final Graph cluster) {
        final StringBuffer result = new StringBuffer();

        result.append(String.format("subgraph cluster%s {\n", cluster.getId()));
        result.append(emitGraph(cluster));
        result.append("}\n");

        return result;
    }

    protected String emitNode(final Node node) {
        return String.format("node%s%s;\n", node.getId(), emitAttributes(node.getAttributes()));
    }

    protected StringBuffer emitAttributes(final Attributes attributes) {
        final StringBuffer result = new StringBuffer();

        if (!attributes.isEmpty()) {
            result.append(" [");

            for (final Attributes.KeyValuePair pair : attributes) {
                result.append(String.format(" %s=\"%s\"", pair.getKey(), pair.getValue()));
            }

            result.append("]");
        }

        return result;
    }
}
