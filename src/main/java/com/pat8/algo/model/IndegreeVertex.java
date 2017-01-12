/**
 * File Name    : IndegreeVertex.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Jan 20, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.model;

import java.util.LinkedList;
import java.util.List;

public class IndegreeVertex {
    private final Integer v;
    private final List<DirectedEdge> inEdges = new LinkedList<>();
    private final List<DirectedEdge> outEdges = new LinkedList<>();

    public IndegreeVertex(final Integer id) {
        this.v = id;
    }

    public int value() {
        return this.v;
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            final IndegreeVertex o = (IndegreeVertex) obj;
            return this.v.equals(o.v);
        } catch (final Exception x) {
            return false;
        }
    }

    public Integer getId() {
        return this.v;
    }

    @Override
    public String toString() {
        return this.v + "";
    }

    @Override
    public int hashCode() {
        return this.v;
    }

    public int compareTo(final IndegreeVertex other) {
        return Integer.compare(this.v, other.v);
    }

    public void addInEdge(final DirectedEdge e) {
        this.inEdges.add(e);
    }

    public void addOutEdge(final DirectedEdge e) {
        this.outEdges.add(e);
    }

    public DirectedEdge[] getInEdges() {
        return this.inEdges.toArray(new DirectedEdge[this.inEdges.size()]);
    }

    public DirectedEdge[] getOutEdges() {
        return this.outEdges.toArray(new DirectedEdge[this.outEdges.size()]);
    }
}
