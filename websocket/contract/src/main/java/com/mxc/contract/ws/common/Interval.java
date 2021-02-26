package com.mxc.contract.ws.common;

/**
 * Interval
 */
public enum Interval {
    Min1(60000L),
    Min5(300000L),
    Min15(900000L),
    Min30(1800000L),
    Min60(3600000L),
    Hour4(14400000L),
    Hour8(28800000L),
    Day1(86400000L),
    Week1(604800000L),
    Month1(2592000000L);
    private long step;

    Interval(long step) {
        this.step = step;
    }

    public long getStep() {
        return this.step;
    }

    public static Interval parse(String interval) {
        try {
            return Interval.valueOf(interval);
        } catch (Exception e) {
            return null;
        }
    }

}
