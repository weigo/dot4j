/**
 *
 */
package org.arachna.dot4j;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import org.arachna.dot4j.model.Attributes;
import org.arachna.dot4j.model.Edge;
import org.arachna.dot4j.model.Graph;
import org.arachna.dot4j.model.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link DotGenerator}.
 * 
 * @author Dirk Weigenand
 */
public class DotGeneratorTest {
    /**
     * 
     */
    private static final String LABEL = "label";

    /**
     * Graph to serialize as <code>.dot</code>.
     */
    private Graph graph;

    /**
     * DotGenerator under test.
     */
    private DotGenerator generator;

    /**
     * Setup test instances.
     */
    @Before
    public void setUp() {
        this.graph = new Graph();
        this.generator = new DotGenerator(this.graph);
    }

    /**
     * nullify test instances.
     */
    @After
    public void tearDown() {
        this.graph = null;
        this.generator = null;
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#DotGenerator(org.arachna.dot4j.model.Graph)}
     * .
     * 
     * @throws IOException
     */
    @Test
    public final void testGenerateEmptyGraph() throws IOException {
        final StringWriter result = new StringWriter();
        this.generator.generate(result);

        assertEquals("digraph {\n}\n", result.toString());
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#generate(java.io.Writer)}.
     * 
     * @throws IOException
     */
    @Test
    public final void testGenerateOneNode() throws IOException {
        graph.newNode();
        final StringWriter result = new StringWriter();
        this.generator.generate(result);

        assertEquals("digraph {\nnode0;\n}\n", result.toString());
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#generate(java.io.Writer)}.
     * 
     * @throws IOException
     */
    @Test
    public final void testGenerateTwoNodes() throws IOException {
        graph.newNode();
        graph.newNode();

        final StringWriter result = new StringWriter();
        this.generator.generate(result);

        assertEquals("digraph {\nnode0;\nnode1;\n}\n", result.toString());
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#emitEdges(java.util.Collection)}.
     * 
     * @throws IOException
     */
    @Test
    public final void testEmitEdgesWithoutAttributes() throws IOException {
        final Node start = graph.newNode();
        final Node middle = graph.newNode();
        final Node end = graph.newNode();

        final Edge firstEdge = graph.newEdge(start, middle);
        final Edge secondEdge = graph.newEdge(middle, end);
        StringWriter result = new StringWriter();
        this.generator.emitEdges(Arrays.asList(new Edge[] { firstEdge, secondEdge }), result);

        assertEquals("node0 -> node1;\nnode1 -> node2;\n", result.toString());
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#emitEdge(org.arachna.dot4j.model.Edge)}
     * .
     */
    @Test
    public final void testEmitEdgeWithoutAttributes() {
        final Node start = graph.newNode();
        final Node end = graph.newNode();

        final Edge edge = graph.newEdge(start, end);
        final String result = this.generator.emitEdge(edge);

        assertEquals("node0 -> node1;\n", result.toString());
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#emitClusters(java.util.Collection)}
     * .
     * 
     * @throws IOException
     *             when writing the .dot file fails
     */
    @Test
    public final void testEmitClusters() throws IOException {
        final Graph firstCluster = this.graph.newGraph();
        final Graph secondCluster = this.graph.newGraph();
        final StringWriter result = new StringWriter();
        this.generator.emitClusters(Arrays.asList(new Graph[] { firstCluster, secondCluster }), result);

        assertEquals("subgraph cluster1 {\n}\nsubgraph cluster2 {\n}\n", result.toString());
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#emitCluster(org.arachna.dot4j.model.Graph)}
     * .
     * 
     * @throws IOException
     *             when writing the .dot file fails
     */
    @Test
    public final void testEmitEmptyCluster() throws IOException {
        final StringWriter result = new StringWriter();
        this.generator.emitCluster(graph, result);

        assertEquals("subgraph cluster0 {\n}\n", result.toString());
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#emitCluster(org.arachna.dot4j.model.Graph)}
     * .
     * 
     * @throws IOException
     *             when writing the .dot file fails
     */
    @Test
    public final void testEmitEmptyClusterWithAttributes() throws IOException {
        final Graph cluster = this.graph.newGraph();
        final Attributes attributes = cluster.getAttributes();
        attributes.setAttribute(LABEL, LABEL);
        final StringWriter result = new StringWriter();
        this.generator.emitCluster(cluster, result);

        assertEquals("subgraph cluster1 {\nlabel = \"label\";\n}\n", result.toString());
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#emitNode(org.arachna.dot4j.model.Node)}
     * .
     */
    @Test
    public final void testEmitNodeWithAttribute() {
        final Node node = this.graph.newNode();
        final Attributes attributes = node.getAttributes();
        attributes.setAttribute(LABEL, LABEL);
        attributes.setAttribute("font", "Helvetica");

        final String result = this.generator.emitNode(node);

        assertEquals("node0 [ font=\"Helvetica\" label=\"label\"];\n", result);
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#emitAttributes(org.arachna.dot4j.model.Attributes)}
     * .
     */
    @Test
    public final void testEmitEmptyAttributes() {
        final Attributes attributes = new Attributes();

        final String result = this.generator.emitAttributes(attributes).toString();

        assertEquals("", result);
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#emitAttributes(org.arachna.dot4j.model.Attributes)}
     * .
     */
    @Test
    public final void testEmitNonEmptyAttribute() {
        final Attributes attributes = new Attributes();
        attributes.setAttribute(LABEL, LABEL);

        final String result = this.generator.emitAttributes(attributes).toString();

        assertEquals(" [ label=\"label\"]", result);
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.DotGenerator#emitAttributes(org.arachna.dot4j.model.Attributes)}
     * .
     */
    @Test
    public final void testEmitTwoNonEmptyAttributes() {
        final Attributes attributes = new Attributes();
        attributes.setAttribute(LABEL, LABEL);
        attributes.setAttribute("font", "Helvetica");

        final String result = this.generator.emitAttributes(attributes).toString();

        assertEquals(" [ font=\"Helvetica\" label=\"label\"]", result);
    }
}
