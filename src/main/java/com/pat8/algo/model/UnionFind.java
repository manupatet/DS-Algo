/**
 * File Name    : UnionFind.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Dec 24, 2012      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class UnionFind<T extends Comparable<T>> {
    private final Map<T, Set<T>> clusters;

    public UnionFind(final Set<T> vertices) {
        this.clusters = new HashMap<>(vertices.size(), 1);
        for (final T v : vertices) {
            final Set<T> vCluster = new TreeSet<T>();
            vCluster.add(v);
            this.clusters.put(v, vCluster);
        }
    }

    public UnionFind(final Map<? extends Number, T> vertices) {
        this.clusters = new HashMap<>(vertices.size(), 1);
        for (final Number vId : vertices.keySet()) {
            final Set<T> vCluster = new TreeSet<T>();
            vCluster.add(vertices.get(vId));
            this.clusters.put(vertices.get(vId), vCluster);
        }
    }

    public int size() {
        return this.clusters.size();
    }

    public T findParent(final T vertex) {
        if (this.clusters.containsKey(vertex)) {
            return vertex;
        }
        for (final T v : this.clusters.keySet()) {
            if (this.clusters.get(v).contains(vertex)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Unknown vertex");
    }

    public void union(final T v1, final T v2) {
        if (v1.equals(v2)) {
            throw new IllegalArgumentException("Same clusters");
        }
        if (!this.clusters.containsKey(v1) || !this.clusters.containsKey(v2)) {
            throw new IllegalArgumentException("Unknown clusters");
        }
        if (v1.compareTo(v2) > 0) {
            this.clusters.get(v2).addAll(this.clusters.get(v1));
            this.clusters.remove(v1);
        } else {
            this.clusters.get(v1).addAll(this.clusters.get(v2));
            this.clusters.remove(v2);
        }
    }

    @Override
    public String toString() {
        return this.clusters.toString();
    }
}
