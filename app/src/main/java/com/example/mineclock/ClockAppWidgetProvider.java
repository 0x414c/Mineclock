package com.example.mineclock;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import org.jetbrains.annotations.NotNull;

import static com.example.mineclock.RemoteViewsExtensions.setImageLevel;

public final class ClockAppWidgetProvider extends AppWidgetProvider {
    private final Logger logger;
    private final UpdateAlarmManager updateAlarmManager;
    private final ViewModelAdapter viewModelAdapter;

    private ViewModel viewModel;

    public ClockAppWidgetProvider() {
        final Configuration configuration = new DefaultConfiguration();
        this.logger = new DefaultLogger(configuration);
        this.logger.debug("ClockAppWidgetProvider");
        final ClockFactory clockFactory = new LocalSystemClockFactory();
        final WallClock wallClock = new DefaultWallClock(clockFactory);
        this.updateAlarmManager = new DefaultUpdateAlarmManager(
            configuration, this.getClass(), this.logger
        );
        this.viewModelAdapter = new ViewModelAdapter(configuration, wallClock);

        this.viewModel = new ViewModel();
    }

    @Override
    public void onAppWidgetOptionsChanged(
        @NotNull final Context context, final AppWidgetManager appWidgetManager, final int widget,
        final Bundle __
    ) {
        this.logger.debug("onAppWidgetOptionsChanged");
        this.updateAlarmManager.cancelPending(context.getApplicationContext());
        this.updateAppWidget(context, appWidgetManager, widget);
        this.updateAlarmManager.scheduleNext(context.getApplicationContext());
    }

    @Override
    public final void onDisabled(@NotNull final Context context) {
        this.logger.debug("onDisabled");
        this.updateAlarmManager.cancelPending(context.getApplicationContext());
    }

    @Override
    public final void onEnabled(@NotNull final Context context) {
        this.logger.debug("onEnabled");
        this.updateAlarmManager.scheduleNext(context.getApplicationContext());
    }

    @Override
    public final void onReceive(final Context context, @NotNull final Intent intent) {
        this.logger.debug("onReceive " + intent.getAction());
        super.onReceive(context, intent);
        final String action = intent.getAction();
        if (UpdateAlarmManager.ACTION_UPDATE_ALARM_FIRED.equals(action)) {
            onUpdateAlarmFired(context);
        } else if (
               Intent.ACTION_TIME_CHANGED.equals(action)
            || Intent.ACTION_TIMEZONE_CHANGED.equals(action)
        ) {
            this.onTimeChanged(context);
        }
    }

    @Override
    public final void onUpdate(
        @NotNull final Context context, final AppWidgetManager appWidgetManager,
        @NotNull final int[] widgets
    ) {
        this.logger.debug("onUpdate");
        this.updateAlarmManager.cancelPending(context.getApplicationContext());
        for (final int widget : widgets) {
            this.updateAppWidget(context, appWidgetManager, widget);
        }
        this.updateAlarmManager.scheduleNext(context.getApplicationContext());
    }

    private void onTimeChanged(@NotNull final Context context) {
        this.logger.debug("onTimeChanged");
        this.updateAlarmManager.cancelPending(context.getApplicationContext());
        this.updateAllAppWidgets(context);
        this.updateAlarmManager.scheduleNext(context.getApplicationContext());
    }

    private void onUpdateAlarmFired(final Context context) {
        this.logger.debug("onUpdateAlarmFired");
        this.updateAllAppWidgets(context);
    }

    private void updateAllAppWidgets(final Context context) {
        this.logger.debug("updateAllAppWidgets");
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final ComponentName self = new ComponentName(
            context.getApplicationContext(), this.getClass()
        );
        final int[] widgets = appWidgetManager.getAppWidgetIds(self);
        for (final int widget : widgets) {
            this.updateAppWidget(context, appWidgetManager, widget);
        }
    }

    private void updateAppWidget(
        @NotNull final Context context, @NotNull final AppWidgetManager appWidgetManager,
        final int widget
    ) {
        this.logger.debug("updateAppWidget");
        final RemoteViews remoteViews = new RemoteViews(
            context.getPackageName(), R.layout.clock_widget
        );
        this.updateViewState();
        this.updateRemoteViews(remoteViews);
        appWidgetManager.updateAppWidget(widget, remoteViews);
    }

    private void updateRemoteViews(@NotNull final RemoteViews views) {
        this.logger.debug("updateRemoteViews");
        views.setTextViewText(R.id.clock_widget_text, this.viewModel.debugInfo());
        setImageLevel(views, R.id.clock_widget_image, this.viewModel.image());
    }

    private void updateViewState() {
        this.logger.debug("updateViewState");
        this.viewModel = this.viewModelAdapter.viewModel();
        this.logger.debug(
            "updateViewState " + this.viewModel.debugInfo() + " " + this.viewModel.image()
        );
    }
}
