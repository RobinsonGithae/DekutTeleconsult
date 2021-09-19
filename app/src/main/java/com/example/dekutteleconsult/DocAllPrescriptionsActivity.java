package com.example.dekutteleconsult;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.dekutteleconsult.DataModel.Prescription;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DocAllPrescriptionsActivity extends AppCompatActivity {
    ListView addedprescriptionLV;
    List<Prescription> prescriptionList;
    DatabaseReference precriptionsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_all_prescriptions);

        precriptionsRef= FirebaseDatabase.getInstance().getReference("prescriptions");

        addedprescriptionLV=(ListView)findViewById(R.id.allPrescriptionList);




        prescriptionList=new ArrayList<>();




    }
}
