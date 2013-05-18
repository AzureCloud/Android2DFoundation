package com.metagx.foundation.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for making a Sprite
 */
public abstract class Sprite {
    private static final String TAG = Sprite.class.getSimpleName();

	protected Bitmap sprite;
	protected float xPos, yPos;
    protected int fps, currentFrame;
	protected float spriteHeight, spriteWidth;
	protected Rect sRectangle;
	protected long frameTimer;
	protected boolean valid = true;
	protected Integer[] activeFrameSet;

	private final Map<String, Integer[]> frameSets;
	
	protected RectF curPos = new RectF(getXPos(), getYPos(),
			getXPos() + spriteWidth, getYPos() + spriteHeight);
	
	public RectF getPos() {
		return curPos;
	}
	
	public Sprite() {
		sRectangle = new Rect(0, 0, 0, 0);
		frameTimer = 0;
		currentFrame = 0;
		xPos = 0;
		yPos = 0;
		frameSets = new HashMap<String, Integer[]>();
		createFrameSet("Static", 0);
	}
	
	public Sprite(int x, int y, Bitmap spriteSheet, int theFPS, float frameWidth, float frameHeight) {
		sRectangle = new Rect(0, 0, 0, 0);
		frameTimer = 0;
		currentFrame = 0;
		xPos = x;
		yPos = y;
		frameSets = new HashMap<String, Integer[]>();
		init(spriteSheet, theFPS, frameWidth, frameHeight);
	}
	
	protected void createFrameSet(String key, Integer... frameOrder) {
		frameSets.put(key,frameOrder);
		if(activeFrameSet == null) {
			activeFrameSet = frameOrder;
		}
	}
	
	protected void removeFrameSet(String key) {
		frameSets.remove(key);
	}

    /**
     * Set the active frame set based on the key
     */
	protected synchronized void setActiveFrameSet(String key) {
		if(frameSets.containsKey(key)) {
			activeFrameSet = frameSets.get(key);
			currentFrame = 0;
		}
	}

    /**
     * Advance the frame in the active frame set
     */
	public synchronized void moveToNextFrame(Integer[] activeSet) {
		currentFrame += 1;

		if (currentFrame >= activeSet.length) {
			currentFrame = 0;
		}
	}

    /**
     * Update the sprite's frame/position
     */
	public void update() {
		Integer[] activeSet = activeFrameSet;
		if(activeSet == null) {
			return;
		}
		
		if (System.currentTimeMillis() > frameTimer + fps) {
			frameTimer = System.currentTimeMillis();
			moveToNextFrame(activeSet);
		}
		
		int activeFrame = activeSet[currentFrame];

		sRectangle.left = (int) (activeFrame * spriteWidth);
		sRectangle.right = (int) (sRectangle.left + spriteWidth);
	}
	
	public abstract void draw(Canvas c);

	public void init(Bitmap theBitmap, int theFPS) {
		init(theBitmap, theFPS, theBitmap.getWidth(), theBitmap.getHeight());
	}
	
	public void init(Bitmap theBitmap, int theFPS, int frameWidth) {
		init(theBitmap, theFPS, frameWidth, theBitmap.getHeight());
	}
	
	public void init(Bitmap theBitmap, int theFPS,float frameWidth, float frameHeight) {
		sprite = theBitmap;
		spriteHeight = frameHeight;
		spriteWidth = frameWidth;
		sRectangle.top = 0;
		sRectangle.bottom = (int) spriteHeight;
		sRectangle.left = 0;
		sRectangle.right = (int) spriteWidth;
        fps = 1000 / theFPS;

        curPos = new RectF(0, 0,
                0 + spriteWidth, 0 + spriteHeight);
	}

    public synchronized void recycle() {
        if(this.sprite != null && !this.sprite.isRecycled()) {
            Log.d(TAG, "Recycling Bitmap: "+this.sprite.toString());
            this.sprite.recycle();
        }
    }

	public float getYPos() {
		return yPos;
	}

	public float getXPos() {
		return xPos;
	}
	
	public void setX(float x) {
		xPos = x;
	}

	public void setY(float y) {
		yPos = y;
	}
	
	public float getWidth() {
		return spriteWidth;
	}
	
	public float getHeight() {
		return spriteHeight;
	}
}
