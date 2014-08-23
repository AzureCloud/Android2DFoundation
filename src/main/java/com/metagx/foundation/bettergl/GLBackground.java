package com.metagx.foundation.bettergl;

/**
 * Created by Adam on 8/23/14.
 */
public interface GLBackground {
    void draw();

    void update(float delta);

    Texture getTexture();

    public void init(GLGraphics glGraphics, int width, int height);
}
