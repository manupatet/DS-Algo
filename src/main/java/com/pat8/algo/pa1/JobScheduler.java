/**
 * File Name    : JobScheduler.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Dec 12, 2012      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobScheduler {
    private final List<Job> jobs;
    private final boolean ratio;

    public JobScheduler(final boolean ratio, final String filename) throws IOException {
        final BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
        in.readLine();// Ignore first line
        this.ratio = ratio;
        this.jobs = new ArrayList<>();
        String line = null;
        while ((line = in.readLine()) != null) {
            final String[] arr = line.split(" ");
            this.jobs.add(new Job(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), ratio));
        }
        in.close();
    }

    public void compute() {
        final long start = System.currentTimeMillis();
        Collections.sort(this.jobs);// Timsort
        int totalDuration = 0;
        long weightedSum = 0;
        for (final Job j : this.jobs) {
            totalDuration += j.getDuration();
            weightedSum += totalDuration * j.getPriority();
            // System.out.println(j+" Weighted sum: "+weightedSum);
        }
        System.out.println("Final weighted sum " + (this.ratio ? "(ratio)" : "(diff)") + ": " + weightedSum + " ("
                + (System.currentTimeMillis() - start) + " ms)");
    }
}

class Job implements Comparable<Job> {
    private final double priority, duration;
    private final double rank;

    public Job(final double priority, final double duration, final boolean isRatio) {
        this.priority = priority;
        this.duration = duration;
        this.rank = isRatio ? priority / duration : priority - duration;
    }

    @Override
    public int compareTo(final Job o) {
        if (this.rank == o.rank) {
            if (this.priority == o.priority)
                return 0;
            else {
                return this.priority > o.priority ? -1 : 1;
            }
        }
        return this.rank > o.rank ? -1 : 1;
    }

    public double getDuration() {
        return this.duration;
    }

    public double getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        return this.priority + " " + this.duration + " [" + this.rank + "]";
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            final Job other = (Job) obj;
            return this.duration == other.duration && this.priority == other.priority;
        } catch (final Exception x) {
            return false;
        }
    }
}