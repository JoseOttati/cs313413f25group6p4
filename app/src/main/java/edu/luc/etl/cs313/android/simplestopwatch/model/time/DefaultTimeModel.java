package edu.luc.etl.cs313.android.simplestopwatch.model.time;

/**
 * An implementation of the timer data model.
 *
 * Design Changes from Stopwatch to Timer:
 * - Added decRuntime(): Countdown functionality (stopwatch only counted up)
 * - incRuntime(): Now used for setting timer value, not elapsed time
 * - Runtime represents REMAINING time, not ELAPSED time
 *
 * Timer Semantics:
 * - Time starts at 0 (user hasn't set anything yet)
 * - User increments to desired countdown value (1-99 seconds)
 * - Timer decrements back to 0 during countdown
 * - Reaching 0 triggers alarm
 *
 * Contrast with Stopwatch:
 * - Stopwatch: started at 0, counted up indefinitely
 * - Timer: starts at 0, increments to desired value, counts down to 0
 */
public class DefaultTimeModel implements TimeModel {

    private int runningTime = 0;

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void incRuntime() {
        runningTime++;
    }

    @Override
    public void decRuntime() {
        if (runningTime > 0) {
            runningTime--;
        }
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }
}