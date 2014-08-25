package com.metagx.foundation.bettergl.model.area;

import com.metagx.foundation.bettergl.BindableVertices;
import com.metagx.foundation.bettergl.GLGraphics;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Adam on 8/24/14.
 */
public class DrawableArea extends Area {

    protected BindableVertices bindableVertices;
    protected GLGraphics glGraphics;

    protected boolean isSealed = false;
    private float alpha = 0.0f;

    private final float alpha_fade_delta = .05f;
    private final static float MAX_ALPHA = .4f;

    public DrawableArea(GLGraphics glGraphics, float centerX, float centerY, float width, float height) {
        super(centerX, centerY, width, height);
        this.glGraphics = glGraphics;
        setBindableVertices();
    }

    public boolean isSealed() {
        return isSealed;
    }

    public void setSealed(boolean isSealed) {
        this.isSealed = isSealed;
    }

    @Override
    public Area createArea(float centerX, float centerY, float width, float height) {
        return new DrawableArea(glGraphics, centerX, centerY, width, height);
    }

    public void draw() {
        if(!isSealed) {
            return;
        }

        GL10 gl = glGraphics.getGL();

        gl.glDisable(GL10.GL_TEXTURE_2D);
        bindableVertices.bind();

        gl.glLoadIdentity();

        alpha = alpha<MAX_ALPHA?alpha+alpha_fade_delta:MAX_ALPHA;

        gl.glColor4f(.48f, .48f, .48f, alpha);

        gl.glTranslatef(areaBounds.lowerLeft.x + areaBounds.width / 2, areaBounds.lowerLeft.y + areaBounds.height / 2, 0);
        bindableVertices.draw(GL10.GL_TRIANGLES, 0, 6);
        gl.glColor4f(1, 1, 1, 1f);
        bindableVertices.unbind();
    }

    protected void setBindableVertices() {
        float width = areaBounds.width;
        float height = areaBounds.height;

        bindableVertices = new BindableVertices(glGraphics, 4, 6, false, false);
        bindableVertices.setVertices(new float[] {
                -width/2, -height/2,
                width/2, -height/2,
                width/2, height/2,
                -width/2, height/2}, 0, 8);

        bindableVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
    }
}
