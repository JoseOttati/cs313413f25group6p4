package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

/**
 * The running state of the timer.
 * Timer is actively counting down.
 *
 * Behavior:
 * - Decrements time by 1 second on each tick
 * - Transitions to ALARM state when reaching zero
 * - Cancel button stops countdown and returns to STOPPED state
 *
 * Design Changes from Stopwatch to Timer:
 * - Stopwatch: incremented time (counted UP)
 * - Timer: decrements time (counts DOWN)
 * - Stopwatch: ran indefinitely
 * - Timer: stops at zero and triggers alarm
 */
class RunningState implements TimerState {

    public RunningState(final DefaultTimerStateMachine sm) {
        this.sm = sm;
    }

    private final DefaultTimerStateMachine sm;

    @Override
    public void onButtonPress() {
        sm.actionStop();
        sm.actionReset();
        sm.toStoppedState();
    }

    @Override
    public void onTick() {
        if (!sm.isAtZero()) {
            sm.actionDec();
        } else {
            sm.actionStop();
            sm.toAlarmState();
        }
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.RUNNING;
    }
    @Override
    public void onIncrementButton() {
        // Do nothing while running
    }

    @Override
    public void onStartCancelButton() {
        sm.actionStop();
        sm.actionReset();
        sm.toStoppedState();
    }
}