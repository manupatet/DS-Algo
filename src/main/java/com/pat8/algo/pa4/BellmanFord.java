/**
 * File Name    : BellmanFord.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Jan 19, 2013      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.pa4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.pat8.algo.model.DirectedEdge;
import com.pat8.algo.model.IndegreeVertex;

public class BellmanFord {
    private Map<Integer, IndegreeVertex> vertexMap;
    private Integer minValue = Integer.MAX_VALUE;

    public BellmanFord(final String graphFile) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(graphFile)));
            final String[] firstLine = in.readLine().split(" ");
            final int vertexCount = Integer.parseInt(firstLine[0]);
            final int edgeCount = Integer.parseInt(firstLine[1]);
            this.vertexMap = new LinkedHashMap<>(vertexCount, 1);
            System.out.println("File " + graphFile + ": Vertices " + vertexCount + ", Edges " + edgeCount);
            String line;
            while ((line = in.readLine()) != null) {
                final String[] split = line.split(" ");
                final Integer i1 = Integer.parseInt(split[0]);
                final Integer i2 = Integer.parseInt(split[1]);
                if (!this.vertexMap.containsKey(i1)) {
                    final IndegreeVertex v = new IndegreeVertex(i1);
                    // v.addInEdge(new DirectedEdge(v, v, 0));//a directed edge to itself with weight 0
                    this.vertexMap.put(i1, v);
                }
                if (!this.vertexMap.containsKey(i2)) {
                    final IndegreeVertex v = new IndegreeVertex(i2);
                    // v.addInEdge(new DirectedEdge(v, v, 0));//a directed edge to itself with weight 0
                    this.vertexMap.put(i2, v);
                }
                final IndegreeVertex v1 = this.vertexMap.get(i1);
                final IndegreeVertex v2 = this.vertexMap.get(i2);
                final DirectedEdge<IndegreeVertex> e = new DirectedEdge<IndegreeVertex>(v1, v2, Integer.parseInt(split[2]));
                v1.addOutEdge(e);
                v2.addInEdge(e);
            }
        } finally {
            in.close();
        }
    }

    public boolean compute() {
        final Integer cnt = this.vertexMap.size();
        Integer min = this.minValue;
        for (int i = 1; i <= cnt; i++) {
            final boolean b = compute(i);
            if (!b)
                return false;
            System.out.println("This graph has min weight " + this.minValue);
            min = Math.min(min, this.minValue);
        }
        return true;
    }

    public boolean compute(final Integer srcId) {
        final Integer infinity = 1000000;

        final Integer[][] arr = new Integer[2][this.vertexMap.size()];
        arr[0][srcId - 1] = 0;// src to itself is weight 0
        arr[1][srcId - 1] = 0;// src to itself is weight 0
        for (int i = 0; i < arr[0].length; i++) {
            if (i != srcId - 1)
                arr[0][i] = infinity;// infinity
        }

        for (int i = 1; i < arr[0].length; i++) {
            final Integer ref[] = arr[(i - 1) % 2];
            final Integer curr[] = arr[i % 2];
            for (int j = 0; j < ref.length; j++) {
                if (j + 1 == srcId)
                    continue;
                final IndegreeVertex v = this.vertexMap.get(j + 1);
                final DirectedEdge<IndegreeVertex>[] edgs = v.getInEdges();
                // Integer min = ref[j];
                Integer min = Math.min(ref[j], infinity);
                for (final DirectedEdge<IndegreeVertex> e : edgs) {
                    final Integer a = ref[e.getTail().getId() - 1];
                    // if(min != null && a != null && (a + e.getWeight() < min))
                    // min = a + e.getWeight();
                    min = Math.min(min, ref[e.getTail().getId() - 1] + e.getWeight());
                }
                curr[j] = min;
            }
        }
        for (int i = 0; i < arr[0].length; i++) {
            if (arr[1][i] != arr[0][i]) {
                this.minValue = infinity;
                return false;
            }
            // if(arr[1][i] != null && (arr[1][i]<minValue))
            // minValue = arr[1][i];
            this.minValue = Math.min(this.minValue, arr[1][i]);
        }
        return true;
    }

    public Integer getMinValue() {
        return this.minValue;
    }
}
