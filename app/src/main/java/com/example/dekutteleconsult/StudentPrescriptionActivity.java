package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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




    public void searchViewFunction(){

        androidx.appcompat.widget.SearchView prescrptnSearchView=(androidx.appcompat.widget.SearchView)findViewById(R.id.myPrescriptionSearchVw);
        prescrptnSearchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //searchPrescription(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //searchPrescription(newText);
                return false;
            }
        });

    }

//
//    public void searchPrescription(String srchTxt){
//
//
//        prescriptionLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Prescription  prescription= StudprescrptionList.get(position);
//
//                Intent intent=new Intent(getApplicationContext(),StudentViewPrescriptionActivity.class);
//
//                intent.putExtra(PATIENT_ID,prescription.getPatientID());
//                intent.putExtra(PATIENT_NAME,prescription.getPatientName());
//                intent.putExtra(PRESCRIPTION_ID,prescription.getPrescriptionID());
//                intent.putExtra(PRESCRIPTION_DATE,prescription.getIssuingDate());
//
//                startActivity(intent);
//            }
//        });
//
//
//
//
//
//
//        //sick user is student
//       FirebaseUser fbSickUser= FirebaseAuth.getInstance().getCurrentUser();
//        assert fbSickUser != null;
//        final String currentUserID=fbSickUser.getUid();
//
//        //testing
//
//
//
//
//       DatabaseReference DbReference=FirebaseDatabase.getInstance().getReference("Prescriptions").child(currentUserID);
//        DbReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                User userd=snapshot.getValue(User.class);
//                assert user != null;
//
//
//
//
//
//
//                Query chatSrchQuery=FirebaseDatabase.getInstance().getReference("chats").orderByChild("message").startAt(srchTxt).endAt(srchTxt+"\uf8ff");
//
//                chatSrchQuery.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                        //User user=snapshot.getValue(User.class);
//                        assert user != null;
//                        String imageurl=user.getImageURL();
//
//                        //clear list if data changes/new chat added
//                        mchats.clear();
//
//
//
//                        for (DataSnapshot dataSnapshot:snapshot.getChildren() ){
//                            //get all chats from databse
//                            Chat chat=dataSnapshot.getValue(Chat.class);
//                            assert chat != null;
//                            if(chat.getReceiver().equals(myid)&& chat.getSender().equals(userid)|| chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
//
//                                //NOW get only add chat for the specific (receiver) user to the chats list
//                                mchats.add(chat);
//                            }
//                            appchatAdapter=new ChatAdapter(StudentChatActivity.this,mchats,imageurl);
//                            chatsRecyclrVw.setAdapter(appchatAdapter);
//
//                        }
//
//
//
//
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//
//
//    }
//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_prescription);

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
