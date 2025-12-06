package edu.luc.etl.cs313.android.simplestopwatch.android;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.ConcreteStopwatchModelFacade;
import edu.luc.etl.cs313.android.simplestopwatch.model.StopwatchModelFacade;

/**
 * Android Activity adapter for the timer application.
 * Follows the Model-View-Controller pattern.
 *
 * Design Changes from Stopwatch to Timer:
 * - Single multi-function button changes behavior based on state
 * - Displays only seconds (00-99) instead of minutes:seconds
 * - Implements audio feedback (beep) and alarm system
 * - Button label changes dynamically (START/CANCEL)
 *
 * Responsibilities:
 * - Bridges Android UI events to model
 * - Updates UI based on model state changes
 * - Manages MediaPlayer resources for sounds
 * - Schedules all UI updates on main thread
 *
 * State-based Button Behavior:
 * - STOPPED: Button increments time
 * - INCREMENTING: Button increments time
 * - RUNNING: Button cancels and resets timer
 * - ALARM: Button stops alarm and resets
 */
public class TimerAdapter extends Activity implements StopwatchModelListener {

    private static final String TAG = "timer-android-activity";

    /**
     * The state-based dynamic model.
     */
    private StopwatchModelFacade model;

    /**
     * MediaPlayer for beep and alarm sounds.
     */
    private MediaPlayer beepPlayer;
    /**
     * MediaPlayer for alarm sound (continuous notification).
     * Created once in onCreate(), reused throughout lifecycle.
     * Configured to loop until explicitly stopped.
     */
    private MediaPlayer alarmPlayer;

    protected void setModel(final StopwatchModelFacade model) {
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject dependency on view so this adapter receives UI events
        setContentView(R.layout.activity_main);

        // Inject dependency on model into this so model receives UI events
        this.setModel(new ConcreteStopwatchModelFacade());

        // Inject dependency on this into model to register for UI updates
        model.setModelListener(this);

        // Initialize alarm player (beep player created on-demand)
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        alarmPlayer = MediaPlayer.create(getApplicationContext(), notification);
        if (alarmPlayer != null) {
            alarmPlayer.setLooping(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAlarm();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (beepPlayer != null) {
            beepPlayer.release();
            beepPlayer = null;
        }
        if (alarmPlayer != null) {
            alarmPlayer.release();
            alarmPlayer = null;
        }
    }

    /**
     * Updates the time display in the UI.
     * Shows only seconds (00-99) for the timer.
     *
     * @param time the remaining time in seconds
     */
    @Override
    public void onTimeUpdate(final int time) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView tvS = findViewById(R.id.seconds);
            final var locale = Locale.getDefault();
            tvS.setText(String.format(locale, "%02d", time));
        });
    }

    /**
     * Updates the state name in the UI.
     *
     * @param stateId the resource ID of the state name string
     */
    @Override
    public void onStateUpdate(final int stateId) {
        runOnUiThread(() -> {
            final TextView stateName = findViewById(R.id.stateName);
            stateName.setText(getString(stateId));

            // Update start/cancel button text based on state
            final android.widget.Button startCancelButton = findViewById(R.id.startStopButton);
            if (startCancelButton != null) {
                if (stateId == R.string.RUNNING || stateId == R.string.ALARM) {
                    startCancelButton.setText(R.string.cancel);
                } else {
                    startCancelButton.setText(R.string.start);
                }
            }
        });
    }
    /**
     * Handles the single multi-function button press.
     * Button behavior changes based on timer state:
     * - Stopped: increments time
     * - Incrementing: increments time
     * - Running: cancels timer and resets to zero
     * - Alarm: stops alarm and resets to zero
     *
     * @param view the button view
     */
    public void onIncrementButton(final View view) {
        model.onIncrementButton();
    }
    /**
     * Handles the start/cancel button click.
     * Button function changes based on current state.
     *
     * @param view the button view
     */
    public void onStartCancelButton(final View view) {
        model.onStartCancelButton();
    }

    /**
     * Plays a single beep sound.
     * Called when timer starts running after incrementing phase.
     */
    @Override
    public void playBeep() {
        runOnUiThread(() -> {
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                MediaPlayer player = MediaPlayer.create(getApplicationContext(), notification);
                if (player != null) {
                    player.setOnCompletionListener(mp -> mp.release());
                    player.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Starts the continuous alarm sound.
     * Called when timer reaches zero.
     */
    @Override
    public void startAlarm() {
        runOnUiThread(() -> {
            if (alarmPlayer != null && !alarmPlayer.isPlaying()) {
                try {
                    alarmPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Stops the alarm sound.
     * Called when button is pressed during alarm state.
     */
    @Override
    public void stopAlarm() {
        runOnUiThread(() -> {
            if (alarmPlayer != null && alarmPlayer.isPlaying()) {
                try {
                    alarmPlayer.pause();
                    alarmPlayer.seekTo(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}