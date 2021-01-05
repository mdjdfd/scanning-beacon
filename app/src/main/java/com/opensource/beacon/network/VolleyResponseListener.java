package com.opensource.beacon.network;

public interface VolleyResponseListener {

    void onResponse(Object response);

    void onError(String message);
}
