package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dekutteleconsult.Adapter.MedicineListArrayAdapter;
import com.example.dekutteleconsult.Adapter.PrescriptionListArrayAdapter;
import com.example.dekutteleconsult.Model.Medicine;
import com.example.dekutteleconsult.Model.Prescription;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddMedicineActivity extends AppCompatActivity {

    TextView TVpattientName,TVpattientID,TVPrescriptionID,TVIssuingDate;

    String prescribedDrugNme,DrugType,DoseUnits,DoseQuantity,DoseTimesOfTheDay,DrugIntakeDuration,DrugInstructions;
    Spinner DrugNmeSpnr,DrugTypeSpnr,DosageUnitsSpnr;
    EditText DoseQuantityET,DoseTimesOfTheDayET,drugIntakeDurationET,DrugInstructionsET;
    Button AddPrescrbdDrugBtn,clearBtn,ExitBtn,DonePrescribngBtn;


    ListView LVaddedmedicine;

    DatabaseReference addmedicineRef;

    List <Medicine>medicineList;


    public boolean AllMedicineInputsAreValid() {
        if (TextUtils.isEmpty(DoseQuantityET.getText().toString().trim())){
            DoseQuantityET.setError("Dose Cant be Empty");
            return false;

        }
        if (TextUtils.isEmpty(DoseTimesOfTheDayET.getText().toString().trim())){
            DoseTimesOfTheDayET.setError("Number of times in a day Cant be Empty");
            return false;
        }
        if (TextUtils.isEmpty(drugIntakeDurationET.getText().toString().trim())){
            drugIntakeDurationET.setError("Intake Duration Cant be Empty");
            return false;
        }
        if (TextUtils.isEmpty(DrugInstructionsET.getText().toString().trim())){
            DrugInstructionsET.setError("Drug Instructions Cant be Empty");
            return false;
        }


        //return true if all inputs correct and not empty
        return true;
    }



    public void receivePrescriptionInputs() {

        if (AllMedicineInputsAreValid()==true){

        prescribedDrugNme = DrugNmeSpnr.getSelectedItem().toString();
        DrugType = DrugTypeSpnr.getSelectedItem().toString();
        DoseUnits = DosageUnitsSpnr.getSelectedItem().toString();

        DoseQuantity = DoseQuantityET.getText().toString().trim();
        DoseTimesOfTheDay = DoseTimesOfTheDayET.getText().toString().trim();
        DrugIntakeDuration = drugIntakeDurationET.getText().toString().trim();
        DrugInstructions = DrugInstructionsET.getText().toString().trim();



        }else {
            Toast.makeText(AddMedicineActivity.this,"Adding Medicine Failed. Please recheck Medicines inputs such as empty fields",Toast.LENGTH_LONG).show();
        }
    }

    public void clearPrescriptionInputs() {
        DrugNmeSpnr.setSelection(0);
        DrugTypeSpnr.setSelection(0);
        DosageUnitsSpnr.setSelection(0);

        DoseQuantityET.getText().clear();
        DoseTimesOfTheDayET.getText().clear();
        drugIntakeDurationET.getText().clear();
        DrugInstructionsET.getText().clear();

    }


    @Override
    protected void onStart() {
        super.onStart();

        addmedicineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                medicineList.clear();
                for (DataSnapshot medicineSnapshot : datasnapshot.getChildren()) {
                    Medicine medicine = medicineSnapshot.getValue(Medicine.class);


                    medicineList.add(medicine);

                }
                //pass adapter to listview
                MedicineListArrayAdapter medicineListAdapter = new MedicineListArrayAdapter(AddMedicineActivity.this, medicineList);
                LVaddedmedicine.setAdapter(medicineListAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



            public void addMedicine() {

receivePrescriptionInputs();
String medicineID=addmedicineRef.push().getKey();

  //String Dosage= DoseQuantity+" x "+DoseTimesOfTheDay;

        String drugName=prescribedDrugNme;
        String drugType=DrugType;
        String drugDoseUnit=DoseUnits;
        String drugDosage=DoseQuantity+" x "+DoseTimesOfTheDay;
        String drugIntakeDuration=DrugIntakeDuration;
        String drugInstruction=DrugInstructions;

        Medicine medicine=new Medicine(drugName,drugType,drugDoseUnit,drugDosage,drugIntakeDuration,drugInstruction);
        assert medicineID != null;
        addmedicineRef.child(medicineID).setValue(medicine).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Medicine Added Successfully",Toast.LENGTH_LONG).show();
                clearPrescriptionInputs();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"ADDING MEDICINE FAILED DUE TO "+e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });





    }






        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        TVpattientName=(TextView)findViewById(R.id.TvPatientNameLabel);
        TVpattientID=(TextView)findViewById(R.id.TvPatientIDLabel);
        TVPrescriptionID=(TextView)findViewById(R.id.TvPrescriptionIDLabel);
        TVIssuingDate=(TextView)findViewById(R.id.TvMedcneIssueDateLabel);

        DrugNmeSpnr=(Spinner)findViewById(R.id.medicineNmSpnr);
        DrugTypeSpnr=(Spinner)findViewById(R.id.DrugtypeSpnr);
        DosageUnitsSpnr=(Spinner)findViewById(R.id.DoseUnitsSpnr);

        DoseQuantityET=(EditText) findViewById(R.id.doseQuantityET);
        DoseTimesOfTheDayET=(EditText)findViewById(R.id.doseTimesOftheDayET);
        drugIntakeDurationET=(EditText)findViewById(R.id.doseDurationET);
        DrugInstructionsET=(EditText)findViewById(R.id.drugInstructionET);

        AddPrescrbdDrugBtn=(Button) findViewById(R.id.AddDrug2PrescpnBtn);
        clearBtn=(Button) findViewById(R.id.ClearAllPrescrptnDetailsBtn);
        ExitBtn=(Button) findViewById(R.id.ExitPrescrptnBtn);
        DonePrescribngBtn=(Button) findViewById(R.id.DonePrescrbingBtn);

        LVaddedmedicine=(ListView) findViewById(R.id.addedMedicineLst);

medicineList=new ArrayList<>();


            AddPrescrbdDrugBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 addMedicine();


                }
            });




            clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clearPrescriptionInputs();

                }
            });





            ExitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });




            DonePrescribngBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearPrescriptionInputs();
                    finish();
                    Toast.makeText(AddMedicineActivity.this,"DONE PRESCRIBING",Toast.LENGTH_LONG).show();
                }
            });












            Intent intent=getIntent();

        String patientId=intent.getStringExtra(AddPrescriptionActivity.PATIENT_ID);
        String patientName=intent.getStringExtra(AddPrescriptionActivity.PATIENT_NAME);
        String PrescriptionDate=intent.getStringExtra(AddPrescriptionActivity.PRESCRIPTION_DATE);;
        String prescriptionID=intent.getStringExtra(AddPrescriptionActivity.PRESCRIPTION_ID);


        String patName="Patient Name: "+patientName;
        String patID="RegNo: "+patientId;
        String prescID="PrescriptionID: "+prescriptionID;
       String prescrpDate="Date: "+PrescriptionDate;



        TVpattientName.setText(patName);
        TVpattientID.setText(patID);
        TVPrescriptionID.setText(prescID);
        TVIssuingDate.setText(prescrpDate);



        addmedicineRef= FirebaseDatabase.getInstance().getReference("medicines").child(prescriptionID);



    }
}
