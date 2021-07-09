package com.example.dekutteleconsult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DoctorsDashboardActivity extends AppCompatActivity {
    CardView DocconsultationsCrd,DocPrescribeCrd,DocReprtedEmergCrd,DocnotificationsCrd,DocAllPrescrptnCrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_dashboard);


        DocconsultationsCrd=(CardView)findViewById(R.id.CrdConsultionsFromStudnt);
        DocPrescribeCrd=(CardView)findViewById(R.id.prescribeCrd);
        DocReprtedEmergCrd=(CardView)findViewById(R.id.CrdReportedEmergencys);
        DocnotificationsCrd=(CardView)findViewById(R.id.CrdDocNotifications);
        DocAllPrescrptnCrd=(CardView)findViewById(R.id.CrdAllPrescriptions);
        DocconsultationsCrd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});




        DocAllPrescrptnCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        DocReprtedEmergCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorsDashboardActivity.this, DoctorgetAllEmergenciesActivity.class));



            }
        });



        DocnotificationsCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });







    }
}
