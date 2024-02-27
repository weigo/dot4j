/**
 * 
 */
package org.arachna.dot4j.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

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
    @BeforeEach
    public void setUp() throws Exception {
        this.graph = new Graph();
        this.algorithm = new CommonEdgeMergeAlgorithm(graph);
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link org.arachna.dot4j.model.CommonEdgeMergeAlgorithm#execute()}.
     */
    @Test
    public final void testExecuteWithEmptyGraph() {
        assertThat(graph.getEdges(), empty());
        algorithm.execute();
        assertThat(graph.getEdges(), empty());
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
        assertThat(graph.getEdges(), hasItem(edge0));
        assertThat(graph.getEdges(), hasItem(edge1));
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
        assertThat(graph.getEdges(), hasItem(edge0));
        assertThat(graph.getEdges(), hasItem(edge1));
        assertThat(graph.getEdges(), hasSize(3));
    }
}
