package edu.luc.etl.cs313.android.simplestopwatch.model.clock;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Implementation of the internal clock using Java Timer.
 *
 * Architecture:
 * - Uses java.util.Timer for periodic task execution
 * - Notifies registered TickListener every second
 * - Runs on separate thread, independent of UI
 *
 * Timer vs Stopwatch:
 * - Difference is how ticks are used (count up vs count down)
 *
 * Threading:
 * - Timer runs on background thread
 * - State machine must handle thread-safe updates
 */
public class DefaultClockModel implements ClockModel {

    private Timer timer;
    private TickListener tickListener;

    @Override
    public void setTickListener(final TickListener listener) {
        this.tickListener = listener;
    }

    @Override
    public void start() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (tickListener != null) {
                    tickListener.onTick();
                }
            }
        }, 0, 1000);
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}