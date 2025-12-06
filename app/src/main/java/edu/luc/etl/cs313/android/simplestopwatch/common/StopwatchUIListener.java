package edu.luc.etl.cs313.android.simplestopwatch.common;

/**
 * A listener for UI input events for the timer.
 * This interface follows the Dependency Inversion Principle (DIP).
 */
public interface StopwatchUIListener {
    void onButtonPress();
}