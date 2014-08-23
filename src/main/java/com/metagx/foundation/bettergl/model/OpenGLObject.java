package com.metagx.foundation.bettergl.model;

import android.os.SystemClock;

import com.metagx.foundation.bettergl.BindableVertices;
import com.metagx.foundation.bettergl.GLGame;
import com.metagx.foundation.bettergl.GLGraphics;
import com.metagx.foundation.bettergl.Texture;
import com.metagx.foundation.bettergl.model.MotionModel;
import com.metagx.foundation.bettergl.model.area.Area;
import com.metagx.foundation.exception.SuperClassDidNotImplementException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Adam on 12/10/13.
 */
public class OpenGLObject {

    protected BindableVertices bindableVertices;
    protected Texture texture;

    protected List<MotionModel> motionModelList;

    protected GLGraphics glGraphics;

    protected GLGame glGame;

    protected int glWorldWidth, glWorldHeight, width, height;

    private volatile String assetPath;

    private final Random r = new Random();

    public Area area; //TODO make wunpublic

    protected OpenGLObject() {

    }

    public OpenGLObject(GLGame glGame, GLGraphics glGraphics, int glWorldWidth, int glWorldHeight, int width, int height) {
        this(glGame, glGraphics, glWorldWidth, glWorldHeight, width, height, null, null);
    }

    public OpenGLObject(GLGame glGame, GLGraphics glGraphics, int glWorldWidth, int glWorldHeight, int width, int height, String assetPath) {
        this(glGame, glGraphics, glWorldWidth, glWorldHeight, width, height, assetPath, null);
    }

    public OpenGLObject(GLGame glGame, GLGraphics glGraphics, int glWorldWidth, int glWorldHeight, int width, int height, String assetPath, Area area) {
        init(glGame, glGraphics, glWorldWidth, glWorldHeight, width, height, assetPath, area);
    }

    public OpenGLObject(Texture texture, GLGame glGame, GLGraphics glGraphics, int glWorldWidth, int glWorldHeight, int width, int height, String assetPath, Area area) {
        init(texture, glGame, glGraphics, glWorldWidth, glWorldHeight, width, height, assetPath, area);
    }

    protected void init(Texture texture, GLGame glGame, GLGraphics glGraphics, int glWorldWidth, int glWorldHeight, int width, int height, String assetPath, Area area) {
        this.assetPath = assetPath;
        this.texture = texture;
        this.area = area;
        this.glWorldWidth = glWorldWidth;
        this.glWorldHeight = glWorldHeight;
        this.width = width;
        this.height = height;

        this.glGame = glGame;

        this.glGraphics = glGraphics;

        setBindableVertices();

        motionModelList = new ArrayList<MotionModel>();
    }

    protected void init(GLGame glGame, GLGraphics glGraphics, int glWorldWidth, int glWorldHeight, int width, int height, String assetPath, Area area) {
        this.assetPath = assetPath;
        if(hasTexture()) {
            this.texture = new Texture(glGame, getAssetPath());
        } else {
            this.texture = null;
        }

        init(texture, glGame, glGraphics, glWorldWidth, glWorldHeight, width, height, assetPath, area);
    };

    public Texture getTexture() {
        return texture;
    }

    public BindableVertices getBindableVertices() {
        return bindableVertices;
    }

    /**
     * Asset path for the Texture
     * @return
     */
    public String getAssetPath() {
        return assetPath;
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

    public MotionModel addObject(Area areaBounds) {
        MotionModel motionModel = new BoundedMotionModel(areaBounds, width, height);
        motionModelList.add(motionModel);
        return motionModel;
    }

    public MotionModel addObjectRandomVelocity(Area areaBounds) {
        MotionModel model = addObject(areaBounds);
        float vx = ((r.nextInt(111)%3==0)?-1:1) * 125 + (r.nextInt(222)*17)%50;
        float vy = ((r.nextInt(333)%2==0)?-1:1) * 100 + (r.nextInt(444)*17)%50;
        if(vx >= width) {
            vx = width-1;
        } else if(vx <= -32) {
            vx = -width+1;
        }
        if(vx >= height) {
            vy = height-1;
        } else if(vx <= -height) {
            vy = -height+1;
        }

        model.getVelocity().set(vx, vy);
        return model;
    }

    public List<MotionModel> getMotionModelList() {
        return motionModelList;
    }

    public MotionModel getMotionModel(int index) {
        return motionModelList.get(index);
    }

    final List<MotionModel> motionModels = new ArrayList<MotionModel>();

    public void draw() {
        GL10 gl = glGraphics.getGL();

        getBindableVertices().bind();
        if(hasTexture()) {
            gl.glEnable(GL10.GL_TEXTURE_2D);
            getTexture().bind();
        }

        motionModels.clear();
        motionModels.addAll(getMotionModelList());

        for(MotionModel motionModel : motionModels) {
            gl.glLoadIdentity();

            //+width/2 , +height/2
            gl.glTranslatef(motionModel.position.x, motionModel.position.y, 0);
            gl.glScalef(motionModel.scaleX, motionModel.scaleY, 0);
            getBindableVertices().draw(GL10.GL_TRIANGLES, 0, 6);
        }
        getBindableVertices().unbind();
    }
}
