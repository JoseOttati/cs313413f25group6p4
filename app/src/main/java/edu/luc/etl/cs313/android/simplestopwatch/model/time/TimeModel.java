package edu.luc.etl.cs313.android.simplestopwatch.model.time;

/**
 * The passive data model of the timer.
 * It does not emit any events.
 */
public interface TimeModel {
    void resetRuntime();
    void incRuntime();
    void decRuntime();
    int getRuntime();
}