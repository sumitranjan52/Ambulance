package com.ambulance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ambulance.Common.Common;
import com.ambulance.Model.FCMResponse;
import com.ambulance.Model.MessagingToken;
import com.ambulance.Model.Notification;
import com.ambulance.Model.Sender;
import com.ambulance.Remote.IFCMService;
import com.ambulance.Remote.IGoogleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRequest extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    // google map
    GoogleMap mMap;
    SupportMapFragment mapFragment;

    // Views
    TextView txtTime, txtDistance, txtAddress;
    Button accept,decline;

    //Lat & Lng
    double lat, lng;
    private IGoogleAPI mService;
    private IFCMService mIfcmService;

    String customerTokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_request);

        mService = Common.getGoogleAPI();
        mIfcmService = Common.getFCMService();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.reqLoc);
        mapFragment.getMapAsync(this);

        //Init views
        txtTime = findViewById(R.id.txtTime);
        txtDistance = findViewById(R.id.txtDistance);
        txtAddress = findViewById(R.id.txtAddress);
        accept = findViewById(R.id.btnAccept);
        decline = findViewById(R.id.btnDecline);
        accept.setOnClickListener(this);
        decline.setOnClickListener(this);

        if (getIntent() != null) {

            lat = getIntent().getDoubleExtra("lat", -1.0);
            lng = getIntent().getDoubleExtra("lng", -1.0);

            customerTokenId = getIntent().getStringExtra("customer");

            getRequestDetailsFromCurrentLocation();

        }
    }

    private void getRequestDetailsFromCurrentLocation() {

        String requestAPI = null;
        try {
            requestAPI = Common.baseURL + "maps/api/directions/json?" +
                    "mode=driving&" +
                    "transit_routing_preference=less_driving&" +
                    "origin=" + Common.mLastLocation.getLatitude() + "," + Common.mLastLocation.getLongitude() + "&" +
                    "destination=" + lat + "," + lng + "&" +
                    "key=" + getResources().getString(R.string.google_direction_api);

            Log.d("API", requestAPI);

            mService.getPath(requestAPI).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONArray routes = jsonObject.getJSONArray("routes");

                        JSONObject routeObject = routes.getJSONObject(0);
                        JSONArray legs = routeObject.getJSONArray("legs");

                        JSONObject legsObject = legs.getJSONObject(0);

                        // get response
                        JSONObject distance = legsObject.getJSONObject("distance");
                        JSONObject time = legsObject.getJSONObject("duration");
                        String address = legsObject.getString("end_address");

                        //set values
                        txtTime.setText(time.getString("text"));
                        txtDistance.setText(distance.getString("text"));
                        txtAddress.setText(address);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(CustomerRequest.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).flat(true));
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15.0f));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnAccept:

                acceptBooking();

                break;

            case R.id.btnDecline:

                if (!TextUtils.isEmpty(customerTokenId)){

                    cancelBooking(customerTokenId);

                }

                break;

        }
    }

    private void acceptBooking() {

        Intent intent = new Intent(this,DriverTrackingActivity.class);
        intent.putExtra("lat",lat);
        intent.putExtra("lng",lng);
        intent.putExtra("customerId",customerTokenId);
        startActivity(intent);
        finish();

    }

    private void cancelBooking(String customerTokenId) {

        MessagingToken token = new MessagingToken(customerTokenId);

        Notification notification = new Notification("Notice!","Your booking has been cancelled by driver");

        Sender sender = new Sender(notification,token.getToken());

        mIfcmService.sendMessage(sender)
                .enqueue(new Callback<FCMResponse>() {
                    @Override
                    public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {

                        if (response.body().success == 1){
                            Toast.makeText(CustomerRequest.this,"Cancelled!",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<FCMResponse> call, Throwable t) {

                    }
                });

    }
}
