package com.ambulance.Service;

import android.content.Intent;

import com.ambulance.CustomerRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

/**
 * Created by sumit on 26-Jan-18.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        LatLng riderLocation = new Gson().fromJson(remoteMessage.getNotification().getBody(),LatLng.class);

        Intent customerRequest = new Intent(getBaseContext(),CustomerRequest.class);
        customerRequest.putExtra("lat",riderLocation.latitude);
        customerRequest.putExtra("lng",riderLocation.longitude);
        customerRequest.putExtra("customer",remoteMessage.getNotification().getTitle());

        startActivity(customerRequest);

    }

}
