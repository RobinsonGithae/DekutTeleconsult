package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dekutteleconsult.Adapter.EmergencyAdapter;
import com.example.dekutteleconsult.DataModel.Emergency;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorgetAllEmergenciesActivity extends AppCompatActivity {

    //FirebaseDatabase fbDatabase;
    DatabaseReference DbReference;

    EmergencyAdapter appEmergencytAdapter;
    List<Emergency> mEmergencies;
    RecyclerView mEmergenciesRecyclrVw;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorget_all_emergencies);

        mEmergenciesRecyclrVw=findViewById(R.id.emergencyListRecyclr);
        mEmergenciesRecyclrVw.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        mEmergenciesRecyclrVw.setLayoutManager(linearLayoutManager);

        readEmergencies();


    }



    public void readEmergencies(){
        mEmergencies=new ArrayList<>();
        appEmergencytAdapter=new EmergencyAdapter(this,mEmergencies);
        mEmergenciesRecyclrVw.setAdapter(appEmergencytAdapter);
        DbReference=FirebaseDatabase.getInstance().getReference("emergencies");
        DbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //clear list if data changes/new emergency added
                //mEmergencies.clear();

                for (DataSnapshot snapshot:datasnapshot.getChildren() ){
                    //get all Emergencies from databse
                    Emergency emergency=snapshot.getValue(Emergency.class);
                    assert emergency != null;

                    mEmergencies.add(emergency);

                    appEmergencytAdapter=new EmergencyAdapter(DoctorgetAllEmergenciesActivity.this,mEmergencies);
                    mEmergenciesRecyclrVw.setAdapter(appEmergencytAdapter);



                }
                appEmergencytAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
