package com.metagx.foundation.log;

/**
 * Created by Adam on 8/23/14.
 */
public final class Logger {

    public static void error(String TAG, String message, Object... arguments) {
        android.util.Log.e(TAG, String.format(message, arguments));
    }

    public static void verbose(String TAG, String message, Object... arguments) {
        android.util.Log.v(TAG, String.format(message, arguments));
    }

    public static void warn(String TAG, String message, Object... arguments) {
        android.util.Log.w(TAG, String.format(message, arguments));
    }
}
