package com.ambulance.Remote;

import com.ambulance.Model.FCMResponse;
import com.ambulance.Model.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by sumit on 25-Jan-18.
 */

public interface IFCMService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAig44gvE:APA91bGquk-BWPkHrm_F4AoctWO9hpDCnp05hb1rAOD3pGfL4gKWcBQfZZggmI5Yycpg4lfY9KGxX_fxBeNpITWfhKncsXAp4plCz7tgyFTKj7dfbS4xvjzXiJ8x3YOcxch5tEAnUt122WMcltY4seTvvgKbN7nTeQ"
    })

    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Sender body);

}
