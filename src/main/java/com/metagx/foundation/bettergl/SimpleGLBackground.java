package com.metagx.foundation.bettergl;

import com.metagx.foundation.bettergl.model.OpenGLSingleObject;

/**
 * Created by Adam on 8/23/14.
 */
public class SimpleGLBackground implements GLBackground {
    private Texture texture;

    private OpenGLSingleObject background;

    private String asset;
    private int glWorldWidth, glWorldHeight;

    private FileIO fileIO;

    public SimpleGLBackground(String asset, FileIO fileIO, int glWorldWidth, int glWorldHeight) {
        this.asset = asset;
        this.glWorldWidth = glWorldWidth;
        this.glWorldHeight = glWorldHeight;
        this.fileIO = fileIO;
    }

    @Override
    public void draw() {
        background.draw();
    }

    @Override
    public void update(float delta) {
        background.model.update(delta);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void init(GLGraphics glGraphics, int width, int height) {
        texture = new Texture(glGraphics, fileIO, asset);
        background = new OpenGLSingleObject(texture, glGraphics,
                glWorldWidth, glWorldHeight, glWorldWidth, glWorldHeight, asset);
        background.model.position.set(glWorldWidth/2, glWorldHeight/2);
    }
}