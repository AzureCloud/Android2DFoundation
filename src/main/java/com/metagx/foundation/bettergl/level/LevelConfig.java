package com.metagx.foundation.bettergl.level;

import com.metagx.foundation.bettergl.GLBackground;
import com.metagx.foundation.game.GameState;

/**
 * Basic interface for level configuration
 */
public interface LevelConfig {

    GLBackground getBackground();

    public LevelConfig getNextLevel();

    public void setGameState(GameState gameState);

    public boolean isFinalLevel();

    public int getLevelNumber();

    int getLiveCount();
}
