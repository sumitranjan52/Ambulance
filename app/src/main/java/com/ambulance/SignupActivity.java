package com.ambulance;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.ambulance.Common.Common;
import com.ambulance.Model.Drivers;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    /* Views */
    private EditText name, email, phone, password, confirmPassword;
    private RelativeLayout signUpRootLayout;

    /* Firebase variables */
    private FirebaseAuth mfirebaseAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference drivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /* Init Firebase elements */
        mfirebaseAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        drivers = mDB.getReference(Common.driverInfo);

        /* Init Views */
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.signUpEmail);
        phone = (EditText) findViewById(R.id.signUpPhoneNumber);
        password = (EditText) findViewById(R.id.signUpPassword);
        confirmPassword = (EditText) findViewById(R.id.signUpConfirmPassword);
        signUpRootLayout = (RelativeLayout) findViewById(R.id.signUpRootLayout);
        Button signUp = (Button) findViewById(R.id.signUpButton);

        /* Button click listeners */
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpButton:
                /* SignUp drivers with their details */
                signUpUser();
                break;
        }
    }

    private void signUpUser() {
        final String uName, uEmail, uPhone, uPassword, uConfirmPassword;
        uName = name.getText().toString();
        uEmail = email.getText().toString();
        uPhone = phone.getText().toString();
        uPassword = password.getText().toString();
        uConfirmPassword = confirmPassword.getText().toString();

        if (uName.length() >= 3 && uEmail.length() >= 3 && uPhone.length() == 10 && uPassword.length() > 6 && uConfirmPassword.length() > 6) {
            if (uPassword.equals(uConfirmPassword)){
                mfirebaseAuth.createUserWithEmailAndPassword(uEmail,uPassword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Drivers user = new Drivers();
                                user.setName(uName);
                                user.setEmail(uEmail);
                                user.setPhone(uPhone);
                                user.setPassword(uPassword);
                                drivers.child(mfirebaseAuth.getCurrentUser().getUid()).setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Snackbar.make(signUpRootLayout,"Registered! :)",Snackbar.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(signUpRootLayout,"Failed :(",Snackbar.LENGTH_SHORT).show();
                                        }
                                    });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(signUpRootLayout,"Failed :(",Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        }else{
            Snackbar.make(signUpRootLayout,"All fields are mandatory and minimum 3 character is required.",Snackbar.LENGTH_SHORT).show();
        }
    }
}
