package com.opensource.beacon.notification;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.opensource.beacon.BuildConfig;
import com.opensource.beacon.R;
import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.Autopilot;
import com.urbanairship.UAirship;

import java.util.UUID;

public class SampleAutopilot extends Autopilot {
    private static final String TAG = SampleAutopilot.class.getSimpleName();

    @Override
    public void onAirshipReady(@NonNull UAirship airship) {
        airship.getPushManager().setUserNotificationsEnabled(true);
        Log.i(TAG, "_log onAirshipReady.");
        // Additional Airship SDK setup

        String userEmail = "fahadcse11@gmail.com";

        airship.getNamedUser().setId(UUID.nameUUIDFromBytes(userEmail.getBytes()).toString());

        AirshipListener airshipListener = new AirshipListener();
        airship.getPushManager().addPushListener(airshipListener);
        airship.getPushManager().addPushTokenListener(airshipListener);
        airship.getPushManager().setNotificationListener(airshipListener);
        airship.getChannel().addChannelListener(airshipListener);

        airship.getLocationManager().setBackgroundLocationAllowed(true);
        airship.getLocationManager().setLocationUpdatesEnabled(true);
        airship.getLocationManager().addLocationListener(airshipListener);
    }

    @Override
    public AirshipConfigOptions createAirshipConfigOptions(@NonNull Context context) {
        new AirshipConfigOptions.Builder()
                .setInProduction(!BuildConfig.DEBUG)
                .setNotificationIcon(R.drawable.ic_launcher_foreground)
                .build();

        return super.createAirshipConfigOptions(context);
    }
}
