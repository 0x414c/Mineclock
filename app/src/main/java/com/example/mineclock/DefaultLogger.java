package com.example.mineclock;

import android.util.Log;

public final class DefaultLogger implements Logger {
    private static final String TAG = "mineclock";

    private final Configuration configuration;

    public DefaultLogger(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final void debug(final String message) {
        if (this.configuration.debugEnabled()) {
            Log.d(TAG, message);
        }
    }
}
