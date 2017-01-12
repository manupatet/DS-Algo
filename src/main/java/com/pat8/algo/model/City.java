/**
 * File Name    : City.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Jan 26, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.model;

import java.util.LinkedList;
import java.util.List;

public class City implements Comparable<City>
{
    private final Double latitude, longitude;
    private int hash=0;
    private final List<Edge<City>> edges = new LinkedList<Edge<City>>();

    private final Integer PRIME_MULTIPLIER=31;
    public City(final double latitude, final double longitude)
    {
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public double getLatitude()
    {
        return this.latitude;
    }
    public double getLongitude()
    {
        return this.longitude;
    }
    public Double getEucledianDistance(final City c2){
        return Math.sqrt((this.latitude - c2.latitude)*(this.latitude - c2.latitude) +
                         (this.longitude - c2.longitude)*(this.longitude - c2.longitude));
    }
    @Override
    public boolean equals(final Object obj){
        try{
            final City o = (City)obj;
            return o.latitude.equals(this.latitude) && o.longitude.equals(this.longitude);
        }catch(final Exception x){
            return false;
        }
    }
    @Override
    public int hashCode()
    {
        if(this.hash==0){
            this.hash = this.PRIME_MULTIPLIER*this.PRIME_MULTIPLIER + this.latitude.intValue();
            this.hash = this.PRIME_MULTIPLIER*this.hash + this.longitude.intValue();
        }
        return this.hash;
    }
    @Override
    public String toString()
    {
        return "["+this.latitude+", "+this.longitude+"]";
    }
    public void addEdge(final Edge<City> e)
    {
        this.edges.add(e);
    }

    public Edge<City>[] getEdges()
    {
        return this.edges.toArray(new Edge[this.edges.size()]);
    }
    @Override
    public int compareTo(final City o)
    {
        int cmp = this.latitude.compareTo(o.latitude);
        if(cmp == 0){
            cmp = this.longitude.compareTo(o.longitude);
        }
        return cmp;
    }
}
