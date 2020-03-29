package com.example.soundclassification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class homeActivity extends AppCompatActivity {
    private static final String TAG= "homeActivity";
    Button signOut,history;
    TextView tv;

    FirebaseAuth mAuth;
    TextView user_name;
    TextView SN,sound_name;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference reff;
    DatabaseReference reffHistory;
    DatabaseReference refMeh;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView=(ListView)findViewById(R.id.ListV);

        signOut = (Button) findViewById(R.id.signOut);
        history = (Button) findViewById(R.id.history);
        tv = (TextView) findViewById(R.id.tv);

        SN = (TextView) findViewById(R.id.SN);

        sound_name = (TextView) findViewById(R.id.sound_name);

        mAuth = FirebaseAuth.getInstance();

        user_name = (TextView) findViewById(R.id.user_name);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        user_name.setText(user);

        reff = FirebaseDatabase.getInstance().getReference().child("user");

        final ArrayList<String>arrayList = new ArrayList<>();

        final ArrayAdapter mArrayAdapter = new ArrayAdapter(homeActivity.this,android.R.layout.simple_list_item_1,arrayList);

        reffHistory = FirebaseDatabase.getInstance().getReference();

        listView.setAdapter(mArrayAdapter);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("sound").getValue().toString();
                sound_name.setText(value);
                arrayList.add(value);
                mArrayAdapter.notifyDataSetChanged();
                reffHistory.child("history").push().setValue(value);
                reffHistory.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("history").getChildrenCount() >= 100){
                            reffHistory.child("history").removeValue();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(homeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homeActivity.this,HistoryActivity.class));
            }
        });


    }
}
