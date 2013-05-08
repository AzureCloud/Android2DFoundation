package com.metagx.foundation;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class Background extends Sprite {

	private int speedX = 0;
	private int speedY = 5;
	final private int screenWidth, screenHeight;
	
	public Background(int screenWidth, int screenHeight) {
		super();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		yPos = (int) (spriteHeight-screenHeight);
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
		
//		if(yPos+spriteHeight < canvas.getHeight()) {
//			yPos = 0;//(int) (canvas.getHeight()-spriteHeight);
//		} else if(yPos >= 0) {
//			yPos = (int)(-spriteHeight+screenHeight);
//		}
		
		
		xPos += speedX;

//		if(xPos+spriteWidth < canvas.getWidth()) {
//			xPos = 0;//(int) (canvas.getHeight()-spriteHeight);
//		} else if(xPos >= 0) {
//			xPos = (int)(-spriteWidth+screenWidth);
//		}
		
		curPos = new RectF(getXPos(), getYPos(),
				getXPos() + spriteWidth, getYPos() + spriteHeight);

        canvas.drawBitmap(sprite, sRectangle, curPos, null);
	}
	
}
