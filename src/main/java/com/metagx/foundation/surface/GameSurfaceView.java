package com.metagx.foundation.surface;

import android.content.Context;
import com.metagx.foundation.graphics.GameGraphics;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements
	SurfaceHolder.Callback {

	final private SurfaceThread thread;
	final private GameGraphics graphics;
	private long gameTime = 0;
	
	public GameSurfaceView(Context context, GameGraphics graphics) {
		super(context);

		getHolder().addCallback(this);
		this.thread = new SurfaceThread(getHolder(), this);
		this.graphics = graphics;
	}

	@Override
	public void draw(Canvas c) {
		graphics.draw(c, gameTime);
	}

    public void onResume() {
        thread.setRunning(true);
        try {
            thread.start();
        }catch(Exception e) {

        }
    }

    public void onPause() {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
        onResume();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
        onPause();
	}

}
