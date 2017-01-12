/**
 * File Name    : Clustering.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Dec 23, 2012      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.pat8.algo.model.Vertex;

public class KCluster {
    private final Set<OrderedEdge> edges;
    private final UnionFind clusters;
    private final int kValue;
    private double weight;

    public KCluster(final String fileName, final int kValue) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(fileName)));
            final String[] firstLine = in.readLine().split(" ");
            this.edges = new TreeSet<>();
            this.kValue = kValue;
            final Map<Integer, Vertex> vertices = new HashMap<>(Integer.parseInt(firstLine[0]), 1);
            String line = null;
            while ((line = in.readLine()) != null) {
                final String[] arr = line.split(" ");
                final int one = Integer.parseInt(arr[0]);
                final int two = Integer.parseInt(arr[1]);
                if (!vertices.containsKey(one)) {
                    vertices.put(one, new Vertex(one));
                }
                if (!vertices.containsKey(two)) {
                    vertices.put(two, new Vertex(two));
                }
                final Vertex v1 = vertices.get(one);
                final Vertex v2 = vertices.get(two);
                final Long weight = Long.parseLong(arr[2]);
                final OrderedEdge e = new OrderedEdge(v1, v2, weight);
                final boolean b = this.edges.add(e);
            }

            this.clusters = new UnionFind(vertices);
        } finally {
            in.close();
        }
    }

    public double compute() {
        double mstCost = 0.0;
        for (final OrderedEdge e : this.edges) {
            final Vertex p1 = this.clusters.findParent(e.getV1());
            final Vertex p2 = this.clusters.findParent(e.getV2());
            if (p1.equals(p2)) {
                continue;
            }
            if (this.clusters.size() == this.kValue) {
                this.weight = e.getWeight();
                return mstCost;
            }
            this.clusters.union(p1, p2);
            mstCost += e.getWeight();
        }
        return mstCost;
    }

    public double getSpacing() {
        return this.weight;
    }
}

class UnionFind {
    private final Map<Vertex, Set<Vertex>> clusters;

    public UnionFind(final Set<Vertex> vertices) {
        this.clusters = new HashMap<>(vertices.size(), 1);
        for (final Vertex v : vertices) {
            final Set<Vertex> vCluster = new TreeSet<Vertex>();
            vCluster.add(v);
            this.clusters.put(v, vCluster);
        }
    }

    public UnionFind(final Map<Integer, Vertex> vertices) {
        this.clusters = new HashMap<>(vertices.size(), 1);
        for (final Integer vId : vertices.keySet()) {
            final Set<Vertex> vCluster = new TreeSet<Vertex>();
            vCluster.add(vertices.get(vId));
            this.clusters.put(vertices.get(vId), vCluster);
        }
    }

    public int size() {
        return this.clusters.size();
    }

    public Vertex findParent(final Vertex vertex) {
        if (this.clusters.containsKey(vertex)) {
            return vertex;
        }
        for (final Vertex v : this.clusters.keySet()) {
            if (this.clusters.get(v).contains(vertex)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Unknown vertex");
    }

    public void union(final Vertex v1, final Vertex v2) {
        if (v1.equals(v2)) {
            throw new IllegalArgumentException("Same clusters");
        }
        if (!this.clusters.containsKey(v1) || !this.clusters.containsKey(v2)) {
            throw new IllegalArgumentException("Unknown clusters");
        }
        if (v1.value() > v2.value()) {
            this.clusters.get(v2).addAll(this.clusters.get(v1));
            this.clusters.remove(v1);
        } else {
            this.clusters.get(v1).addAll(this.clusters.get(v2));
            this.clusters.remove(v2);
        }
    }
}

class OrderedEdge implements Comparable<OrderedEdge> {
    private final Vertex v1, v2;
    private final long weight;

    public OrderedEdge(final Vertex v1, final Vertex v2, final long weight) {
        // v1===<wt>===v2 where v1 is always smaller
        if (v1.value() > v2.value()) {
            this.v1 = v2;
            this.v2 = v1;
        } else {
            this.v1 = v1;
            this.v2 = v2;
        }
        this.weight = weight;
    }

    public Vertex getV1() {
        return this.v1;
    }

    public Vertex getV2() {
        return this.v2;
    }

    public long getWeight() {
        return this.weight;
    }

    @Override
    public int compareTo(final OrderedEdge o) {
        int retVal = 0;
        if (o.weight == this.weight) {
            if (o.getV1().value() == this.getV1().value()) {
                if (o.getV2().value() == this.getV2().value()) {
                    return 0;
                } else {
                    retVal = o.getV2().value() > this.getV2().value() ? -1 : 1;
                }
            } else {
                retVal = o.getV1().value() > this.getV1().value() ? -1 : 1;
            }
        } else {
            retVal = o.weight > this.weight ? -1 : 1;
        }
        return retVal;
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            final OrderedEdge o = (OrderedEdge) obj;
            return o.getV1().value() == this.getV1().value() && o.getV2().value() > this.getV2().value()
                    && o.weight == this.weight;
        } catch (final Exception x) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.v1 + "==<" + this.weight + ">==" + this.v2;
    }
}