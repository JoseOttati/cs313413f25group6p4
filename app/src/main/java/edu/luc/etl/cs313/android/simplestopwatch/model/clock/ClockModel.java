package edu.luc.etl.cs313.android.simplestopwatch.model.clock;

import edu.luc.etl.cs313.android.simplestopwatch.common.Startable;
import edu.luc.etl.cs313.android.simplestopwatch.common.Stoppable;

/**
 * The internal clock that generates periodic tick events.
 *
 * Architecture:
 * - Extends Startable and Stoppable interfaces
 * - Sends tick events every second to registered listeners
 * - Independent of timer logic - just provides timing signals
 *
 * Timer vs Stopwatch:
 * - No changes needed - clock behavior is the same for both
 * - Timer uses ticks to count DOWN, stopwatch used them to count UP
 */
public interface ClockModel extends Startable, Stoppable, TickSource {
    void start();
    void stop();
}
