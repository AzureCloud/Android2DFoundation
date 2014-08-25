package com.metagx.foundation.bettergl.model;

import com.metagx.foundation.bettergl.FileIO;
import com.metagx.foundation.bettergl.GLGame;
import com.metagx.foundation.bettergl.GLGraphics;
import com.metagx.foundation.bettergl.Texture;
import com.metagx.foundation.bettergl.model.OpenGLObject;
import com.metagx.foundation.bettergl.model.area.Area;

/**
 * Created by Adam on 12/10/13.
 */
public class OpenGLObjectFactory {

    protected GLGame game;
    protected GLGraphics glGraphics;
    protected int glWidth, glHeight;

    protected int screenWidth, screenHeight;

    public OpenGLObjectFactory(GLGame game, GLGraphics glGraphics, int glWidth, int glHeight) {
        this.game = game;
        this.glGraphics = glGraphics;
        this.glWidth = glWidth;
        this.glHeight = glHeight;
    };

    public OpenGLObject createObject(final String assetPath, int width, int height) {
        return new OpenGLObject(game, glGraphics, glWidth, glHeight, width, height, assetPath) {

            @Override
            public MotionModel addObject(Area area) {
                MotionModel motionModel = new BoundedMotionModel(area, width, height) {
                    @Override
                    public void update(float delta) {
                        //noop
                    }
                };

                motionModelList.add(motionModel);
                return motionModel;
            }
        };
    }

    public OpenGLObject createObject(final String assetPath, int width, int height, int widthBound, int heightBound) {
        return new OpenGLObject(game, glGraphics, widthBound, heightBound, width, height, assetPath) {

            @Override
            public MotionModel addObject(Area area) {
                MotionModel motionModel = new BoundedMotionModel(area, width, height) {
                    @Override
                    public void update(float delta) {
                        //noop
                    }
                };

                motionModelList.add(motionModel);
                return motionModel;
            }
        };
    }

    public Texture createTexture(FileIO fileIO, String assetPath) {
        return new Texture(glGraphics, fileIO, assetPath);
    }

    public OpenGLSingleObject createSingleObject(final String assetPath, int width, int height) {
        return new OpenGLSingleObject(game.getFileIO(), glGraphics, glWidth, glHeight,
                width, height, assetPath);
    }

    public void setScreenSize(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
}
