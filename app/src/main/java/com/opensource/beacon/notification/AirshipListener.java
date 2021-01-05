package com.opensource.beacon.notification;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.opensource.beacon.ApplicationController;
import com.opensource.beacon.Constants;
import com.opensource.beacon.SharedPreferenceManager;
import com.urbanairship.PreferenceDataStore;
import com.urbanairship.channel.AirshipChannel;
import com.urbanairship.channel.AirshipChannelListener;
import com.urbanairship.channel.NamedUser;
import com.urbanairship.channel.TagGroupRegistrar;
import com.urbanairship.location.LocationListener;
import com.urbanairship.push.NotificationActionButtonInfo;
import com.urbanairship.push.NotificationInfo;
import com.urbanairship.push.NotificationListener;
import com.urbanairship.push.PushListener;
import com.urbanairship.push.PushMessage;
import com.urbanairship.push.PushTokenListener;

public class AirshipListener implements  PushListener, NotificationListener, PushTokenListener, AirshipChannelListener, LocationListener {
    private static final String TAG = "AirshipListener";

    @Override
    public void onNotificationPosted(@NonNull NotificationInfo notificationInfo) {
        Log.i(TAG, "Notification posted: " + notificationInfo);
    }

    @Override
    public boolean onNotificationOpened(@NonNull NotificationInfo notificationInfo) {
        Log.i(TAG, "Notification opened: " + notificationInfo);

        // Return false here to allow Airship to auto launch the launcher
        // activity for foreground notification action buttons
        return false;
    }

    @Override
    public boolean onNotificationForegroundAction(@NonNull NotificationInfo notificationInfo, @NonNull NotificationActionButtonInfo actionButtonInfo) {
        Log.i(TAG, "Notification action: " + notificationInfo + " " + actionButtonInfo);

        // Return false here to allow Airship to auto launch the launcher
        // activity for foreground notification action buttons
        return false;
    }

    @Override
    public void onNotificationBackgroundAction(@NonNull NotificationInfo notificationInfo, @NonNull NotificationActionButtonInfo actionButtonInfo) {
        Log.i(TAG, "Notification action: " + notificationInfo + " " + actionButtonInfo);
    }

    @Override
    public void onNotificationDismissed(@NonNull NotificationInfo notificationInfo) {
        Log.i(TAG, "Notification dismissed. Alert: " + notificationInfo.getMessage().getAlert() + ". Notification ID: " + notificationInfo.getNotificationId());
    }

    @Override
    public void onPushReceived(@NonNull PushMessage message, boolean notificationPosted) {
        Log.i(TAG, "Received push message. Alert: " + message.getAlert() + ". Posted notification: " + notificationPosted);
    }

    @Override
    public void onChannelCreated(@NonNull String channelId) {
        Log.i(TAG, "Channel created " + channelId);
        SharedPreferenceManager.init(ApplicationController.getInstance());
        SharedPreferenceManager.setString(Constants.CHANNEL_ID, channelId);
    }

    @Override
    public void onChannelUpdated(@NonNull String channelId) {
        Log.i(TAG, "Channel updated " + channelId);
    }

    @Override
    public void onPushTokenUpdated(@NonNull String token) {
        Log.i(TAG, "Push token updated " + token);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.i(TAG, "Location Listener : " + location.getLatitude() + " " + location.getLongitude());
    }
}
