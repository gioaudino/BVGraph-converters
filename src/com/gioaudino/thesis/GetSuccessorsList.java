package com.gioaudino.thesis;

import it.unimi.dsi.webgraph.ImmutableGraph;
import it.unimi.dsi.webgraph.LazyIntIterator;
import it.unimi.dsi.webgraph.NodeIterator;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GetSuccessorsList {
    public static void main(String[] args) throws IOException {
        if (args.length < 2 || !args[1].matches("[0-9]+")) {
            System.err.println(String.format("USAGE: %s <basename> <node> [--porcelain]", GetSuccessorsList.class.getName()));
            System.exit(-1);
        }

        boolean porcelain = args.length == 3 && args[2].equals("--porcelain");
        if (!porcelain)
            log("Loading graph");

        ImmutableGraph graph = ImmutableGraph.load(args[0]);

        if (!porcelain)
            log("Graph loaded");

        NodeIterator iterator = graph.nodeIterator(Integer.parseInt(args[1]));
        int node = iterator.nextInt();

        if (!porcelain)
            log(String.format("NODE: %d - %d successors", node, iterator.outdegree()));

        LazyIntIterator successors = iterator.successors();
        int next;

        while ((next = successors.nextInt()) != -1)
            if (porcelain)
                System.out.print(next + ", ");
            else
                System.out.println(String.format("%d -> %d", Integer.parseInt(args[1]), next));

    }

    private static void log(String message) {
        System.out.println(String.format("%s\t%s: %s", getReadableInstant(), GetSuccessorsList.class.getSimpleName(), message));
    }

    private static String getReadableInstant() {
        Instant now = Instant.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_TIME.withZone(ZoneId.systemDefault()).withLocale(Locale.US);
        String time = dtf.format(now);
        try {
            return time.substring(0, time.indexOf('.') + 4);
        } catch (IndexOutOfBoundsException e) {
            return time;
        }
    }
}
