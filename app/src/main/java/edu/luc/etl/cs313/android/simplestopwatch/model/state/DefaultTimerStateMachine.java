package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * An implementation of the timer state machine.
 * This uses the State pattern with dependency injection.
 *
 * Design Changes from Stopwatch to Timer:
 *
 * 1. State Addition:
 *    - INCREMENTING: User sets time before countdown starts
 *    - ALARM: Continuous alarm when timer expires
 *
 * 2. Guard Conditions:
 *    - isAtMaxValue(): Limits timer to 99 seconds
 *    - isAtZero(): Detects countdown completion
 *    - isTimeoutExpired(): Auto-starts after 3 seconds inactivity
 *
 * 3. Sound System:
 *    - playBeep(): User feedback during incrementing
 *    - startAlarm(): Triggered ONCE when entering ALARM state
 *    - stopAlarm(): Dismisses alarm
 *
 * 4. Button Behavior:
 *    - Context-sensitive (changes meaning per state)
 *    - Increment button: Adds time when stopped/incrementing
 *    - Start/Cancel button: Starts countdown or cancels operation
 *
 * Key Implementation Detail:
 * The alarm starts in toAlarmState(), NOT in AlarmState.onTick().
 * This ensures alarm plays exactly once when transitioning to alarm state,
 * not repeatedly on every timer tick.
 *
 * State Responsibilities:
 * - StoppedState: Initial state, increments time on button press
 * - IncrementingState: Allows more increments, auto-starts on timeout
 * - RunningState: Counts down, checks for zero, transitions to alarm
 * - AlarmState: Plays alarm, waits for user dismissal
 */
public class DefaultTimerStateMachine implements TimerStateMachine {

    public DefaultTimerStateMachine(final TimeModel timeModel, final ClockModel clockModel) {
        this.timeModel = timeModel;
        this.clockModel = clockModel;
    }

    private final TimeModel timeModel;
    private final ClockModel clockModel;

    // Constants
    private static final long TIMEOUT_MILLIS = 3000; // 3 seconds
    private static final int MAX_TIME = 99; // maximum timer value

    // Timer state
    private TimerState currentState;
    private StopwatchModelListener modelListener;
    private long lastIncrementTime = 0;

    // State instances (State pattern)
    private final TimerState STOPPED = new StoppedState(this);
    private final TimerState INCREMENTING = new IncrementingState(this);
    private final TimerState RUNNING = new RunningState(this);
    private final TimerState ALARM = new AlarmState(this);

    // Model access for states
    protected TimeModel getTimeModel() {
        return timeModel;
    }

    protected ClockModel getClockModel() {
        return clockModel;
    }

    protected void setState(final TimerState state) {
        this.currentState = state;
        if (modelListener != null) {
            modelListener.onStateUpdate(state.getId());
        }
    }

    @Override
    public void setModelListener(final StopwatchModelListener listener) {
        this.modelListener = listener;
    }

    // UI event handlers
    @Override
    public synchronized void onButtonPress() {
        currentState.onButtonPress();
    }
    @Override
    public synchronized void onIncrementButton() {
        currentState.onIncrementButton();
    }

    @Override
    public synchronized void onStartCancelButton() {
        currentState.onStartCancelButton();
    }
    @Override
    public synchronized void onTick() {
        currentState.onTick();
    }

    @Override
    public void updateUIRuntime() {
        if (modelListener != null) {
            modelListener.onTimeUpdate(timeModel.getRuntime());
        }
    }

    // State transitions
    @Override
    public void toStoppedState() {
        setState(STOPPED);
    }

    @Override
    public void toIncrementingState() {
        setState(INCREMENTING);
    }

    @Override
    public void toRunningState() {
        setState(RUNNING);
    }

    @Override
    public void toAlarmState() {
        setState(ALARM);
        startAlarm();
    }

    // Actions
    @Override
    public void actionInit() {
        toStoppedState();
        actionReset();
    }

    @Override
    public void actionReset() {
        timeModel.resetRuntime();
        actionUpdateView();
    }

    @Override
    public void actionStart() {
        clockModel.start();
    }

    @Override
    public void actionStop() {
        clockModel.stop();
    }

    @Override
    public void actionInc() {
        timeModel.incRuntime();
        actionUpdateView();
    }

    @Override
    public void actionDec() {
        timeModel.decRuntime();
        actionUpdateView();
    }

    @Override
    public void actionUpdateView() {
        currentState.updateView();
    }

    @Override
    public void playBeep() {
        if (modelListener != null) {
            modelListener.playBeep();
        }
    }

    @Override
    public void startAlarm() {
        if (modelListener != null) {
            modelListener.startAlarm();
        }
    }

    @Override
    public void stopAlarm() {
        if (modelListener != null) {
            modelListener.stopAlarm();
        }
    }


    // Guards
    @Override
    public void recordIncrementTime() {
        lastIncrementTime = System.currentTimeMillis();
    }

    @Override
    public boolean isTimeoutExpired() {
        return (System.currentTimeMillis() - lastIncrementTime) >= TIMEOUT_MILLIS;
    }

    @Override
    public boolean isAtMaxValue() {
        return timeModel.getRuntime() >= MAX_TIME;
    }

    @Override
    public boolean isAtZero() {
        return timeModel.getRuntime() == 0;
    }
}