package com.ambulance.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import com.ambulance.Common.Common;
import com.ambulance.Model.MessagingToken;

/**
 * Created by sumit on 25-Jan-18.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();

        updateTokenToServer(refreshToken);

    }

    private void updateTokenToServer(String refreshToken) {

        DatabaseReference token_table = FirebaseDatabase.getInstance().getReference(Common.tokens);

        MessagingToken messagingToken = new MessagingToken(refreshToken);

        if (FirebaseAuth.getInstance().getCurrentUser() != null){

            token_table.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(messagingToken);

        }

    }
}
