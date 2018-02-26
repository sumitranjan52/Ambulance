package com.ambulance.Common;

import android.location.Location;

import com.ambulance.Model.Drivers;
import com.ambulance.Remote.FCMClient;
import com.ambulance.Remote.IFCMService;
import com.ambulance.Remote.IGoogleAPI;
import com.ambulance.Remote.RetrofitClient;

public class Common {

    // Database info
    public static final String driverInfo = "DriverInformation";
    public static final String riderInfo = "RiderInformation";
    public static final String driverLoc = "DriverLocation";
    public static final String requestRide = "RequestRide";
    public static final String tokens = "MessagingTokens";

    // Location com.ambulance.Common for all activities
    public static Location mLastLocation = null;

    // Current user
    public static Drivers currentDriver;

    // APIs URLS
    public static final String baseURL = "https://maps.googleapis.com/";
    public static final String fcmURL = "https://fcm.googleapis.com/";

    public static IGoogleAPI getGoogleAPI(){
        return RetrofitClient.getRetrofit(baseURL).create(IGoogleAPI.class);
    }

    public static IFCMService getFCMService(){
        return FCMClient.getRetrofit(fcmURL).create(IFCMService.class);
    }
}
