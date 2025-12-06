package edu.luc.etl.cs313.android.simplestopwatch.common;

/**
 * A listener for timer model events.
 * This interface follows the Dependency Inversion Principle (DIP).
 *
 * Design Changes from Stopwatch to Timer:
 * - Added playBeep(): Short audio feedback during time increment
 * - Added startAlarm(): Continuous alarm when timer reaches zero
 * - Added stopAlarm(): Stop alarm when user dismisses it
 *
 * Architecture:
 * - Model layer invokes these methods
 * - UI/Android layer (TimerAdapter) implements sound playback
 * - Keeps model independent of Android-specific code
 *
 */
public interface StopwatchModelListener {
    /**
     * Updates the displayed time value.
     * For timer: shows remaining seconds (00-99)
     *
     * @param timeValue the current time in seconds
     */
    void onTimeUpdate(int timeValue);
    /**
     * Updates the displayed state name.
     * Timer states: STOPPED, INCREMENTING, RUNNING, ALARM
     *
     * @param stateId the resource ID of the state name string
     */
    void onStateUpdate(int stateId);
    /**
     * Plays a short beep sound for user feedback.
     * Called when:
     * - User increments time
     * - Auto-transitioning from INCREMENTING to RUNNING state
     */
    void playBeep();
    /**
     * Starts playing the continuous alarm sound.
     * Called once when timer reaches zero and enters ALARM state.
     */
    void startAlarm();
    void stopAlarm();
}