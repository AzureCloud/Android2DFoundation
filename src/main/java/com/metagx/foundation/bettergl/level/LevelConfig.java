package com.metagx.foundation.bettergl.level;

import com.metagx.foundation.bettergl.GLBackground;

/**
 * Basic interface for level configuration
 */
public interface LevelConfig {

    GLBackground getBackground();

    public LevelConfig getNextLevel();

    public boolean isFinalLevel();

    public int getLevelNumber();

    int getLiveCount();
}
