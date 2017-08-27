package com.turastory.ontimerecorder;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Timer {

    public interface TimerCallback {
        void onTick();
    }

    private boolean isRunning;
    private long startTime;
    private long interval;
    private TimerCallback callback;
    private ScheduledThreadPoolExecutor executor;

    public Timer(TimerCallback callback) {
        this.callback = callback;
    }

    private Runnable runnable = () -> {
        interval += 1;

        if (callback != null)
            callback.onTick();
    };

    public void start(int period) {
        isRunning = true;
        startTime = System.currentTimeMillis();
        interval = 0;

        executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(runnable, 0, period, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getInterval() {
        return interval;
    }
}

