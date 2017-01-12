/**
 * File Name    : Edge.java
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

public class Edge<T> implements Comparable<Edge<T>> {
    private final T target;
    private final double weight;

    public Edge(final T argTarget, final double argWeight) {
        this.target = argTarget;
        this.weight = argWeight;
    }

    public T getTarget() {
        return this.target;
    }

    public double getWeight() {
        return this.weight;
    }

    @Override
    public int compareTo(final Edge<T> other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return "==" + this.weight + "=>" + this.target;
    }
}