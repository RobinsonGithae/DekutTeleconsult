package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dekutteleconsult.Adapter.DoctorAdapter;
import com.example.dekutteleconsult.Adapter.UserAdapter;
import com.example.dekutteleconsult.DataModel.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllDoctorsActivity extends AppCompatActivity {

    DatabaseReference doctorReference;
   // FirebaseAuth firebaseAuth;

    private RecyclerView recyclerView;
    private DoctorAdapter DocAdapter;
    private List<Doctor> mDoctors;

    FirebaseUser fUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_doctors);

        recyclerView = (RecyclerView) findViewById(R.id.DoctorsRecyclervw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mDoctors=new ArrayList<>();


        readDoctors();




    }





    public void readDoctors(){

        fUser=FirebaseAuth.getInstance().getCurrentUser();

        assert fUser != null;
        String myid=fUser.getUid();




        doctorReference=FirebaseDatabase.getInstance().getReference("Doctors");

        doctorReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    Doctor doctor=dataSnapshot.getValue(Doctor.class);

                    assert doctor!=null;

                  //  if (!(doctor.getId().equals(myid))){
                        mDoctors.add(doctor);
                  //  }



                }

                //remember to set ischat to false
                DocAdapter=new DoctorAdapter(getApplicationContext(),mDoctors,false);
                recyclerView.setAdapter(DocAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}
