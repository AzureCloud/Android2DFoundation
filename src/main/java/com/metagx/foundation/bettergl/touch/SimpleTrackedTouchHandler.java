package com.metagx.foundation.bettergl.touch;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import com.metagx.foundation.bettergl.GLGame;
import com.metagx.foundation.bettergl.ui.UIPanels;

/**
 * Simple impl of AbstractTouchHandler for a glGame application with google analytics tracking.
 */
public class SimpleTrackedTouchHandler implements TouchHandler {
    private static final String TAG = SimpleTrackedTouchHandler.class.getSimpleName();

    protected Tracker tracker;
    protected int screenWidth, screenHeight, glWidth, glHeight;
    protected final GLGame glGame;
    protected UIPanels uiPanels;

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
    public void onFling(MotionEvent motionEvent, MotionEvent motionEvent2) {} // noop

    @Override
    public void onTap(float x, float y) {} // noop

    @Override
    public void onTouchDown(float x, float y) {} // noop

    @Override
    public void onTouchUp(float x, float y) {} // noop

    @Override
    public boolean onKeyEvent(KeyEvent keyEvent) { return false; } // noop

    @Override
    public void setUiPanels(UIPanels uiPanels) {
        this.uiPanels = uiPanels;
    }

    @Override
    public void onDrag(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
        // noop
    }
}
