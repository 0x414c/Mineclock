package com.example.mineclock;

import java.time.Clock;
import java.time.LocalTime;

public final class DefaultWallClock implements WallClock {
    private final ClockFactory clockFactory;

    public DefaultWallClock(final ClockFactory clockFactory) {
        this.clockFactory = clockFactory;
    }

    @Override
    public final LocalTime now() {
        final Clock clock = this.clockFactory.clock();

        return LocalTime.now(clock);
    }
}
