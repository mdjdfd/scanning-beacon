package com.opensource.beacon.notification;

import android.util.Base64;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.opensource.beacon.ApplicationController;
import com.opensource.beacon.Constants;
import com.opensource.beacon.R;
import com.opensource.beacon.SharedPreferenceManager;
import com.opensource.beacon.Utils;
import com.opensource.beacon.network.ApiEndpoints;
import com.opensource.beacon.network.VolleyResponseListener;
import com.opensource.beacon.network.VolleyUtils;

public class ScheduleNotificationInteractor {
    private static final String TAG = ScheduleNotificationInteractor.class.getSimpleName();

    public void sendPush(String state) {

        VolleyUtils.POST_METHOD(ApplicationController.getInstance(), ApiEndpoints.getSchedulePushUrl(), getHeaders(), getRequestBody(state), new VolleyResponseListener() {
            @Override
            public void onResponse(Object response) {
                Log.i(TAG, "_log onResponse : " + response.toString());
            }

            @Override
            public void onError(String message) {
                Log.i(TAG, "_log onError : %s" + message);
            }
        });
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        String credentials = Constants.AIRSHIP_APP_KEY + ":" + Constants.AIRSHIP_APP_MASTER_SECRET;
        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", auth);

        return headers;
    }


    private String getRequestBody(String state) {
        JSONArray jsonArray = new JSONArray();

        JSONObject schedule = new JSONObject();
        try {
            schedule.put("scheduled_time", Utils.getDeviceDateTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject android_channel = new JSONObject();
        try {
            android_channel.put("android_channel", SharedPreferenceManager.getString(Constants.CHANNEL_ID, null));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray arrayAudience = new JSONArray();
        arrayAudience.put(android_channel);

        JSONObject audience = new JSONObject();
        try {
            audience.put("or", arrayAudience);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject notification = new JSONObject();
        try {
            notification.put("alert", "Good Day : " + state);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray device_types = new JSONArray();
        device_types.put("android");

        JSONObject push = new JSONObject();
        try {
            push.put("audience", audience);
            push.put("notification", notification);
            push.put("device_types", device_types);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject scheduleObject = new JSONObject();
        try {
            scheduleObject.put("name", ApplicationController.getInstance().getString(R.string.app_name));
            scheduleObject.put("schedule", schedule);
            scheduleObject.put("push", push);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(scheduleObject);


        return jsonArray.toString();
    }
}
