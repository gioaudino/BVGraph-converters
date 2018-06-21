package com.gioaudino.thesis;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

class Logger extends Thread {
    private volatile boolean stop = false;
    private BVGraphConverter instance;

    Logger(BVGraphConverter instance) {
        this.instance = instance;
        log(instance, String.format("STARTING CONVERSION - %d nodes - %d arcs to convert", instance.nodes, instance.arcs));
    }

    static void log(Class caller, String message) {
        log(caller.getName(), message);
    }

    static void log(Object caller, String message) {
        log(caller.getClass().getName(), message, "info");
    }

    private static void log(String className, String message, String type) {
        System.out.println(String.format("%s\t%s %s: %s", getReadableInstant(), className, type.toUpperCase(), message));
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

    static String getETA(long printedArcs, long arcs, Date start) {
        if (arcs == printedArcs || printedArcs == 0) return "";
        Date now = new Date();
        long diff = (now.getTime() - start.getTime()) / 1000;
        long eta = diff * arcs / printedArcs;
        int hours = (int) eta / 3600;
        int min = (int) (eta % 3600) / 60;
        if (hours == 0 && min == 0) return "< 1 min";

        return (hours > 0 ? hours + "h " : "") + min + " min";
    }

    @Override
    public void run() {
        while (!stop) {
            instance.logProgress();
            try {
                if (!stop)
                    sleep(5000);
            } catch (InterruptedException ignored) {

            }
        }
    }

    void stopMe() {
        this.stop = true;
    }
}
