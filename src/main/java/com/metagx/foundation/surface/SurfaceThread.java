package com.metagx.foundation.surface;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceThread extends Thread {
    private static final String TAG = SurfaceThread.class.getSimpleName();

    private SurfaceHolder myThreadSurfaceHolder;
		private SurfaceView myThreadSurfaceView;
		private boolean myThreadRun = false;
		
		public SurfaceThread(SurfaceHolder surfaceHolder,
				SurfaceView surfaceView) {
			myThreadSurfaceHolder = surfaceHolder;
			myThreadSurfaceView = surfaceView;
		}

		public void setRunning(boolean b) {
			myThreadRun = b;
		}

        boolean sleep = false;

        public synchronized void goToSleep() {
            sleep = true;
        }

		@Override
		public void run() {
            long sleepTime = 0;
            int countMissedFrames = 0;
            while (myThreadRun) {
                synchronized (this) {
                    try {
                        if(sleep == true) {
                            this.wait();
                            sleep = false;
                        }
                    } catch (InterruptedException e) {
                        Log.d(TAG, "Interrupted while sleeping");
                    }
                }
				Canvas c = null;
				try {
					c = myThreadSurfaceHolder.lockCanvas(null);
                    sleepTime = SystemClock.uptimeMillis();
					synchronized (myThreadSurfaceHolder) {
						myThreadSurfaceView.draw(c);
					}
                    sleepTime = 10 - (SystemClock.uptimeMillis()-sleepTime); //Sleep for the remainder
					SystemClock.sleep(sleepTime > 0 ? sleepTime : 0); // `~60 fps
				} finally {
					if (c != null) {
						myThreadSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
}
