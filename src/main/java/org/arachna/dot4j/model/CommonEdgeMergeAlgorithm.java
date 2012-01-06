/**
 * 
 */
package org.arachna.dot4j.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Algorithm to merge edges between groups that have the same target node. The
 * source nodes have to originate from the same sub graph (cluster).
 * 
 * @author Dirk Weigenand
 */
public class CommonEdgeMergeAlgorithm {
    /**
     * the graph for which edges having the same target node and the same
     * originating group should be merged.
     */
    private final Graph graph;

    /**
     * Create an instance of the algorithm using the given graph.
     * 
     * @param graph
     *            graph to improve through common edge merging.
     */
    public CommonEdgeMergeAlgorithm(final Graph graph) {
        this.graph = graph;
    }

    public void execute() {
        mergeEdgesWithSameTargetAndSameOriginatingGraph(collectEdgesToSameNode(this.graph));
    }

    /**
     * @param edges
     */
    protected Map<Graph, Map<Node, List<Edge>>> collectEdgesToSameNode(Graph graph) {
        Map<Graph, Map<Node, List<Edge>>> sourceGraphs = new HashMap<Graph, Map<Node, List<Edge>>>();
        Map<Node, List<Edge>> targetNodes;

        for (Edge edge : graph.getEdges()) {
            targetNodes = sourceGraphs.get(edge.getStartNode().getGraph());

            if (targetNodes == null) {
                targetNodes = new HashMap<Node, List<Edge>>();
                sourceGraphs.put(edge.getStartNode().getGraph(), targetNodes);
            }

            List<Edge> edges = targetNodes.get(edge.getEndNode());

            if (edges == null) {
                edges = new ArrayList<Edge>();
                targetNodes.put(edge.getEndNode(), edges);
            }

            edges.add(edge);
        }

        for (Graph cluster : graph.getClusters()) {
            sourceGraphs.putAll(collectEdgesToSameNode(cluster));
        }

        return sourceGraphs;
    }

    protected void mergeEdgesWithSameTargetAndSameOriginatingGraph(
        Map<Graph, Map<Node, List<Edge>>> originToMergableEdgeMapping) {
        for (Map.Entry<Graph, Map<Node, List<Edge>>> mappingEntry : originToMergableEdgeMapping.entrySet()) {
            Graph sourceGraph = mappingEntry.getKey();
            Map<Node, List<Edge>> mapping = mappingEntry.getValue();

            for (Map.Entry<Node, List<Edge>> entry : mapping.entrySet()) {
                List<Edge> edges = entry.getValue();

                if (edges.size() > 1) {
                    Node target = entry.getKey();
                    Node startNode = sourceGraph.newNode();
                    sourceGraph.newEdge(startNode, target);

                    for (Edge edge : edges) {
                        edge.setEndNode(startNode);
                    }
                }
            }
        }
    }
}
