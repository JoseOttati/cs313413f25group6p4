package edu.luc.etl.cs313.android.simplestopwatch.common;

/**
 * Constants for the timer calculations.
 *
 * Design Changes from Stopwatch to Timer:
 * - Added MAX_TIMER_VALUE: Limits timer to 99 seconds (countdown constraint)
 * - Added INCREMENT_TIMEOUT_MS: 3-second inactivity period before auto-start
 *
 * Implementation Notes:
 * - Timer counts DOWN from user-set value to zero (vs stopwatch counting UP)
 * - Timer triggers alarm when reaching zero
 * - Timer has maximum limit (99 seconds) unlike unlimited stopwatch
 */
public enum Constants {

    ;

    public static final int SEC_PER_TICK = 1;
    public static final int SEC_PER_MIN = 60;
    public static final int SEC_PER_HOUR = 3600;
    public static final int MAX_TIMER_VALUE = 99;
    public static final int INCREMENT_TIMEOUT_MS = 3000;


}