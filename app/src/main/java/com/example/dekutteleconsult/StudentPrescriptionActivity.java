package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.dekutteleconsult.Adapter.PrescriptionListArrayAdapter;
import com.example.dekutteleconsult.Model.Prescription;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentPrescriptionActivity extends AppCompatActivity {

    public static final String PRESCRIPTION_ID="prescriptionid";
    public static final String PATIENT_NAME="patientname";
    public static final String PATIENT_ID="patientid";
    public static final String PRESCRIPTION_DATE="prescriptiondate";




    SearchView StudprescrpSearchVw;
    ListView prescriptionLV;
    List<Prescription> StudprescrptionList;
    DatabaseReference precriptionsRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_prescription);

        precriptionsRef= FirebaseDatabase.getInstance().getReference("prescriptions");

        prescriptionLV=(ListView)findViewById(R.id.studentPrescriptionsLst);
        StudprescrpSearchVw=(SearchView) findViewById(R.id.myPrescriptionSearchVw);



        StudprescrptionList=new ArrayList<>();



        prescriptionLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prescription  prescription= StudprescrptionList.get(position);

                Intent intent=new Intent(getApplicationContext(),StudentViewPrescriptionActivity.class);

                intent.putExtra(PATIENT_ID,prescription.getPatientID());
                intent.putExtra(PATIENT_NAME,prescription.getPatientName());
                intent.putExtra(PRESCRIPTION_ID,prescription.getPrescriptionID());
                intent.putExtra(PRESCRIPTION_DATE,prescription.getIssuingDate());

                startActivity(intent);
            }
        });





    }


    @Override
    protected void onStart() {
        super.onStart();

        precriptionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                StudprescrptionList.clear();
                for (DataSnapshot prescriptionSnapshot:datasnapshot.getChildren()){
                    Prescription prescription=prescriptionSnapshot.getValue(Prescription.class );

                    assert prescription != null;
                    if (prescription.getPatientID().equalsIgnoreCase("C025-01-1002/2018")){
                    StudprescrptionList.add(prescription);
                    }
                }
                //pass adapter to listview
                PrescriptionListArrayAdapter prescriptionListAdapter=new PrescriptionListArrayAdapter(StudentPrescriptionActivity.this,StudprescrptionList);
                prescriptionLV.setAdapter(prescriptionListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}
