package com.metagx.foundation.bettergl.touch;

import android.view.MotionEvent;

/**
 * Interface for defining handling touch events.
 */
public interface TouchHandler {

    void onFling(MotionEvent motionEvent, MotionEvent motionEvent2);

    void onTap(MotionEvent motionEvent);

}
