package com.metagx.foundation;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.metagx.foundation.sprite.Sprite;

public class Background extends Sprite {

	private int speedX = 0;
	private int speedY = 0;
	
	public Background() {
		super();
		yPos = 0;
        xPos = 0;
	}

    public void setSpeedX(int speed) {
        this.speedX = speed;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
	
	@Override
	public synchronized void draw(Canvas canvas) {
		if(activeFrameSet == null || sprite.isRecycled()) {
			return;
		}
		update();
		
		yPos += speedY;
		xPos += speedX;
		
		curPos = new RectF(getXPos(), getYPos(),
				getXPos() + spriteWidth, getYPos() + spriteHeight);

        canvas.drawBitmap(sprite, sRectangle, curPos, null);
	}

    public void handleCollision(Sprite sprite) {
        //noop
    }

}
