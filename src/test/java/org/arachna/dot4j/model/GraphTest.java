/**
 *
 */
package org.arachna.dot4j.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Dirk Weigenand
 * 
 */
public class GraphTest {
    /**
     * instance under test.
     */
    private Graph graph;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        graph = new Graph();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        graph = null;
    }

    @Test
    public void testANewGraphHasNoClusters() {
        assertThat(graph.getClusters(), Matchers.<Graph> empty());
    }

    @Test
    public void testANewGraphHasNoAttributes() {
        assertThat(graph.getAttributes().isEmpty(), equalTo(true));
    }

    @Test
    public void testANewGraphHasNoCommonNodeAttributes() {
        assertThat(graph.getNodeAttributes().isEmpty(), equalTo(true));
    }

    @Test
    public void testANewGraphHasNoCommonEdgeAttributes() {
        assertThat(graph.getEdgeAttributes().isEmpty(), equalTo(true));
    }
}
