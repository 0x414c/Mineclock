package com.example.mineclock;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

final class FakeWallClock implements WallClock {
    private LocalTime now;

    public FakeWallClock() {
        this.now = LocalTime.MIN;
    }

    @Override
    public final LocalTime now() {
        return this.now;
    }

    public final void setNow(final LocalTime now) {
        this.now = now;
    }
}

public final class ViewModelAdapterUnitTest {
    private ViewModelAdapter viewModelAdapter;
    private FakeWallClock wallClock;

    @Before
    public final void setUp() {
        this.wallClock = new FakeWallClock();
        final Configuration configuration = new DefaultConfiguration();
        this.viewModelAdapter = new ViewModelAdapter(configuration, this.wallClock);
    }

    @Test
    public final void image_isCorrect_whenNoon() {
        this.wallClock.setNow(LocalTime.NOON);
        assertEquals(0, this.viewModelAdapter.viewModel().image());
    }

    @Test
    public final void image_isCorrect_whenBeforeNoon() {
        this.wallClock.setNow(LocalTime.NOON.minusSeconds(1));
        assertEquals(63, this.viewModelAdapter.viewModel().image());
    }

    @Test
    public final void image_isCorrect_whenMidnight() {
        this.wallClock.setNow(LocalTime.MIDNIGHT);
        assertEquals(32, this.viewModelAdapter.viewModel().image());
    }

    @Test
    public final void image_isCorrect_whenBeforeMidnight() {
        this.wallClock.setNow(LocalTime.MIDNIGHT.minusSeconds(1));
        assertEquals(31, this.viewModelAdapter.viewModel().image());
    }
}
