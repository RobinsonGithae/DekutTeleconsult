package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.dekutteleconsult.Adapter.MedicineListArrayAdapter;
import com.example.dekutteleconsult.Model.Medicine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentViewPrescriptionActivity extends AppCompatActivity {

    ListView LVprescribedmedicine;

    DatabaseReference StudprescribedmedicineRef;

    List<Medicine> medicineList;




    @Override
    protected void onStart() {
        super.onStart();

        StudprescribedmedicineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                medicineList.clear();
                for (DataSnapshot medicineSnapshot : datasnapshot.getChildren()) {
                    Medicine medicine = medicineSnapshot.getValue(Medicine.class);


                    medicineList.add(medicine);

                }
                //pass adapter to listview
                MedicineListArrayAdapter medicineListAdapter = new MedicineListArrayAdapter(StudentViewPrescriptionActivity.this, medicineList);
                LVprescribedmedicine.setAdapter(medicineListAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_prescription);

        LVprescribedmedicine=(ListView) findViewById(R.id.StudViewPrescrbdMedicineList);

        medicineList=new ArrayList<>();




        Intent intent=getIntent();
        String patientId=intent.getStringExtra(StudentPrescriptionActivity.PATIENT_ID);
        String patientName=intent.getStringExtra(StudentPrescriptionActivity.PATIENT_NAME);
        String PrescriptionDate=intent.getStringExtra(StudentPrescriptionActivity.PRESCRIPTION_DATE);;
        String prescriptionID=intent.getStringExtra(StudentPrescriptionActivity.PRESCRIPTION_ID);


        String patName="Patient Name: "+patientName;
        String patID="RegNo: "+patientId;
        String prescID="PrescriptionID: "+prescriptionID;
        String prescrpDate="Date: "+PrescriptionDate;


//
//        TVpattientName.setText(patName);
//        TVpattientID.setText(patID);
//        TVPrescriptionID.setText(prescID);
//        TVIssuingDate.setText(prescrpDate);
//


        StudprescribedmedicineRef= FirebaseDatabase.getInstance().getReference("medicines").child(prescriptionID);







    }
}
