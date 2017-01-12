/**
 * File Name    : Variable.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Feb 2, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa6;

import java.util.LinkedList;
import java.util.List;

public class Variable
{
    private final Integer id;
    private final List<Variable> implicationsTo, implicationsFrom;
    private boolean seen = false;
    public Variable(final Integer id)
    {
        this.id= id;
        this.implicationsTo = new LinkedList<>();
        this.implicationsFrom = new LinkedList<>();
    }
    @Override
    public String toString()
    {
        return this.id.toString();
    }
    public void addImplicationTo(final Variable v)
    {
        this.implicationsTo.add(v);
    }
    public void addImplicationFrom(final Variable v)
    {
        this.implicationsFrom.add(v);
    }
    public List<Variable> getImplications(final boolean transpose)
    {
        return transpose? this.implicationsFrom: this.implicationsTo;
    }
    public void seen()
    {
        this.seen=true;
    }
    public boolean isSeen()
    {
        return this.seen;
    }
    public void reset()
    {
        this.seen=false;
    }
    @Override
    public int hashCode()
    {
        return Math.abs(this.id);
    }
    @Override
    public boolean equals(final Object obj)
    {
        try{
            final Variable other = (Variable)obj;
            return this.id.equals(other.id);// Math.abs(v.id) == Math.abs(this.id);
        }catch(final Exception x){
            return false;
        }
    }
    public int getId()
    {
        return this.id;
    }
}
