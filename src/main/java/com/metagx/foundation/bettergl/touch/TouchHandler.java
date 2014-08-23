package com.metagx.foundation.bettergl.touch;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.metagx.foundation.bettergl.ui.UIPanels;

/**
 * Interface for defining handling touch events.
 */
public interface TouchHandler {

    void onFling(MotionEvent motionEvent, MotionEvent motionEvent2);

    void onTap(MotionEvent motionEvent);

    boolean onKeyEvent(KeyEvent keyEvent);

    void setUiPanels(UIPanels uiPanels);
}
