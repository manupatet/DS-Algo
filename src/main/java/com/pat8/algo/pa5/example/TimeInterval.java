/**
 * File Name    : TimeInterval.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Feb 7, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa5.example;

class TimeInterval
{
    private long start, elapsed;

    public void startTiming()
    {
        this.start = System.currentTimeMillis()/1000;
    }

    public void endTiming()
    {
        this.elapsed = System.currentTimeMillis()/1000 - this.start;
    }

    public String getElapsedTime()
    {
        return this.elapsed + "";
    }
}