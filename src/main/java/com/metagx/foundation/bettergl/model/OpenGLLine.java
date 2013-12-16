package com.metagx.foundation.bettergl.model;

import com.metagx.foundation.bettergl.BindableVertices;
import com.metagx.foundation.bettergl.GLGame;
import com.metagx.foundation.bettergl.GLGraphics;
import com.metagx.foundation.bettergl.model.OpenGLObject;
import com.metagx.foundation.exception.SuperClassDidNotImplementException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Adam on 12/11/13.
 */
public abstract class OpenGLLine extends OpenGLObject {
    protected final float lineWidth;

    protected MotionModel motionModel;

    public OpenGLLine(GLGame glGame, GLGraphics glGraphics, int glWorldWidth, int glWorldHeight, int x, int y, float lineWidth) {
        super(glGame, glGraphics, glWorldWidth, glWorldHeight, 10, (int) lineWidth);
        this.lineWidth = lineWidth;
        this.motionModel.position.set(x,y);
        updateVerticies();
    }

    public abstract void setMotionModel();

    public MotionModel getMotionModel() {
        return motionModel;
    }

    @Override
    @Deprecated
    public MotionModel addObject() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public List<MotionModel> getMotionModelList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void draw() {
        GL10 gl = glGraphics.getGL();
        gl.glDisable(GL10.GL_TEXTURE_2D);
        bindableVertices.bind();
        gl.glLoadIdentity();
        gl.glLineWidth(height);
        gl.glTranslatef(motionModel.position.x, motionModel.position.y, 0);
        gl.glScalef(motionModel.scaleX, motionModel.scaleY, 0);
        bindableVertices.draw(GL10.GL_LINES, 0, 2);
        bindableVertices.unbind();
    }

    public void updateVerticies() {
        bindableVertices.setVertices(new float[] {
                -motionModel.width/2, 1, 1,0,0,1,
                motionModel.width/2, 1, 1, 0, 0, 1}, 0, 12);

//        Log.d("Growing", "V: mm.x=" + motionModel.x + " mm.x+width=" + motionModel.x+motionModel.width);
    }

    @Override
    protected void setupCustomVerticies() throws SuperClassDidNotImplementException {
        setMotionModel();

        bindableVertices = new BindableVertices(glGraphics, 2, 0, true, hasTexture());
        updateVerticies();
    }
}
