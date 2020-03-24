package com.example.soundclassification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyLoginActivity extends AppCompatActivity {
    Button btnSignin;
    TextView tvSignup;
    EditText tvEmail, tvPass;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);
        btnSignin = (Button) findViewById(R.id.btnSignIn);
        tvSignup = (TextView) findViewById(R.id.tvSignup);
        tvEmail = (EditText) findViewById(R.id.tvEmail);
        tvPass = (EditText) findViewById(R.id.tvPass);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Toast.makeText(MyLoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MyLoginActivity.this, homeActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MyLoginActivity.this, "Please enter correct credentials", Toast.LENGTH_SHORT).show();
                }
            }
        };


        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyLoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tvEmail.getText().toString();
                String password = tvPass.getText().toString();
                if (!(email.isEmpty() && password.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MyLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MyLoginActivity.this, "Sign In succesful",
                                        Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        Toast.makeText(MyLoginActivity.this, "Sign In Failed",
                                        Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }
                            });
                } else {
                    tvEmail.setError("Please enter email id");
                    tvEmail.requestFocus();
                }
            }
        });


    }

    private void updateUI(FirebaseUser user) {
        if (null != mAuth.getCurrentUser()) {
            Intent i = new Intent(MyLoginActivity.this, homeActivity.class);
            startActivity(i);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
