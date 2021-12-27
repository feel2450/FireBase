package com.example.firebase;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button onButton;
    Button offButton;
    TextView textView;
    ImageView Led_Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onButton = findViewById(R.id.btn01);
        offButton = findViewById(R.id.btn02);
        textView = findViewById(R.id.textView);
        Led_Status = findViewById(R.id.Led_Status);
        setTitle(("LED Remote Control"));

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LED_STATUS");

        myRef.setValue("Hello, World!");
        textView.setText(myRef.getKey());

        // read from the Database, addValue Event Listenning
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ledState = (String) dataSnapshot.getValue();
                //String value = dataSnapshot.getValue(String.class);
                textView.setText("LED is " + ledState);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.RED);
                // write to the Database
                Led_Status.setImageResource(R.drawable.on);
                myRef.setValue("ON");


            }
        });

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.BLUE);
                // write to the Database
                Led_Status.setImageResource(R.drawable.off);
                myRef.setValue("OFF");
            }
        });
    }
}