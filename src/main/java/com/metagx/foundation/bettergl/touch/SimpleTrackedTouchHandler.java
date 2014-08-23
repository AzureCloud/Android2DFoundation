package com.metagx.foundation.bettergl.touch;

import android.view.MotionEvent;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import com.metagx.foundation.bettergl.GLGame;

/**
 * Simple impl of AbstractTouchHandler for a glGame application with google analytics tracking.
 */
public class SimpleTrackedTouchHandler implements TouchHandler {
    private static final String TAG = SimpleTrackedTouchHandler.class.getSimpleName();

    protected Tracker tracker;
    protected int screenWidth, screenHeight, glWidth, glHeight;
    protected final GLGame glGame;

    public SimpleTrackedTouchHandler(
            GLGame glGame, int screenWidth, int screenHeight, int glWidth, int glHeight) {
        this.glGame = glGame;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.glWidth = glWidth;
        this.glHeight = glHeight;

        tracker = EasyTracker.getInstance(glGame);
    }

    @Override
    public void onFling(MotionEvent motionEvent, MotionEvent motionEvent2) {} //noop

    @Override
    public void onTap(MotionEvent motionEvent) {} //noop
}
