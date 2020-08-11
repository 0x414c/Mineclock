package com.example.mineclock;

import android.widget.RemoteViews;

import org.jetbrains.annotations.NotNull;

public class RemoteViewsExtensions {
    public static void setImageLevel(
        @NotNull final RemoteViews self, final int view, final int level
    ) {
        self.setInt(view, "setImageLevel", level);
    }
}
