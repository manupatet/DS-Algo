/**
 * File Name    : Vertex.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Dec 23, 2012      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.model;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {
    private final Integer v;
    private final List<Edge<Vertex>> edges = new ArrayList<>();

    public Vertex(final Integer id) {
        this.v = id;
    }

    public int value() {
        return this.v;
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            final Vertex o = (Vertex) obj;
            return this.v.equals(o.v);
        } catch (final Exception x) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.v + "";
    }

    @Override
    public int hashCode() {
        return this.v;
    }

    @Override
    public int compareTo(final Vertex other) {
        return Integer.compare(this.v, other.v);
    }

    public void addEdge(final Edge<Vertex> e) {
        this.edges.add(e);
    }

    public Edge<Vertex>[] getEdges() {
        return this.edges.toArray(new Edge[this.edges.size()]);
    }
}