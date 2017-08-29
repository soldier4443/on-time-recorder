package com.turastory.ontimerecorder.bus;

/**
 * Created by soldi on 2017-08-29.
 */

public class Bus {
    private static final com.squareup.otto.Bus BUS = new com.squareup.otto.Bus();

    public static void register(Object object) {
        BUS.register(object);
    }

    public static void unregister(Object object) {
        BUS.unregister(object);
    }

    public static void post(Object object) {
        BUS.post(object);
    }
}
