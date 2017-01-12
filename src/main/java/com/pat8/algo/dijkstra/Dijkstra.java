/**
 * File Name    : Dijkstra.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Dec 17, 2012      manu          First Version
 *
 * Copyright PAT8 Inc.
 **/

package com.pat8.algo.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

class Vertex implements Comparable<Vertex> {
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(final String argName) {
        this.name = argName;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(final Vertex other) {
        return Double.compare(this.minDistance, other.minDistance);
    }
}

class Edge {
    public final Vertex target;
    public final double weight;

    public Edge(final Vertex argTarget, final double argWeight) {
        this.target = argTarget;
        this.weight = argWeight;
    }
}

public class Dijkstra {
    public static void computePaths(final Vertex source) {
        source.minDistance = 0.;
        final PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            final Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (final Edge e : u.adjacencies) {
                final Vertex v = e.target;
                final double weight = e.weight;
                final double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public static List<Vertex> getShortestPathTo(final Vertex target) {
        final List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(final String[] args) {
        final Vertex v0 = new Vertex("Redvile");
        final Vertex v1 = new Vertex("Blueville");
        final Vertex v2 = new Vertex("Greenville");
        final Vertex v3 = new Vertex("Orangeville");
        final Vertex v4 = new Vertex("Purpleville");

        v0.adjacencies = new Edge[] { new Edge(v1, 5), new Edge(v2, 10), new Edge(v3, 8) };
        v1.adjacencies = new Edge[] { new Edge(v0, 5), new Edge(v2, 3), new Edge(v4, 7) };
        v2.adjacencies = new Edge[] { new Edge(v0, 10), new Edge(v1, 3) };
        v3.adjacencies = new Edge[] { new Edge(v0, 8), new Edge(v4, 2) };
        v4.adjacencies = new Edge[] { new Edge(v1, 7), new Edge(v3, 2) };
        final Vertex[] vertices = { v0, v1, v2, v3, v4 };
        computePaths(v0);
        for (final Vertex v : vertices) {
            System.out.println("Distance to " + v + ": " + v.minDistance);
            final List<Vertex> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
    }
}
