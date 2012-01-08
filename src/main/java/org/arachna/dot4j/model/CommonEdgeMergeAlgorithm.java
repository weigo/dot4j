/**
 * 
 */
package org.arachna.dot4j.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
        mergeEdgesWithSameTargetAndSameOriginatingGraph(collectEdgesToSameNode(graph));

        final Map<Node, Collection<Edge>> mergableEdges = new HashMap<Node, Collection<Edge>>();

        for (final Map<Node, Collection<Edge>> mapping : collectEdgesToSameNode(graph).values()) {
            for (final Map.Entry<Node, Collection<Edge>> entry : mapping.entrySet()) {
                final Node sourceNode = entry.getKey();
                final Collection<Edge> edges = entry.getValue();

                if (mergableEdges.containsKey(sourceNode)) {
                    mergableEdges.get(sourceNode).addAll(edges);
                }
                else {
                    mergableEdges.put(sourceNode, edges);
                }
            }
        }

        mergeEdgesWithSameTargetAndDifferingOriginatingGrap(mergableEdges);
    }

    private void mergeEdgesWithSameTargetAndDifferingOriginatingGrap(final Map<Node, Collection<Edge>> mergableEdges) {
        for (final Map.Entry<Node, Collection<Edge>> entry : mergableEdges.entrySet()) {
            final Collection<Edge> edges = entry.getValue();

            if (edges.size() > 1) {
                final Node target = entry.getKey();
                final Graph parentGraph = graph.newGraph();
                parentGraph.getAttributes().setAttribute("style", "invis");
                final Node intermediateTarget = parentGraph.newNode();
                intermediateTarget.getAttributes().setAttribute("shape", "none");
                intermediateTarget.getAttributes().setAttribute("label", "");
                intermediateTarget.getAttributes().setAttribute("height", "0");
                intermediateTarget.getAttributes().setAttribute("width", "0");

                parentGraph.newEdge(intermediateTarget, target);

                for (final Edge edge : edges) {
                    edge.setEndNode(intermediateTarget);
                }
            }
        }
    }

    /**
     * @param edges
     */
    protected Map<Graph, Map<Node, Collection<Edge>>> collectEdgesToSameNode(final Graph graph) {
        final Map<Graph, Map<Node, Collection<Edge>>> sourceGraphs = new HashMap<Graph, Map<Node, Collection<Edge>>>();
        Map<Node, Collection<Edge>> targetNodes;

        for (final Edge edge : graph.getEdges()) {
            final Graph sourceGraph = edge.getStartNode().getGraph();

            // skip edges in same graph.
            if (sourceGraph.equals(edge.getEndNode().getGraph())) {
                continue;
            }

            targetNodes = sourceGraphs.get(sourceGraph);

            if (targetNodes == null) {
                targetNodes = new HashMap<Node, Collection<Edge>>();
                sourceGraphs.put(sourceGraph, targetNodes);
            }

            Collection<Edge> edges = targetNodes.get(edge.getEndNode());

            if (edges == null) {
                edges = new HashSet<Edge>();
                targetNodes.put(edge.getEndNode(), edges);
            }

            edges.add(edge);
        }

        for (final Graph cluster : graph.getClusters()) {
            for (final Map.Entry<Graph, Map<Node, Collection<Edge>>> entry : collectEdgesToSameNode(cluster).entrySet()) {
                final Map<Node, Collection<Edge>> nodeEdgeMapping = sourceGraphs.get(entry.getKey());

                if (nodeEdgeMapping == null) {
                    sourceGraphs.put(entry.getKey(), entry.getValue());
                }
                else {
                    for (final Map.Entry<Node, Collection<Edge>> mapping : entry.getValue().entrySet()) {
                        final Collection<Edge> edges = nodeEdgeMapping.get(mapping.getKey());

                        if (edges == null) {
                            nodeEdgeMapping.put(mapping.getKey(), mapping.getValue());
                        }
                        else {
                            edges.addAll(mapping.getValue());
                        }
                    }
                }

            }
        }

        return sourceGraphs;
    }

    protected Map<Node, Collection<Edge>> mergeEdgesWithSameTargetAndSameOriginatingGraph(
        final Map<Graph, Map<Node, Collection<Edge>>> originToMergableEdgeMapping) {
        final Map<Node, Collection<Edge>> mergedEdges = new HashMap<Node, Collection<Edge>>();

        for (final Map.Entry<Graph, Map<Node, Collection<Edge>>> mappingEntry : originToMergableEdgeMapping.entrySet()) {
            final Graph sourceGraph = mappingEntry.getKey();
            final Map<Node, Collection<Edge>> mapping = mappingEntry.getValue();

            for (final Map.Entry<Node, Collection<Edge>> entry : mapping.entrySet()) {
                final Collection<Edge> edges = entry.getValue();

                if (edges.size() > 1) {
                    final Node target = entry.getKey();
                    Collection<Edge> newEdges = mergedEdges.get(target);

                    if (newEdges == null) {
                        newEdges = new HashSet<Edge>();
                        mergedEdges.put(target, newEdges);
                    }

                    final Node startNode = sourceGraph.newNode();
                    startNode.getAttributes().setAttribute("shape", "none");
                    startNode.getAttributes().setAttribute("label", "");
                    startNode.getAttributes().setAttribute("height", "0");
                    startNode.getAttributes().setAttribute("width", "0");
                    newEdges.add(graph.newEdge(startNode, target));

                    for (final Edge edge : edges) {
                        edge.setEndNode(startNode);
                    }
                }
            }
        }

        return mergedEdges;
    }
}
