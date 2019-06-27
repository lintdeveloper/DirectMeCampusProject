package com.geekbounty.directme.Authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.geekbounty.directme.HomeActivity;
import com.geekbounty.directme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";;
    FirebaseAuth auth;
    Button signupBtn;
    EditText userMail, userPass;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        userMail = (EditText)findViewById(R.id.userEmail);
        userPass = (EditText)findViewById(R.id.userPass);
        signupBtn = (Button)findViewById(R.id.signupBtn);
        progressBar = findViewById(R.id.progressBar);
        getSupportActionBar().hide();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser!= null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

    }

    private void signUp() {
        String email = userMail.getText().toString().toString().toLowerCase().trim();
        String password = userPass.getText().toString().toString().toLowerCase().trim();

        //Validation
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please Enter Your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6 || password.length() > 10 ){
            Toast.makeText(this, "Password Length Must be between 6 and 10 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        loginUser(email, password);
    }

    //Logs rhe User In
    private void loginUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public void fPasswordHandler(View view) {
        startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
    }

    public void onSigninHandler(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
