package com.metagx.foundation.game;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by Adam on 12/26/13.
 */
public abstract class BaseGameState implements GameState {

    @Override
    public void saveState(Context context) {

    }

    @Override
    public void loadState(Context context) {

    }

    protected abstract JSONObject buildState();
}
