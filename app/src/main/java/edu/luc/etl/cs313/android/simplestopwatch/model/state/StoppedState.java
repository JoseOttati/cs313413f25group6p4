package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

/**
 * The stopped state of the timer.
 * Initial state when app starts.
 *
 * Behavior:
 * - Time is at zero
 * - Increment button adds time and transitions to INCREMENTING state
 * - Start/Cancel button does nothing
 *
 * Design Changes from Stopwatch to Timer:
 * - Stopwatch: button started counting up from zero
 * - Timer: button increments time, then countdown starts later
 * - Timer requires separate INCREMENTING state
 */
class StoppedState implements TimerState {

    public StoppedState(final DefaultTimerStateMachine sm) {
        this.sm = sm;
    }

    private final DefaultTimerStateMachine sm;

    @Override
    public void onButtonPress() {
        sm.actionInc();
        sm.recordIncrementTime();
        sm.toIncrementingState();
    }

    @Override
    public void onTick() {
        // No action in stopped state
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.STOPPED;
    }
    @Override
    public void onIncrementButton() {
        sm.recordIncrementTime();
        sm.actionInc();
        sm.toIncrementingState();
    }

    @Override
    public void onStartCancelButton() {
        // Do nothing when stopped at zero
    }
}