package com.metagx.foundation.sprite;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;

/**
 * Created by Adam on 5/15/13.
 */
public abstract class MotionSprite extends Sprite {
    protected final int screenWidth, screenHeight;

    protected float speedX = 5.2f, speedY = 5.2f;

    private Paint paint;
    private int alpha = 255;

    public MotionSprite(int screenWidth, int screenHeight) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.paint = new Paint();
        this.visualState = VisualState.VISIBLE;
        paint.setAlpha(alpha);
    }

    protected VisualState visualState = VisualState.VISIBLE;
    private boolean visualStateInFlux = false;

    public enum VisualState {
        GONE(0),
        TRANSPARENT(125),
        VISIBLE(255);

        public int value;

        VisualState(int value) {
            this.value = value;
        }
    }

    protected abstract void positionInBounds();

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    };

    public int getAlpha() {
        return alpha;
    }

    public synchronized boolean isVisualStateInFlux() {
        return visualStateInFlux;
    }

    public synchronized void fadeTo(VisualState visualState) {
        this.visualState = visualState;
        visualStateInFlux = true;
    }

    @Override
    public void update() {
        yPos += speedY;
        xPos += speedX;

        positionInBounds();

        synchronized (this) {
            if(visualStateInFlux) {
                if(this.visualState.value < alpha) {
                    alpha -= 1;
                } else if(this.visualState.value > alpha) {
                    alpha += 1;
                } else if(this.visualState.value == alpha) {
                    visualStateInFlux = false;
                }
                paint.setAlpha(alpha);
            }
        }

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

        canvas.drawBitmap(sprite, sRectangle, curPos, paint);
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}
