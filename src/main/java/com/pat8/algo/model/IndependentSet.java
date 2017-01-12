package com.pat8.algo.model;


public class IndependentSet {
    private final int index, weight;
    private int hash;

    public IndependentSet(final int i, final int w) {
        this.index = i;
        this.weight = w;
        this.hash = 0;
    }

    @Override
    public int hashCode() {
        if (this.hash == 0) {
            this.hash = 31 * 31 + this.index;
            this.hash = this.hash * 31 + this.weight;
        }
        return this.hash;
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            final IndependentSet other = (IndependentSet) obj;
            return other.index == this.index && other.weight == this.weight;
        } catch (final Exception x) {
            return false;
        }
    }

    public int getWeight() {
        return this.weight;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return "V(" + this.index + "," + this.weight + ")";
    }
}