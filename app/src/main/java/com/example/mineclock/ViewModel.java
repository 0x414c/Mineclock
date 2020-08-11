package com.example.mineclock;

public final class ViewModel {
    private final CharSequence debugInfo;
    private final int image;

    public ViewModel() {
        this.debugInfo = "";
        this.image = 0;
    }

    public ViewModel(CharSequence debugInfo, int image) {
        this.debugInfo = debugInfo;
        this.image = image;
    }

    public final CharSequence debugInfo() {
        return debugInfo;
    }

    public final int image() {
        return image;
    }
}
