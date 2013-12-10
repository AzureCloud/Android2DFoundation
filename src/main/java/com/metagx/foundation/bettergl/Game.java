package com.metagx.foundation.bettergl;

public interface Game {

    public FileIO getFileIO();

    public void setScreen(Screen screen);

    public GLGraphics getGLGraphics();

    public Screen getCurrentScreen();

    public Screen getStartScreen();
}