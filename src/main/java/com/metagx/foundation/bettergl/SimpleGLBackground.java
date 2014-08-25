package com.metagx.foundation.bettergl;

import com.metagx.foundation.bettergl.model.OpenGLObjectFactory;
import com.metagx.foundation.bettergl.model.OpenGLSingleObject;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Adam on 8/23/14.
 */
public class SimpleGLBackground implements GLBackground {
    private Texture texture;

    private OpenGLSingleObject background;

    private String asset;
    private int glWorldWidth, glWorldHeight;

    public SimpleGLBackground(String asset, FileIO fileIO, int glWorldWidth, int glWorldHeight) {
        this.asset = asset;
        this.glWorldWidth = glWorldWidth;
        this.glWorldHeight = glWorldHeight;
    }

    @Override
    public void draw(GL10 gl) {
        background.draw();
    }

    @Override
    public void update(float delta) {
        background.model.update(delta);
    }

    @Override
    public void reloadTextures() {
        if (background != null) {
            background.getTexture().reload();
        }
    }

    @Override
    public void init(OpenGLObjectFactory objectFactory) {
        background = objectFactory.createSingleObject(asset, glWorldWidth, glWorldHeight);
        background.model.position.set(glWorldWidth/2, glWorldHeight/2);
    }

    @Override
    public void dispose() {
        if (background != null) {
            background.getTexture().dispose();
        }
    }
}