package com.metagx.foundation.game;

import android.content.Context;
import org.json.JSONObject;

/**
 * Created by Adam on 12/26/13.
 */
public interface GameState {

    void saveState(Context context);

    void loadState(Context context);
}
