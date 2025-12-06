package edu.luc.etl.cs313.android.simplestopwatch.model;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.DefaultClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.TickListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultTimerStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.TimerStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.DefaultTimeModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * Facade that coordinates the timer model components.
 * Simplifies interaction between UI and model layers.
 *
 * Architecture:
 * - Facade pattern: provides simple interface to complex subsystem
 * - Wires together: ClockModel, TimeModel, TimerStateMachine
 * - Delegates UI events to state machine
 *
 * Components:
 * - ClockModel: generates tick events every second
 * - TimeModel: stores current time value
 * - TimerStateMachine: handles state logic and transitions
 *
 * Timer vs Stopwatch:
 * - Added onIncrementButton() and onStartCancelButton()
 * - Same architectural structure as stopwatch version
 */
public class ConcreteStopwatchModelFacade implements StopwatchModelFacade {

    private TimerStateMachine stateMachine;
    private ClockModel clockModel;
    private TimeModel timeModel;

    public ConcreteStopwatchModelFacade() {
        timeModel = new DefaultTimeModel();
        clockModel = new DefaultClockModel();
        stateMachine = new DefaultTimerStateMachine(timeModel, clockModel);
        clockModel.setTickListener(new TickListener() {
            @Override
            public void onTick() {
                stateMachine.onTick();
            }
        });
    }

    @Override
    public void onButtonPress() {
        stateMachine.onButtonPress();
    }

    @Override
    public void setModelListener(final StopwatchModelListener listener) {
        stateMachine.setModelListener(listener);
    }

    @Override
    public void start() {
        stateMachine.actionInit();
    }
    @Override
    public void onIncrementButton() {
        stateMachine.onIncrementButton();
    }

    @Override
    public void onStartCancelButton() {
        stateMachine.onStartCancelButton();
    }
}