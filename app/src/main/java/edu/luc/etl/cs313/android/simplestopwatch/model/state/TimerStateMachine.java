package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;

/**
 * The state machine for the timer.
 * This interface follows the Dependency Inversion Principle (DIP).
 *
 * Design Changes from Stopwatch to Timer:
 * - Added INCREMENTING state (allows setting time before countdown)
 * - Added ALARM state (triggered when countdown reaches zero)
 * - Split button handling: onIncrementButton() and onStartCancelButton()
 * - Added alarm control methods: startAlarm(), stopAlarm()
 * - Added timeout tracking: recordIncrementTime(), isTimeoutExpired()
 * - Added limit checking: isAtMaxValue(), isAtZero()
 *
 * State Transitions:
 * STOPPED → INCREMENTING (button press increments time)
 * INCREMENTING → RUNNING (3-sec timeout or max value reached)
 * RUNNING → ALARM (countdown reaches zero)
 * ALARM → STOPPED (button press dismisses alarm)
 * Any → STOPPED (cancel button during countdown)
 *
 * Architecture Pattern: State Pattern
 * - Each state encapsulates its own behavior
 * - State machine coordinates transitions
 * - Guards prevent invalid transitions
 */
public interface TimerStateMachine {
    void setModelListener(StopwatchModelListener listener);
    void onButtonPress();
    void onTick();
    void updateUIRuntime();

    // State transitions
    void toStoppedState();
    void toIncrementingState();
    void toRunningState();
    void toAlarmState();

    // Actions
    void actionInit();
    void actionReset();
    void actionStart();
    void actionStop();
    void actionInc();
    void actionDec();
    void actionUpdateView();
    void playBeep();
    void startAlarm();
    void stopAlarm();

    // Guards
    void recordIncrementTime();
    boolean isTimeoutExpired();
    boolean isAtMaxValue();
    boolean isAtZero();

    void onIncrementButton();
    void onStartCancelButton();
}