package com.metagx.foundation.graphics;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.MotionEvent;
import com.metagx.foundation.HUD.HUDPanel;
import com.metagx.foundation.sprite.Sprite;

public abstract class GameGraphics implements SensorEventListener {
	protected final List<Sprite> sprites;
	protected final List<HUDPanel> hudPanels;

    protected int screenWidth=0,screenHeight=0;

	public GameGraphics() {
        sprites = new ArrayList<Sprite>();
		hudPanels = new ArrayList<HUDPanel>();
	}
	
	public abstract boolean onTouchEvent(MotionEvent e);
	
	public abstract void draw(Canvas c, long gameTime);

    public void setNewSurfaceSize(Resources resrouces, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

	public synchronized void addGameObject(Sprite obj) {
        sprites.add(obj);
	}

	public synchronized void removeGameObject(Sprite obj) {
        sprites.remove(obj);
	}
	
	public synchronized void addHUDPanel(HUDPanel obj) {
		hudPanels.add(obj);
	}

	public synchronized void removeHUDPanel(HUDPanel obj) {
		hudPanels.remove(obj);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
	}
}
