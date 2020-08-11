package com.example.mineclock;

public final class DefaultConfiguration implements Configuration {
    private static final boolean DEBUG_ENABLED = false;
    private static final int IMAGES_PER_DAY = 64;
    private static final int IMAGE_OFFSET = IMAGES_PER_DAY / 2;
    private static final int UPDATE_INTERVAL_MS = 24 * 60 * 60 / IMAGES_PER_DAY * 1000;

    @Override
    public final boolean debugEnabled() {
        return DEBUG_ENABLED;
    }

    @Override
    public final int imageOffset() {
        return IMAGE_OFFSET;
    }

    @Override
    public final int imagesPerDay() {
        return IMAGES_PER_DAY;
    }

    @Override
    public final int updateIntervalMs() {
        return UPDATE_INTERVAL_MS;
    }
}
