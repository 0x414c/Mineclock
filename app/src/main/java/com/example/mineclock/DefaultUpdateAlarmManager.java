package com.example.mineclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import org.jetbrains.annotations.NotNull;

public final class DefaultUpdateAlarmManager implements UpdateAlarmManager {
    private static final int UPDATE_ALARM_FIRED_INTENT_REQUEST_ID = 0;

    private final Configuration configuration;
    private final Class<?> handler;
    private final Logger logger;

    public DefaultUpdateAlarmManager(
        final Configuration configuration, final Class<?> handler, final Logger logger
    ) {
        this.configuration = configuration;
        this.handler = handler;
        this.logger = logger;
    }

    @Override
    public final void cancelPending(final Context context) {
        final PendingIntent alarmFired = this.alarmFired(context);
        final AlarmManager alarmManager = this.alarmManager(context);
        alarmManager.cancel(alarmFired);
    }

    @Override
    public final void scheduleNext(final Context context) {
        final PendingIntent alarmFired = this.alarmFired(context);
        final AlarmManager alarmManager = this.alarmManager(context);
        final long now = SystemClock.elapsedRealtime();
        this.logger.debug("scheduleNext now " + now);
        final long fireAt = this.nextMultiple(now, this.configuration.updateIntervalMs());
        this.logger.debug("scheduleNext fireAt " + fireAt);
        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME, fireAt, this.configuration.updateIntervalMs(), alarmFired
        );
    }

    private PendingIntent alarmFired(final Context context) {
        final Intent alarmFired = new Intent(context, this.handler);
        alarmFired.setAction(UpdateAlarmManager.ACTION_UPDATE_ALARM_FIRED);

        return PendingIntent.getBroadcast(
            context, UPDATE_ALARM_FIRED_INTENT_REQUEST_ID, alarmFired,
            PendingIntent.FLAG_CANCEL_CURRENT
        );
    }

    private AlarmManager alarmManager(@NotNull final Context context) {
        return (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    private long nextMultiple(final long x, final long multiple) {
        return x - x % multiple + multiple;
    }
}
