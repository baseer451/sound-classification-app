package com.example.soundclassification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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

public class MainActivity extends AppCompatActivity {
    Button btnSignup;
    TextView tvSignin;
    EditText tvEmail,tvPass;
    FirebaseAuth mfirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        tvSignin = (TextView) findViewById(R.id.tvSignin);
        tvEmail = (EditText) findViewById(R.id.tvEmail);
        tvPass = (EditText) findViewById(R.id.tvPass);
        mfirebaseAuth = FirebaseAuth.getInstance();


        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyLoginActivity.class);
                startActivity(i);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tvEmail.getText().toString();
                String password = tvPass.getText().toString();
                if(!(email.isEmpty() && password.isEmpty())) {
                    mfirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Signup succesful",
                                                Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mfirebaseAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Signup Failed",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }
                            });
                }
                else {
                    tvEmail.setError("Please enter email id");
                    tvEmail.requestFocus();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mfirebaseAuth.getCurrentUser();
        if(null != mfirebaseAuth.getCurrentUser()) {
            updateUI(currentUser);
        }
}

    private void updateUI(FirebaseUser currentUser) {
        if(null != mfirebaseAuth.getCurrentUser()){
            Intent i = new Intent(MainActivity.this,homeActivity.class);
            startActivity(i);
        }
    }
}
