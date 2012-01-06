/**
 * 
 */
package org.arachna.dot4j.model;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test case for {@link CommonEdgeMergeAlgorithm}.
 * 
 * @author Dirk Weigenand
 */
public class CommonEdgeMergeAlgorithmTest {
    /**
     * algorithm instance under test.
     */
    private CommonEdgeMergeAlgorithm algorithm;

    /**
     * graph instance to use throughout testing.
     */
    private Graph graph;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.graph = new Graph();
        this.algorithm = new CommonEdgeMergeAlgorithm(graph);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.model.CommonEdgeMergeAlgorithm#execute()}.
     */
    @Test
    public final void testExecuteWithEmptyGraph() {
        assertThat(graph.getEdges(), (Matcher)empty());
        algorithm.execute();
        assertThat(graph.getEdges(), (Matcher)empty());
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.model.CommonEdgeMergeAlgorithm#execute()}.
     */
    @Test
    public final void testExecuteWithGraphContainingOneEdge() {
        Edge edge = graph.newEdge(graph.newNode(), graph.newNode());
        algorithm.execute();
        assertThat(graph.getEdges(), hasItem(edge));
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.model.CommonEdgeMergeAlgorithm#execute()}.
     */
    @Test
    public final void testExecuteWithGraphContainingTwoUnrelatedEdges() {
        Edge edge0 = graph.newEdge(graph.newNode(), graph.newNode());
        Edge edge1 = graph.newEdge(graph.newNode(), graph.newNode());
        algorithm.execute();
        assertThat(graph.getEdges(), (Matcher)hasItem(edge0));
        assertThat(graph.getEdges(), (Matcher)hasItem(edge1));
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.model.CommonEdgeMergeAlgorithm#execute()}.
     */
    @Test
    public final void testExecuteWithGraphContainingTwoRelatedEdges() {
        Node target = graph.newGraph().newNode();
        Edge edge0 = graph.newEdge(graph.newNode(), target);
        Edge edge1 = graph.newEdge(graph.newNode(), target);
        algorithm.execute();
        assertThat(graph.getEdges(), (Matcher)hasItem(edge0));
        assertThat(graph.getEdges(), (Matcher)hasItem(edge1));
        assertThat(graph.getEdges(), hasSize(3));
    }
}
