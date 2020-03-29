package com.example.soundclassification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {


    private static final String TAG = "HistoryActivity";
    ListView listV;
    TextView history,go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listV = (ListView) findViewById(R.id.listV);
        history = (TextView) findViewById(R.id.history);
        go_back = (TextView) findViewById(R.id.go_back);
        DatabaseReference refMeh = FirebaseDatabase.getInstance().getReference().child("history");
        refMeh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapShot : dataSnapshot.getChildren()){
                    GenericTypeIndicator<HashMap<String,Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    Map<String,Object> objectHashMap = dataSnapshot.getValue(objectsGTypeInd);
                    ArrayList<Object> objectArrayList = new ArrayList<Object>(objectHashMap.values());
                    ArrayAdapter historyArrayAdapter = new ArrayAdapter(HistoryActivity.this,android.R.layout.simple_list_item_1,objectArrayList);
                    Log.v(TAG, ""+objectArrayList);
                    listV.setAdapter(historyArrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistoryActivity.this, homeActivity.class);
                startActivity(i);
            }
        });
    }

}
