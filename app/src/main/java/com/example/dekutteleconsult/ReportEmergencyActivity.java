package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dekutteleconsult.Service.StudentLocationTrackService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class ReportEmergencyActivity extends AppCompatActivity {
Button ActivateEmergencyBtn,DeactivateEmergencyBtn;

StudentLocationTrackService locationTrack;
FirebaseAuth fAuth;
    FirebaseUser currentfUser;

    FirebaseDatabase fbDatabase;
    DatabaseReference DbReference;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_emergency);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);




        ActivateEmergencyBtn=(Button) findViewById(R.id.activateEmergcyBtn);
        DeactivateEmergencyBtn=(Button) findViewById(R.id.deactivateEmergcyBtn);







        ActivateEmergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reportEmergency();

            }
        });




        DeactivateEmergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deactivateEmergency();

            }
        });






    }

    private void deactivateEmergency() {





    }

    private void reportEmergency() {
        locationTrack = new StudentLocationTrackService(ReportEmergencyActivity.this);


        if (locationTrack.canGetLocation()) {


            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();




            fbDatabase=FirebaseDatabase.getInstance();

            DatabaseReference DbReference=fbDatabase.getReference();

            HashMap<String,Object> EmergencyHashmap=new HashMap<>();

            if (latitude!=0.0 && longitude!=0.0) {
                EmergencyHashmap.put("emergencyID", DbReference.push().getKey());
                EmergencyHashmap.put("latitude", latitude);
                EmergencyHashmap.put("longitude", longitude);
                EmergencyHashmap.put("emergencystatus", "active");

                //set values of hashmap to db
                DbReference.child("emergencies").push().setValue(EmergencyHashmap).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });


            } else {
               Toast.makeText(getApplicationContext(),"Location is inaccurate. TRY AGAIN",Toast.LENGTH_LONG).show();
            }














        } else {

            locationTrack.showSettingsAlert();
        }
    }

}
