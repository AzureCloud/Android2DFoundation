package com.metagx.foundation.surface;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceThread extends Thread {
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

		@Override
		public void run() {
			while (myThreadRun) {
				Canvas c = null;
				try {
					c = myThreadSurfaceHolder.lockCanvas(null);
					synchronized (myThreadSurfaceHolder) {
						myThreadSurfaceView.draw(c);
					}
					SystemClock.sleep(16); // 60 fps
				} finally {
					if (c != null) {
						myThreadSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
}
