package com.example.dekutteleconsult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DoctorsDashboardActivity extends AppCompatActivity {
    CardView DocconsultationsCrd,DocHelpCrd,DocReprtedEmergCrd,DocAllPrescrptnCrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_dashboard);


        DocconsultationsCrd=(CardView)findViewById(R.id.CrdConsultionsFromStudnt);
        DocHelpCrd=(CardView)findViewById(R.id.helpCrd);
        DocReprtedEmergCrd=(CardView)findViewById(R.id.CrdReportedEmergencys);


        DocconsultationsCrd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startActivity(new Intent(DoctorsDashboardActivity.this,AllStudentChatListActivity.class));


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



        DocHelpCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });







    }
}
