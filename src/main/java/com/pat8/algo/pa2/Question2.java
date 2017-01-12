/**
 * File Name    : Question2.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Dec 24, 2012      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pat8.algo.model.OrderedEdge;
import com.pat8.algo.model.UnionFind;
import com.pat8.algo.model.Vertex;
import com.pat8.algo.util.RadixBoy;

public class Question2 {
    private final List<OrderedEdge> edges;
    private final UnionFind<Vertex> clusters;
    private int weight;
    private int kValue;

    public Question2(final String fileName, final int weight) throws NumberFormatException, IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(fileName)));
            final String[] firstLine = in.readLine().split(" ");
            this.edges = new LinkedList<>();
            this.weight = weight;
            final Map<Integer, Vertex> vertices = new HashMap<>(Integer.parseInt(firstLine[0]), 1);
            String line = null;
            final List<String> file = new ArrayList<>(20000);
            while ((line = in.readLine()) != null) {
                file.add(line.replaceAll(" ", ""));
            }
            for (int i = 0; i < file.size(); i++) {
                final String vertex1 = file.get(i);
                final int one = RadixBoy.fromRadix(vertex1, 2).intValue();
                boolean excessEdgeAdded = false;
                for (int j = i + 1; j < file.size(); j++) {
                    final String vertex2 = file.get(j);
                    int hd = hammingDistance(vertex1, vertex2, weight);
                    if (hd == -1) {
                        if (excessEdgeAdded)
                            continue;
                        else {
                            excessEdgeAdded = true;
                            hd = Integer.MAX_VALUE;
                        }
                    }
                    final int two = RadixBoy.fromRadix(vertex2, 2).intValue();
                    // if(one == two)
                    // System.out.println("zero cost edge");

                    if (!vertices.containsKey(one)) {
                        vertices.put(one, new Vertex(one));
                    }
                    if (!vertices.containsKey(two)) {
                        vertices.put(two, new Vertex(two));
                    }
                    final Vertex v1 = vertices.get(one);
                    final Vertex v2 = vertices.get(two);
                    final OrderedEdge e = new OrderedEdge(v1, v2, hd);
                    final boolean b = this.edges.add(e);
                    if (!b)
                        System.out.println(e + " was not added");
                }
            }

            this.clusters = new UnionFind<Vertex>(vertices);
        } finally {
            in.close();
        }
    }

    public double compute() {
        double mstCost = 0.0;
        Collections.sort(this.edges);
        for (final OrderedEdge e : this.edges) {
            final Vertex p1 = this.clusters.findParent(e.getV1());
            final Vertex p2 = this.clusters.findParent(e.getV2());
            if (p1.equals(p2)) {
                continue;
            }
            if (e.getWeight() >= this.weight) {
                this.kValue = this.clusters.size();
                return mstCost;
            }
            this.clusters.union(p1, p2);
            mstCost += e.getWeight();
        }
        return mstCost;
    }

    public double getKValue() {
        return this.kValue;
    }

    private int hammingDistance(final String s1, final String s2, final int noMoreThan) {
        int counter = 0;
        for (int k = 0; k < s1.length(); ++k) {
            if (counter == noMoreThan) {
                counter = -1;
                break;
            }
            if (s1.charAt(k) != s2.charAt(k))
                counter++;
        }
        return counter;
    }
}

/*
 * class Vertex implements Comparable<Vertex> { private final Long v; public Vertex(Long id) { this.v = id; } public
 * long value() { return v; } public boolean equals(Object obj) { try{ Vertex o = (Vertex)obj; return o.v == this.v;
 * }catch(Exception x){ return false; } } public String toString() { return v+""; } public int hashCode() { return
 * v.hashCode(); } public int compareTo(Vertex other) { return Long.compare(v, other.v); } }
 */
