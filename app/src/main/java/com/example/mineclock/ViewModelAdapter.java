package com.example.mineclock;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.Locale;

public final class ViewModelAdapter {
    private static final int SECONDS_IN_DAY = 24 * 60 * 60;

    private final Configuration configuration;
    private final WallClock wallClock;

    public ViewModelAdapter(final Configuration configuration, final WallClock wallClock) {
        this.configuration = configuration;
        this.wallClock = wallClock;
    }

    @NotNull
    @Contract(" -> new")
    public ViewModel viewModel() {
        CharSequence debugInfo;
        if (this.configuration.debugEnabled()) {
            debugInfo = String.format(
                Locale.getDefault(), "%s %d",
                this.wallClock.now().toString(), this.image()
            );
        } else {
            debugInfo = "";
        }

        final int image = this.image();

        return new ViewModel(debugInfo, image);
    }

    @SuppressWarnings("SameParameterValue")
    private static int lerp(final int x, final int x0, final int x1, final int y0, final int y1) {
        return y0 + (x - x0) * (y1 - y0) / (x1 - x0);
    }

    private int image() {
        final LocalTime now = this.wallClock.now();
        final int seconds = now.toSecondOfDay();

        return (
                lerp(seconds, 0, SECONDS_IN_DAY, 0, this.configuration.imagesPerDay())
                + this.configuration.imageOffset()
            )
            % this.configuration.imagesPerDay();
    }
}
