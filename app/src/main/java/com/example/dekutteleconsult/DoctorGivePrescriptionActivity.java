package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorGivePrescriptionActivity extends AppCompatActivity {
String prescribedDrugNme,DrugType,DoseUnits,DoseQuantity,DoseTimesOfTheDay,drugIntakeDuration,DrugInstructions;
Spinner DrugNmeSpnr,DrugTypeSpnr,DosageUnitsSpnr;
EditText DoseQuantityET,DoseTimesOfTheDayET,drugIntakeDurationET,DrugInstructionsET;
Button AddPrescrbdDrugBtn,clearBtn,ExitBtn,DonePrescribngBtn;
Intent ChatIntent;

FirebaseDatabase fbDatabase;
DatabaseReference prescriptiondbReference;


ArrayList<HashMap<String,Object>>drugArrayList=new ArrayList<HashMap<String,Object>>();

    HashMap <String,Object> PrescribedDrugHashmap;
    HashMap <String,HashMap> AllDrugsHashmap;
    HashMap <String,HashMap> PrescriptionHashmap;

    public static int nthDrug=1;
    String DrugN="Med"+nthDrug;

//    public static void setNthDrug(int nthDrug) {
//        DoctorGivePrescriptionActivity.nthDrug = nthDrug;
//    }

    public void receivePrescriptionInputs(){


    prescribedDrugNme=DrugNmeSpnr.getSelectedItem().toString();
    DrugType=DrugTypeSpnr.getSelectedItem().toString();
    DoseUnits=DosageUnitsSpnr.getSelectedItem().toString();

    DoseQuantity=DoseQuantityET.getText().toString().trim();
    DoseTimesOfTheDay=DoseTimesOfTheDayET.getText().toString().trim();
    drugIntakeDuration=drugIntakeDurationET.getText().toString().trim();
    DrugInstructions=DrugInstructionsET.getText().toString().trim();

//    final String userid=ChatIntent.getStringExtra("userid");
    final String userid=" STUD";
    PrescribedDrugHashmap=new HashMap<>();
    PrescribedDrugHashmap.put("prescribedDrugNme",prescribedDrugNme);
    PrescribedDrugHashmap.put(" DrugType", DrugType);
    PrescribedDrugHashmap.put("DoseUnits",DoseUnits);
    PrescribedDrugHashmap.put("DoseQuantity",DoseQuantity);
    PrescribedDrugHashmap.put("DoseTimesOfTheDay",DoseTimesOfTheDay);
    PrescribedDrugHashmap.put("drugIntakeDuration",drugIntakeDuration);
    PrescribedDrugHashmap.put("DrugInstructions",DrugInstructions);
    PrescribedDrugHashmap.put("PatientID",userid);
    PrescribedDrugHashmap.put("Date","Date");

//    nthDrug=nthDrug+1;
//String DrugN="Med"+nthDrug;

   // HashMap <String,HashMap> PrescriptionHashmap=new HashMap<>();



    Toast.makeText(this,"Drug is"+prescribedDrugNme+"to be taken for "+drugIntakeDuration,Toast.LENGTH_LONG).show();


        prescriptiondbReference=FirebaseDatabase.getInstance().getReference("Prescriptions");
        String ID=prescriptiondbReference.push().getKey();
        AllDrugsHashmap=new HashMap<>();
       // AllDrugsHashmap.put(DrugN,PrescribedDrugHashmap);
        AllDrugsHashmap.put(ID,PrescribedDrugHashmap);


}

public void clearPrescriptionInputs(){

    DrugNmeSpnr.setSelection(-1);
    DrugTypeSpnr.setSelection(-1);
    DosageUnitsSpnr.setSelection(-1);

    DoseQuantityET.getText().clear();
    DoseTimesOfTheDayET.getText().clear();
    drugIntakeDurationET.getText().clear();
    DrugInstructionsET.getText().clear();


//    PrescribedDrugHashmap.clear();
//    AllDrugsHashmap.clear();








    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_give_prescription);


        nthDrug++;
        nthDrug=nthDrug+1;
        String nth= String.valueOf(nthDrug);
        Toast.makeText(getApplicationContext(), nth,Toast.LENGTH_LONG).show();




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




        AddPrescrbdDrugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
nthDrug++;
                receivePrescriptionInputs();

                prescriptiondbReference=FirebaseDatabase.getInstance().getReference("Prescriptions");
                String ID=prescriptiondbReference.push().getKey();

                //AllDrugsHashmap=new HashMap<>();
               // AllDrugsHashmap.put(DrugN,PrescribedDrugHashmap);
//                HashMap<String, HashMap>AllDrugsHashmap= new HashMap<>();
                AllDrugsHashmap.put(ID,PrescribedDrugHashmap);






                nthDrug++;
                nthDrug=nthDrug+1;
                String nth= String.valueOf(nthDrug);
                Toast.makeText(getApplicationContext(), nth,Toast.LENGTH_LONG).show();




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



                PrescriptionHashmap=new HashMap<>();
                PrescriptionHashmap.put("AllDrugs",AllDrugsHashmap);

                prescriptiondbReference=FirebaseDatabase.getInstance().getReference("Prescriptions");
               String ID=prescriptiondbReference.push().getKey();
               Toast.makeText(getApplicationContext(),ID,Toast.LENGTH_LONG).show();
                prescriptiondbReference.push().setValue(PrescriptionHashmap).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });




            clearPrescriptionInputs();

            }
        });






    }
}
