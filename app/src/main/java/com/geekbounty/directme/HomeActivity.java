package com.geekbounty.directme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.geekbounty.directme.Authentication.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        setTitle("Direct Tour Navigator");
    }

    //Logs the User Out of Application
    public void logoutHandler(View view) {
        auth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void mapNavHandler(View view) {
        startActivity(new Intent(getApplicationContext(), MapDemoActivity.class));
    }
}
