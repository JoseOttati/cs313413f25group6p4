package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

/**
 * The incrementing state of the timer.
 * User is setting the countdown time.
 *
 * Behavior:
 * - Each button press increments time by 1 second (with beep)
 * - Auto-starts countdown after 3 seconds of inactivity
 * - Auto-starts immediately if max value (99 seconds) reached
 *
 * Architecture:
 * - onTick() checks timeout and max value conditions
 * - Transitions to RUNNING state when conditions met
 *
 * Timer-Specific:
 * - New state that didn't exist in stopwatch
 * - Stopwatch started at zero and counted up immediately
 * - Timer needs this state to let user set the time first
 */
class IncrementingState implements TimerState {

    public IncrementingState(final DefaultTimerStateMachine sm) {
        this.sm = sm;
    }

    private final DefaultTimerStateMachine sm;

    @Override
    public void onButtonPress() {
        if (!sm.isAtMaxValue()) {
            sm.actionInc();
            sm.recordIncrementTime();
        }
    }

    @Override
    public void onTick() {
        if (sm.isAtMaxValue() || sm.isTimeoutExpired()) {
            sm.playBeep();
            sm.actionStart();
            sm.toRunningState();
        }
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.INCREMENTING;
    }
    @Override
    public void onIncrementButton() {
        sm.recordIncrementTime();
        sm.actionInc();
    }

    @Override
    public void onStartCancelButton() {
        sm.toRunningState();
        sm.actionStart();
    }
}