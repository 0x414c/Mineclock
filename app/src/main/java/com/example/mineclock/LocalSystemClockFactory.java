package com.example.mineclock;

import java.time.Clock;
import java.time.ZoneId;

public final class LocalSystemClockFactory implements ClockFactory {
    @Override
    public final Clock clock() {
        final ZoneId defaultZone = ZoneId.systemDefault();

        return Clock.system(defaultZone);
    }
}
