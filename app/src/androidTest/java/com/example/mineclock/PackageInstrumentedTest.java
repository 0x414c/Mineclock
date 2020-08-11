package com.example.mineclock;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public final class PackageInstrumentedTest {
    @Test
    public final void packageName_isCorrect() {
        final Context applicationContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.mineclock", applicationContext.getPackageName());
    }
}
