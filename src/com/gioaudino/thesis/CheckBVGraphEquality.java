package com.gioaudino.thesis;

import it.unimi.dsi.webgraph.ImmutableGraph;

import java.io.IOException;

public class CheckBVGraphEquality {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println(String.format("USAGE: java %s <graph 1> <graph 2>", CheckBVGraphEquality.class.getName()));
            System.exit(-1);
        }
        Logger.log(CheckBVGraphEquality.class, "Loading graph 1 <" + args[0] + ">");
        ImmutableGraph graph = ImmutableGraph.load(args[0]);
        Logger.log(CheckBVGraphEquality.class, "Loading graph 2 <" + args[1] + ">");
        ImmutableGraph graph2 = ImmutableGraph.load(args[1]);

        Logger.log(CheckBVGraphEquality.class, String.format("Graphs <%s> and <%s> are %s", args[0], args[1], graph.equals(graph2) ? "EQUAL" : "NOT EQUAL"));
    }
}
