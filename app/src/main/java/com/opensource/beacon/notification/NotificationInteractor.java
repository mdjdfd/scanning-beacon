package com.opensource.beacon.notification;

import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.opensource.beacon.ApplicationController;
import com.opensource.beacon.Constants;
import com.opensource.beacon.SharedPreferenceManager;
import com.opensource.beacon.model.push.PushDataModel;
import com.opensource.beacon.network.ApiEndpoints;
import com.opensource.beacon.network.VolleyResponseListener;
import com.opensource.beacon.network.VolleyUtils;

public class NotificationInteractor {
    private static final String TAG = NotificationInteractor.class.getSimpleName();

    public  void sendPush(String state) {

        VolleyUtils.POST_METHOD(ApplicationController.getInstance(), ApiEndpoints.getBasicPushUrl(), getHeaders(), getRequestBody(state), new VolleyResponseListener() {
            @Override
            public void onResponse(Object response) {
                Log.i(TAG, "_log onResponse : " + response.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<PushDataModel>() {}.getType();

                PushDataModel pushDataModel = gson.fromJson(response.toString(), type);

                Log.i(TAG, "_log onResponse : Operation ID : " + pushDataModel.getOperationId());
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
        jsonArray.put("android");

        JSONObject alert = new JSONObject();
        try {
            alert.put("alert", state + " " + Build.MANUFACTURER);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject android = new JSONObject();
        try {
            android.put("android", alert);
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

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("audience", audience);
            jsonObject.put("device_types", jsonArray);
            jsonObject.put("notification", android);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
