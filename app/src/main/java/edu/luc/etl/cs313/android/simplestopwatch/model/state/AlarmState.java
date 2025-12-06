package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

/**
 * The alarm state of the timer.
 * Entered when countdown reaches zero.
 *
 * Behavior:
 * - Alarm sound plays continuously
 * - Waits for user to press button to dismiss
 * - Any button press stops alarm and returns to STOPPED state
 *
 * Timer-Specific:
 * - This state didn't exist in stopwatch (no alarm feature)
 * - Alarm is started when ENTERING this state (in toAlarmState())
 * - This class just handles button press to dismiss
 */
class AlarmState implements TimerState {

    public AlarmState(final DefaultTimerStateMachine sm) {
        this.sm = sm;
    }

    private final DefaultTimerStateMachine sm;

    @Override
    public void onButtonPress() {
        sm.stopAlarm();
        sm.actionReset();
        sm.toStoppedState();
    }

    @Override
    public void onTick() {
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.ALARM;
    }
    @Override
    public void onIncrementButton() {
        // Do nothing during alarm
    }

    @Override
    public void onStartCancelButton() {
        sm.stopAlarm();
        sm.actionReset();
        sm.toStoppedState();
    }
}