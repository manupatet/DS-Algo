/**
 * File Name    : DirectedEdge.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Jan 19, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.model;

public class DirectedEdge<T>{
    private final T tail, head;
    private final Integer weight;
    public DirectedEdge(final T tail, final T head, final Integer weight)
    {
        this.head = head;
        this.weight = weight;
        this.tail = tail;
    }
    public T getTail()
    {
        return this.tail;
    }
    public T getHead()
    {
        return this.head;
    }
    public Integer getWeight()
    {
        return this.weight;
    }
    public int compareTo(final DirectedEdge<T> other)
    {
        return Integer.compare(this.weight, other.weight);
    }
    @Override
    public String toString()
    {
        return this.tail+"=="+this.weight+"=>"+this.head;
    }
}
