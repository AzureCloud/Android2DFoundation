package com.metagx.foundation.game;

import android.content.Context;

import com.metagx.foundation.bettergl.GLBackground;
import com.metagx.foundation.bettergl.GLGraphics;
import com.metagx.foundation.bettergl.model.MotionModel;
import com.metagx.foundation.bettergl.model.OpenGLObject;
import com.metagx.foundation.bettergl.model.OpenGLObjectFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * BaseGameState for serializable game state.
 */
public abstract class BaseGameState implements GameState {
    protected boolean loadedTextures = false;

    private static final String BaseGameStateOpenGlObjectsKey = "baseGameStateOpenGlObjectsKey";

    protected GLBackground background;
    public final ArrayList<OpenGLObject> openGLObjects = new ArrayList<OpenGLObject>();

    protected GLGraphics glGraphics;
    protected int glWidth, glHeight;

    public BaseGameState(GLGraphics glGraphics,
                         int glWidth,
                         int glHeight) {
        this.glGraphics = glGraphics;
        this.glWidth = glWidth;
        this.glHeight = glHeight;
    }

    protected abstract void drawAdditionalObjects();
    protected abstract void updateAdditionalObjects(float deltaTime);

    public void setBackground(GLBackground background) {
        this.background = background;
        this.background.init(getOpenGLObjectFactory());
    }

    public GLBackground getBackground() {
        return background;
    }

    public void addOpenGlObject(OpenGLObject openGLObject) {
        synchronized (openGLObjects) {
            openGLObjects.add(openGLObject);
        }
    }

    public void removeOpenGlObject(OpenGLObject openGLObject) {
        synchronized (openGLObjects) {
            openGLObjects.remove(openGLObject);
        }
    }

    public void clearOpenGlObjects() {
        synchronized (openGLObjects) {
            openGLObjects.clear();
        }
    }

    public abstract OpenGLObjectFactory getOpenGLObjectFactory();

    @Override
    public void update(float deltaTime) {
        if (background != null) {
            background.update(deltaTime);
        }
        synchronized (openGLObjects) {
            for(int i=0 ; i < openGLObjects.size() ; i++) {
                OpenGLObject openGLObject = openGLObjects.get(i);

                for(MotionModel motionModel : openGLObject.getMotionModelList()) {
                    motionModel.update(deltaTime);
                }
            }
        }
        updateAdditionalObjects(deltaTime);
    }

    @Override
    public void pause() {
        loadedTextures = false;
    }

    @Override
    public void saveState(Context context) {

    }

    @Override
    public void loadState(Context context) {

    }

    public void reloadTextures() {
        loadedTextures = false;
    }

    protected  void clearState() {
        synchronized (openGLObjects) {
            for (OpenGLObject object : openGLObjects) {
                if (object.hasTexture()) {
                    object.getTexture().dispose();
                }
            }

            openGLObjects.clear();
        }
    }

    public void draw() {
        if (!loadedTextures) {
            synchronized (openGLObjects) {
                for (OpenGLObject object : openGLObjects) {
                    if (object.hasTexture()) {
                        object.getTexture().reload();
                    }
                }
            }
            background.reloadTextures();
            loadAdditionalTextures();
            loadedTextures = true;
        }
        glGraphics.getGL().glClearColor(0, 0, 0, 0);
        background.draw(glGraphics.getGL());
        synchronized (openGLObjects) {
            for (OpenGLObject object : openGLObjects) {
                object.draw();
            }
        }
        drawAdditionalObjects();
    }

    protected JSONObject buildState() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        JSONArray array = new JSONArray();
        synchronized (openGLObjects) {
            for (OpenGLObject object : openGLObjects) {
//            TODO
//            array.put(object.serialize());
            }
        }
        jsonObject.put(BaseGameStateOpenGlObjectsKey, array);

        return jsonObject;
    }

    public void dispose() {
        synchronized (openGLObjects) {
            for (OpenGLObject object : openGLObjects) {
                if (object.hasTexture()) {
                    object.getTexture().dispose();
                }
            }
        }
        background.dispose();
        disposeAdditionalTextures();
    }

    protected abstract void disposeAdditionalTextures();


}
