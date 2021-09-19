package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.dekutteleconsult.Adapter.ChatAdapter;
import com.example.dekutteleconsult.Adapter.PrescriptionListArrayAdapter;
import com.example.dekutteleconsult.DataModel.Chat;
import com.example.dekutteleconsult.DataModel.Prescription;
import com.example.dekutteleconsult.DataModel.Student;
import com.example.dekutteleconsult.DataModel.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentPrescriptionActivity extends AppCompatActivity {

    public static final String PRESCRIPTION_ID="prescriptionid";
    public static final String PATIENT_NAME="patientname";
    public static final String PATIENT_ID="patientid";
    public static final String PRESCRIPTION_DATE="prescriptiondate";




    SearchView StudprescrpSearchVw;
    ListView prescriptionLV;
    List<Prescription> StudprescrptionList;
    DatabaseReference precriptionsRef;

    String regNoStudent;






    public void searchPrescription(String srchTxt){
        //student can search student by date


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



//
//        //sick user is student
//       FirebaseUser fbSickUser= FirebaseAuth.getInstance().getCurrentUser();
//        assert fbSickUser != null;
//        final String currentUserID=fbSickUser.getUid();

        //testing
        Query SrchPrescrptnQuery=FirebaseDatabase.getInstance().getReference("prescriptions").orderByChild("issuingDate").startAt(srchTxt).endAt(srchTxt+"\uf8ff");


        SrchPrescrptnQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                StudprescrptionList.clear();
                for (DataSnapshot prescriptionSnapshot:datasnapshot.getChildren()){
                    Prescription prescription=prescriptionSnapshot.getValue(Prescription.class );

                    // String sampleRegno="C025-01-1002/2018";
                    assert prescription != null;
                    if (prescription.getPatientID().equalsIgnoreCase(regNoStudent)){
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








    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //backbutton functionality
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_prescription);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        getRegnoAutomaticallyFromDB();

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





//search prescription by date
       // SearchView prescrptnSearchView= (SearchView) findViewById(R.id.myPrescriptionSearchVw);
        StudprescrpSearchVw.setQueryHint("Search prescription by date");
        StudprescrpSearchVw.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchPrescription(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPrescription(newText);
                return false;
            }
        });








    }


    @Override
    protected void onStart() {
        super.onStart();

//        if ()


        precriptionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                StudprescrptionList.clear();
                for (DataSnapshot prescriptionSnapshot:datasnapshot.getChildren()){
                    Prescription prescription=prescriptionSnapshot.getValue(Prescription.class );

                   // String sampleRegno="C025-01-1002/2018";
                    assert prescription != null;
                    if (prescription.getPatientID().equalsIgnoreCase(regNoStudent)){
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






    private void getRegnoAutomaticallyFromDB() {

        FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
        assert fuser != null;
        final String fbaseUserid=fuser.getUid();

        DatabaseReference studentRef=FirebaseDatabase.getInstance().getReference("Students").child(fbaseUserid);

        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student=snapshot.getValue(Student.class);
                assert student != null;
                regNoStudent= student.getRegNo();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


}
