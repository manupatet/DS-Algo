package com.pat8.algo.model;

public class Item {
    private final int value, weight;

    public Item(final int value, final int weight) {
        this.value = value;
        this.weight = weight;
    }

    public int getValue() {
        return this.value;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            final Item other = (Item) obj;
            return other.value == this.value && other.weight == this.weight;
        } catch (final Exception x) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "[" + this.value + "," + this.weight + "]";
    }
}