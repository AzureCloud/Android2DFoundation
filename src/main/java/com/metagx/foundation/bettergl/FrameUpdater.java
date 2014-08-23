package com.metagx.foundation.bettergl;

/**
 * Handles frame updates.
 */
public interface FrameUpdater {
    void update(float deltaTime);

    void pause();

    void unpause();
}
