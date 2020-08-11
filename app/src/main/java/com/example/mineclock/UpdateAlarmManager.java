package com.example.mineclock;

import android.content.Context;

public interface UpdateAlarmManager {
    String ACTION_UPDATE_ALARM_FIRED = "com.example.mineclock.action.UPDATE_ALARM_FIRED";

    void cancelPending(Context context);
    void scheduleNext(Context context);
}
