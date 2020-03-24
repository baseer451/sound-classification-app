package com.example.soundclassification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homeActivity extends AppCompatActivity {
    Button signOut;
    TextView tv;
    FirebaseAuth mAuth;
    TextView user_name;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        signOut = (Button) findViewById(R.id.signOut);
        tv = (TextView) findViewById(R.id.tv);
        mAuth = FirebaseAuth.getInstance();
        user_name = (TextView) findViewById(R.id.user_name);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user_name.setText(user.getDisplayName());
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(homeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
