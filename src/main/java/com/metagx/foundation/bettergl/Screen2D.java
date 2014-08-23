package com.metagx.foundation.bettergl;

import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.metagx.foundation.bettergl.level.LevelConfig;
import com.metagx.foundation.bettergl.model.area.Area;
import com.metagx.foundation.bettergl.touch.TouchHandler;
import com.metagx.foundation.bettergl.ui.UIPanels;
import com.metagx.foundation.game.BaseGameState;
import com.metagx.foundation.game.GameState;

import java.util.logging.Level;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Adam on 12/9/13.
 */
public abstract class Screen2D extends Screen {
    protected volatile boolean initialized = false;
    protected boolean setupLevel = false;

    protected GestureDetector gestureDetector;
    protected TouchHandler touchHandler;
    protected FrameUpdater frameUpdater;
    protected GLGraphics glGraphics;
    protected FPSCounter fpsCounter;

    protected abstract FrameUpdater createFrameUpdater();

    protected abstract void onResumeChrome();

    protected abstract LevelConfig getLevelConfig();

    protected abstract GameState getGameState();

    protected abstract void setupLevel();

    protected abstract UIPanels getUiPanels();

    protected abstract void clearUIPanels();

    public Screen2D(GLGame game, int glWidth, int glHeight) {
        super(game, glWidth, glHeight);
        glGraphics = game.getGLGraphics();
        frameUpdater = createFrameUpdater();
        fpsCounter = new FPSCounter();
    }

    @Override
    public void resume() {
        GL10 gl = glGraphics.getGL();
        gl.glClearColor(0,1,0,0);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, glWidth, 0, glHeight, 1, -1);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glMatrixMode(GL10.GL_MODELVIEW);

        onResumeChrome();
        game.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                setupGesture();
            }
        });
    }

    @Override
    public void setScreenBounds(int width, int height) {
        super.setScreenBounds(width, height);

        initVertices(screenWidth, screenHeight);
        initialized = true;
    }

    @Override
    public boolean onMotionEvent(MotionEvent event) {
        if(gestureDetector == null) {
            return false;
        }
        return gestureDetector.onTouchEvent(event);
    }

    private void setupGesture() {
        gestureDetector = new GestureDetector(game, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float vX, float vY) {
                touchHandler.onFling(motionEvent, motionEvent2);
                return false;
            }
        });
    }


    public void initVertices(int screenWidth, int screenHeight) {
        initialized = true;
    }

    @Override
    public void update(float deltaTime) {
        if(!initialized) {
            return;
        }

        if(!setupLevel) {
            setupLevel();
        }

        frameUpdater.update(deltaTime);
    }

    @Override
    public void present(float deltaTime) {
        if(!initialized) {
            return;
        }

        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        getGameState().draw();

        fpsCounter.logFrame();
    }

    @Override
    public synchronized void pause() {
        clearUIPanels();
        touchHandler.setUiPanels(null);
        gestureDetector = null;
        getGameState().pause();
    }

    @Override
    public boolean onKeyEvent(KeyEvent event) {
        return touchHandler.onKeyEvent(event);
    }
}
