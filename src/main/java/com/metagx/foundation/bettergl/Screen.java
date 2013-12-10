package com.metagx.foundation.bettergl;

public abstract class Screen {
    protected final Game game;

    protected final int glWidth, glHeight;

    public Screen(Game game, int glWidth, int glHeight) {
        this.game = game;
        this.glWidth = glWidth;
        this.glHeight = glHeight;
    }

    public abstract void setScreenBounds(int width, int height);

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
}
