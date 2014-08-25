package com.metagx.foundation.bettergl.touch;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.metagx.foundation.bettergl.ui.UIPanels;

/**
 * Interface for defining handling touch events.
 */
public interface TouchHandler {

    void onFling(MotionEvent motionEvent, MotionEvent motionEvent2);

    void onTap(float x, float y);

    void onTouchDown(float x, float y);

    void onTouchUp(float x, float y);

    boolean onKeyEvent(KeyEvent keyEvent);

    void setUiPanels(UIPanels uiPanels);

    void onDrag(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY);
}
