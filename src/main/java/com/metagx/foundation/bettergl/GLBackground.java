package com.metagx.foundation.bettergl;

import com.metagx.foundation.bettergl.model.OpenGLObjectFactory;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Adam on 8/23/14.
 */
public interface GLBackground {
    void draw(GL10 gl);

    void update(float delta);

    void reloadTextures();

    void init(OpenGLObjectFactory objectFactory);

    void dispose();
}
