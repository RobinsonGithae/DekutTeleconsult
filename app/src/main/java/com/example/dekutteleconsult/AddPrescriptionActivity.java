package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dekutteleconsult.Adapter.PrescriptionListArrayAdapter;
import com.example.dekutteleconsult.DataModel.Prescription;
import com.example.dekutteleconsult.DataModel.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddPrescriptionActivity extends AppCompatActivity {
    public static final String PRESCRIPTION_ID="prescriptionid";
    public static final String PATIENT_NAME="patientname";
    public static final String PATIENT_ID="patientid";
    public static final String PRESCRIPTION_DATE="prescriptiondate";

    public  String ToDeletePRESCRIPTION_ID="";
    public  String ToUpdatePRESCRIPTION_ID="";


    String studentFullName,studentRegNo;

    Button addPrescriptionBtn;

    EditText patientNameET,ETpatientID,ETIntakeDuration,ETNoOfMedcneInPrescrptn;
    TextView DateTV;

    ListView addedprescriptionLV;
    List<Prescription>prescriptionList;
    DatabaseReference precriptionsRef;
    Intent ChatIntent=getIntent();
    Intent intent2=getIntent();

    String passedid;
    String rawSystemtime,PrescriptionDateTime;



    public void automaticallyGetPatientDetailsFromDB(){
       Bundle bundle=getIntent().getExtras();

     if (bundle!=null)
     {
         passedid=bundle.getString("passedid");
     }


      //   Toast.makeText(getApplicationContext(),"passed id is"+passedid,Toast.LENGTH_LONG).show();

        assert passedid != null;
        DatabaseReference studentRef=FirebaseDatabase.getInstance().getReference("Students").child(passedid);

        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student=snapshot.getValue(Student.class);
                assert student != null;
                studentRegNo= student.getRegNo();
                studentFullName=student.getFullName();

                patientNameET.setText(studentFullName);
                ETpatientID.setText(studentRegNo);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

getCurrentSystemsDateAutomatically();
    }

    public void getCurrentSystemsDateAutomatically(){

        rawSystemtime=String.valueOf(System.currentTimeMillis());
      //convert timestamp to dd/mm/yyyy hh:mm am/pm
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(rawSystemtime));

        PrescriptionDateTime= DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();
        //String DateLabel="Todays Date: "+PrescriptionDateTime;
        DateTV.setText(PrescriptionDateTime);
        Toast.makeText(getApplicationContext(),"raw date is "+PrescriptionDateTime,Toast.LENGTH_LONG).show();


    }

    public Boolean prescriptionInputFieldsDataValid(){
        // method to ensure  prescription inputs are valid
        if ((patientNameET.getText().toString().trim().isEmpty())){
           patientNameET.setError("Patient name is empty. Enter All Fields");
            return false;
        }

        if ((ETpatientID.getText().toString().trim().isEmpty())){
            patientNameET.setError("Patient ID/Reg No is empty. Enter All Fields");
            return false;
        }

        if ((ETIntakeDuration.getText().toString().trim().isEmpty())){
            patientNameET.setError("Prescription Duration  is empty. Enter All Fields");
            return false;
        }


        if ((ETNoOfMedcneInPrescrptn.getText().toString().trim().isEmpty())){

            patientNameET.setError("Patient name is empty.Enter All Fields");
            return false;
        }

        return true;
    }




    public void showDeletePrompt(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("DELETE");
        builder.setMessage("Are you sure you want to delete this prescription?");
        builder.setPositiveButton("YES!!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePrescription();

            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
        }

        public void deletePrescription(){
        //retrieve prescrpid from intent

            //remove to delete prescrption and all medicines of the prescription or those medicines that have same prescription id as the prescription which is to be deleted

            DatabaseReference prescriptionRef=FirebaseDatabase.getInstance().getReference("prescriptions").child(ToDeletePRESCRIPTION_ID);
            DatabaseReference medicinesRef=FirebaseDatabase.getInstance().getReference("medicines").child(ToDeletePRESCRIPTION_ID);
           //delete the prescription
            prescriptionRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"Prescription deleted successfully",Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Failed to delete Prescription because "+e.getMessage()+"TRY LATER",Toast.LENGTH_LONG).show();

                }
            });

            //Delete MEDICINES for this prescrition
            medicinesRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"Medicine deleted successfully",Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Failed to delete Medicine 4 this prescription because "+e.getMessage()+"TRY LATER",Toast.LENGTH_LONG).show();

                }
            });


    }





    public void updatePrescription(){
//todo firebase update method here
        Toast.makeText(getApplicationContext(),"UPDATED",Toast.LENGTH_LONG).show();

    }




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
        setContentView(R.layout.activity_add_prescription);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);




        precriptionsRef= FirebaseDatabase.getInstance().getReference("prescriptions");

        addedprescriptionLV=(ListView)findViewById(R.id.addedPrescriptionList);
        addPrescriptionBtn=(Button)findViewById(R.id.addPrescrptnBtn);
      //  prescriptionDatePicker=(DatePicker)findViewById(R.id.prescrptnDatePcr);
         patientNameET=(EditText)findViewById(R.id.patientNameET);
        ETpatientID=(EditText)findViewById(R.id.RegNoET);
        ETIntakeDuration=(EditText)findViewById(R.id.prescpDurationET);
        ETNoOfMedcneInPrescrptn=(EditText)findViewById(R.id.NoOfMedcneInPrescrptnET);
        DateTV=(TextView) findViewById(R.id.TvDate);

        automaticallyGetPatientDetailsFromDB();

        prescriptionList=new ArrayList<>();


         addPrescriptionBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if (prescriptionInputFieldsDataValid()) {

                     addPrescription();
                 }else {
                     Toast.makeText(getApplicationContext(),"Check For empty Prescroption Fields",Toast.LENGTH_LONG).show();
                 }
             }


         });




         addedprescriptionLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Prescription  prescription= prescriptionList.get(position);

                 Intent intent=new Intent(getApplicationContext(),AddMedicineActivity.class);

                 intent.putExtra(PATIENT_ID,prescription.getPatientID());
                 intent.putExtra(PATIENT_NAME,prescription.getPatientName());
                 intent.putExtra(PRESCRIPTION_ID,prescription.getPrescriptionID());
                 intent.putExtra(PRESCRIPTION_DATE,prescription.getIssuingDate());

                 startActivity(intent);
             }
         });




   registerForContextMenu(addedprescriptionLV);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0,v.getId(),0,"Delete");
        menu.add(0,v.getId(),0,"Update");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int itemPosition=contextMenuInfo.position;
        Prescription  prescription= prescriptionList.get(itemPosition);

        ToDeletePRESCRIPTION_ID=prescription.getPrescriptionID();
        ToUpdatePRESCRIPTION_ID=prescription.getPrescriptionID();

        if (item.getTitle()=="Delete"){
            showDeletePrompt();


        }else if(item.getTitle()=="Update"){
            updatePrescription();
        }


        return super.onContextItemSelected(item);
    }



    @Override
    protected void onStart() {
        super.onStart();

        precriptionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            prescriptionList.clear();
               for (DataSnapshot prescriptionSnapshot:datasnapshot.getChildren()){
                   Prescription prescription=prescriptionSnapshot.getValue(Prescription.class );


                   prescriptionList.add(prescription);

               }
               //pass adapter to listview
                PrescriptionListArrayAdapter prescriptionListAdapter=new PrescriptionListArrayAdapter(AddPrescriptionActivity.this,prescriptionList);
                addedprescriptionLV.setAdapter(prescriptionListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void addPrescription() {

        String regNo=ETpatientID.getText().toString().trim();
        String patientName=patientNameET.getText().toString();
        int noOfMedicine=Integer.parseInt(ETNoOfMedcneInPrescrptn.getText().toString());
        int durationInDays=Integer.parseInt(ETIntakeDuration.getText().toString());


if (!TextUtils.isEmpty(patientName)){
    String id=precriptionsRef.push().getKey();

    Prescription prescription=new Prescription(id,regNo,patientName,PrescriptionDateTime,durationInDays,noOfMedicine);

    precriptionsRef.child(id).setValue(prescription);

    Toast.makeText(this,"Prescription added. You can now add drugs into it",Toast.LENGTH_LONG).show();

} else{ Toast.makeText(this,"Prescrption Failed to get added. Recheck empty fields and try again",Toast.LENGTH_LONG).show();
}

    }
}
