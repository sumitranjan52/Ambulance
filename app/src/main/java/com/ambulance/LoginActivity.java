package com.ambulance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.ambulance.Common.Common;
import com.ambulance.Model.Drivers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    /* Views */
    private EditText loginEmail,loginPassword;
    private RelativeLayout loginRootLayout;

    /* Firebase variables */
    private FirebaseAuth mfirebaseAuth;
    /*private FirebaseDatabase mDB;
    private DatabaseReference drivers;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         /* Init Firebase elements */
        mfirebaseAuth = FirebaseAuth.getInstance();
        /*mDB = FirebaseDatabase.getInstance();
        drivers = mDB.getReference(com.ambulance.Common.driverInfo);*/

        /* Init Views */
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginRootLayout = (RelativeLayout) findViewById(R.id.loginRootLayout);
        Button loginBtn = (Button) findViewById(R.id.btnLoginNow);

        /* Button click listeners */
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoginNow:
                /* Login function call goes here below */
                loginNow();
                break;
        }
    }

    private void loginNow() {
        String uEmail,uPassword;
        uEmail = loginEmail.getText().toString();
        uPassword = loginPassword.getText().toString();

        if (uEmail.length() > 3 && uPassword.length() > 6){
            mfirebaseAuth.signInWithEmailAndPassword(uEmail,uPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    FirebaseDatabase.getInstance().getReference(Common.driverInfo)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Common.currentDriver = dataSnapshot.getValue(Drivers.class);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(loginRootLayout,"Failed :(",Snackbar.LENGTH_SHORT).show();
                }
            });
        }else {
            Snackbar.make(loginRootLayout,"All fields are mandatory and minimum 3 character is required.",Snackbar.LENGTH_SHORT).show();
        }
    }
}
