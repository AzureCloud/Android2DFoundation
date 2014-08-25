package com.metagx.foundation.bettergl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class GLGame extends Activity implements Game, Renderer {
    enum GLGameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }
    
    protected GLSurfaceView glView;
    GLGraphics glGraphics;
    FileIO fileIO;
    Screen screen;
    GLGameState state = GLGameState.Initialized;
    Object stateChanged = new Object();
    long startTime = System.nanoTime();
    WakeLock wakeLock;

    private int screenWidth = -1, screenHeight = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);

        superFullScreen();

        setupContentView();

        glGraphics = new GLGraphics(glView);
        fileIO = new AndroidFileIO(getAssets());
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GLGame");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void superFullScreen() {
        // This flag is only available in API level 14 and later.
        getWindow().getDecorView().setSystemUiVisibility(0x8);
    }

    protected void setupContentView() {
        glView = new GLSurfaceView(this);
        glView.setRenderer(this);
        setContentView(glView);
    }

    public void onResume() {
        super.onResume();
        resumeGame();
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {        
        glGraphics.setGL(gl);
        
//        synchronized(stateChanged) {
//            if(state == GLGameState.Initialized)
//                screen = getStartScreen();
//            state = GLGameState.Running;
//            screen.resume();
//            startTime = System.nanoTime();
//        }
    }

    public void pauseGame() {
        synchronized(stateChanged) {
            if(isFinishing())
                state = GLGameState.Finished;
            else
                state = GLGameState.Paused;
            while(true) {
                try {
                    stateChanged.wait();
                    break;
                } catch(InterruptedException e) {
                }
            }
        }
        wakeLock.release();
        glView.onPause();
        if (getCurrentScreen() != null) {
            getCurrentScreen().dispose();
        }
    }

    public void resumeGame() {
        glView.onResume();
        wakeLock.acquire();
        if (getCurrentScreen() != null) {
            getCurrentScreen().reloadTextures();
        }
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;

        if(screenWidth > 0 && screenHeight > 0) {
            synchronized (stateChanged) {
                synchronized(stateChanged) {
                    if(state == GLGameState.Initialized) {
                        screen = getStartScreen();
                    }
                    state = GLGameState.Running;
                    screen.setScreenBounds(screenWidth, screenHeight);
                    screen.resume();
                    startTime = System.nanoTime();
                }
            }
        }
    }

    @Override
    public void runOnMainThread(Runnable runnable) {
        runOnUiThread(runnable);
    }

    @Override
    public void onDrawFrame(GL10 gl) {                
        GLGameState state = null;
        
        synchronized(stateChanged) {
            state = this.state;
        }
        
        if(state == GLGameState.Running) {
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            
            screen.update(deltaTime);
            screen.present(deltaTime);
        }
        
        if(state == GLGameState.Paused) {
            screen.pause();            
            synchronized(stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }
        
        if(state == GLGameState.Finished) {
            screen.pause();
            screen.dispose();
            synchronized(stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }            
        }
    }   
    
    @Override 
    public void onPause() {
        pauseGame();
        super.onPause();
    }    
    
    public GLGraphics getGLGraphics() {
        return glGraphics;
    }  

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }


    public void quit(View unused) {
        finish();
    }

    public abstract void pauseMenu(View unused);

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(getCurrentScreen() == null) {
            return super.onTouchEvent(event);
        }
        return getCurrentScreen().onMotionEvent(event);
    }
}
