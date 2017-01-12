/**
 * File Name    : PrimsAlgo.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Dec 13, 2012      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pat8.algo.model.Edge;
import com.pat8.algo.model.Vertex;

public class PrimsAlgo {
    private final Map<Integer, Vertex> graph;

    public PrimsAlgo(final String fileName) throws IOException {
        final BufferedReader in = new BufferedReader(new FileReader(new File(fileName)));
        final String[] firstLine = in.readLine().split(" ");
        this.graph = new HashMap<>(Integer.parseInt(firstLine[0]), 1);
        String line = null;
        while ((line = in.readLine()) != null) {
            final String[] arr = line.split(" ");
            final Integer v1 = Integer.parseInt(arr[0]);
            final Integer v2 = Integer.parseInt(arr[1]);
            final Double edgeWeight = Double.parseDouble(arr[2]);
            if (!this.graph.containsKey(v1)) {
                this.graph.put(v1, new Vertex(v1));
            }
            if (!this.graph.containsKey(v2)) {
                this.graph.put(v2, new Vertex(v2));
            }
            final Vertex vtx1 = this.graph.get(v1);
            final Vertex vtx2 = this.graph.get(v2);
            vtx1.addEdge(new Edge<Vertex>(vtx2, edgeWeight));
            vtx2.addEdge(new Edge<Vertex>(vtx1, edgeWeight));
        }
        in.close();
    }

    public Double compute() {
        final int count = this.graph.size();
        final int wt = this.graph.keySet().iterator().next();
        final Vertex vOne = this.graph.get(wt);

        final Set<Vertex> primst = new HashSet<>();
        primst.add(vOne);

        double mstCost = 0.0;
        for (int i = 0; i < count; i++) {
            final Edge<Vertex> minEdge = getMinUnvisitedEdge(primst);
            if (minEdge == null) {
                continue;
            }
            final Vertex v = minEdge.getTarget();
            mstCost += minEdge.getWeight();
            primst.add(v);
        }
        return mstCost;
    }

    private Edge<Vertex> getMinUnvisitedEdge(final Set<Vertex> visited) {
        final List<Edge<Vertex>> allEdges = new ArrayList<>();
        for (final Vertex v : visited) {
            final Edge<Vertex>[] edges = v.getEdges();
            allEdges.addAll(Arrays.asList(edges));
        }

        Edge<Vertex> e = null;
        Collections.sort(allEdges);
        for (final Edge<Vertex> edg : allEdges) {
            if (visited.contains(edg.getTarget()))
                continue;
            e = edg;
            break;
        }
        return e;
    }
}