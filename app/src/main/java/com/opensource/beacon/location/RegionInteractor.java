package com.opensource.beacon.location;

import android.location.Location;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.opensource.beacon.ApplicationController;
import com.opensource.beacon.BeaconDistanceListener;
import com.opensource.beacon.model.RegionsDataModel;
import com.opensource.beacon.model.RegionsItem;
import com.opensource.beacon.network.VolleyResponseListener;
import com.opensource.beacon.network.VolleyUtils;

public class RegionInteractor {
    private static final String TAG = RegionInteractor.class.getSimpleName();

    private double latitude;
    private double longitude;


    public void callRegionApi(BeaconDistanceListener beaconDistanceListener) {
        VolleyUtils.GET_METHOD(ApplicationController.getInstance(), "https://go.urbanairship.com/api/regions?limit=100", getHeaders(), new VolleyResponseListener() {
            @Override
            public void onResponse(Object response) {
                Log.i(TAG, "_log onResponse : %s" + response.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<RegionsDataModel>() {
                }.getType();
                RegionsDataModel regionsDataModel = gson.fromJson(response.toString(), type);


                locationTrack();

                for (RegionsItem regionsItem : regionsDataModel.getRegions()) {

                    Location loc1 = new Location("");
                    loc1.setLatitude(getLatitude());
                    loc1.setLongitude(getLongitude());

                    Location loc2 = new Location("");
                    loc2.setLatitude(regionsItem.getGeofence().getCenter().getLatitude());
                    loc2.setLongitude(regionsItem.getGeofence().getCenter().getLongitude());


                    float dist = loc1.distanceTo(loc2);
                    beaconDistanceListener.beaconDistance(dist);
                }

            }

            @Override
            public void onError(String message) {
                Log.i(TAG, "_log onError : %s" + message);
            }
        });
    }


    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        String credentials = "y2cY2XRDRoS--GYmGayhpg:bEDWT9XSS4aUUTARovN5eQ";
        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", auth);
        headers.put("Accept", "application/vnd.urbanairship+json; version=3;");

        return headers;
    }


    private void locationTrack() {
        GPSTracker gpsTracker = new GPSTracker(ApplicationController.getInstance());
        if (gpsTracker.canGetLocation()) {
            double longitude = gpsTracker.getLongitude();
            double latitude = gpsTracker.getLatitude();

            setLongitude(longitude);
            setLatitude(latitude);

        } else {
            gpsTracker.showSettingsAlert();
        }
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
    }


}
