package com.metagx.foundation.sprite;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.List;

/**
 * Created by Adam on 5/15/13.
 */
public abstract class MotionSprite extends Sprite {
    protected final int screenWidth, screenHeight;

    protected float speedX = 5.2f, speedY = 5.2f;

    public MotionSprite(int screenWidth, int screenHeight) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    protected abstract void positionInBounds();

    @Override
    public void update() {
        yPos += speedY;
        xPos += speedX;

        positionInBounds();

        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        if(activeFrameSet == null) {
            return;
        }
        update();

        curPos = new RectF(getXPos(), getYPos(),
                getXPos() + spriteWidth, getYPos() + spriteHeight);

        canvas.drawBitmap(sprite, sRectangle, curPos, null);
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}
