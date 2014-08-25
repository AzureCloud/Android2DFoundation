package com.metagx.foundation.bettergl.model;

import com.metagx.foundation.bettergl.BindableVertices;
import com.metagx.foundation.bettergl.FileIO;
import com.metagx.foundation.bettergl.GLGame;
import com.metagx.foundation.bettergl.GLGraphics;
import com.metagx.foundation.bettergl.Texture;
import com.metagx.foundation.bettergl.model.area.Area;
import com.metagx.foundation.exception.SuperClassDidNotImplementException;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Adam on 8/23/14.
 */
public class OpenGLSingleObject {
    protected BindableVertices bindableVertices;
    protected Texture texture;

    protected GLGraphics glGraphics;

    protected int glWorldWidth, glWorldHeight, width, height;

    private volatile String assetPath;

    public BoundedMotionModel model;

    public OpenGLSingleObject(Texture texture, GLGraphics glGraphics, int glWorldWidth, int glWorldHeight, int width, int height, String assetPath) {
        init(texture, glGraphics, glWorldWidth, glWorldHeight, width, height, assetPath);
    }

    public OpenGLSingleObject(FileIO fileIO, GLGraphics glGraphics, int glWorldWidth, int glWorldHeight, int width, int height, String assetPath) {
        init(new Texture(glGraphics, fileIO, assetPath), glGraphics, glWorldWidth, glWorldHeight, width, height, assetPath);
    }

    protected void init(Texture texture, GLGraphics glGraphics, int glWorldWidth, int glWorldHeight, int width, int height, String assetPath) {
        this.assetPath = assetPath;
        this.texture = texture;
        this.glWorldWidth = glWorldWidth;
        this.glWorldHeight = glWorldHeight;
        this.width = width;
        this.height = height;

        this.glGraphics = glGraphics;

        setBindableVertices();

        model = new BoundedMotionModel(width, height, glWorldWidth, glWorldHeight, false);
        model.wrapWorld = false;
    }

    public Texture getTexture() {
        return texture;
    }

    public BindableVertices getBindableVertices() {
        return bindableVertices;
    }

    public boolean hasTexture() {
        return assetPath != null;
    }

    public void setBindableVertices() {
        if(hasTexture()) {
            setupTextureVerticies();
        } else {
            setupCustomVerticies();
        }
    }

    protected void setupCustomVerticies() throws SuperClassDidNotImplementException {
        throw new SuperClassDidNotImplementException("Must implement this in super class if you need it");
    }

    protected void setupTextureVerticies() {
        bindableVertices = new BindableVertices(glGraphics, 4, 12, false, hasTexture());
        bindableVertices.setVertices(new float[] {
                -width/2, -height/2, 0, 1,
                width/2, -height/2, 1, 1,
                width/2,  height/2, 1, 0,
                -width/2, height/2, 0, 0, }, 0, 16);
        bindableVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
    }

    public void draw() {
        GL10 gl = glGraphics.getGL();

        getBindableVertices().bind();
        if(hasTexture()) {
            gl.glEnable(GL10.GL_TEXTURE_2D);
            getTexture().bind();
        }

        gl.glLoadIdentity();

        gl.glTranslatef(model.position.x, model.position.y, 0);
        getBindableVertices().draw(GL10.GL_TRIANGLES, 0, 6);

        getBindableVertices().unbind();
    }
}


