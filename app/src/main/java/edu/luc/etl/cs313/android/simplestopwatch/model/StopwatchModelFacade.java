package edu.luc.etl.cs313.android.simplestopwatch.model;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;

/**
 * Interface for the timer model facade.
 * Defines the contract between UI and model layers.
 *
 * Architecture:
 * - Facade pattern interface
 * - UI layer depends only on this interface, not implementations
 * - Supports dependency injection and testability
 *
 * Design Changes from Stopwatch to Timer:
 * - Added onIncrementButton() for setting time
 * - Added onStartCancelButton() for context-sensitive button
 * - Kept onButtonPress() for backward compatibility
 */
public interface StopwatchModelFacade {
    void onButtonPress();
    void setModelListener(StopwatchModelListener listener);
    void start();
    void onIncrementButton();
    void onStartCancelButton();
}