package com.metagx.foundation.HUD;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.metagx.foundation.Sprite;

public abstract class HUDItem extends Sprite {

	public HUDItem() {
		super();
	}
	
	public HUDItem(int x, int y, Bitmap spriteSheet, int theFPS, float frameWidth, float frameHeight) {
		super(x,y,spriteSheet, theFPS, frameWidth, frameHeight);
	}
	
	public abstract void draw(Canvas c);
	
	public abstract void handleTouchEvent();
}
