package com.pat8.algo.model;


public class OrderedEdge implements Comparable<OrderedEdge> {
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