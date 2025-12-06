package edu.luc.etl.cs313.android.simplestopwatch.model.state;

/**
 * Interface for timer states.
 * Part of the State design pattern.
 *
 * Architecture:
 * - Each state implements this interface
 * - State machine delegates behavior to current state
 * - States handle button presses and tick events differently
 *
 * Design Changes from Stopwatch to Timer:
 * - Added onIncrementButton() for time setting
 * - Added onStartCancelButton() for context-sensitive button
 * - onButtonPress() retained for backward compatibility
 */
public interface TimerState {
    void onButtonPress();
    void onTick();
    void updateView();
    int getId();
    void onIncrementButton();
    void onStartCancelButton();
}