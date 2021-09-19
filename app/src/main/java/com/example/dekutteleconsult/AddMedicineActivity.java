package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dekutteleconsult.Adapter.MedicineListArrayAdapter;
import com.example.dekutteleconsult.DataModel.Medicine;
import com.example.dekutteleconsult.DataModel.Prescription;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddMedicineActivity extends AppCompatActivity {

    String[]DropdownMedicineOptions;

    TextView TVpattientName,TVpattientID,TVPrescriptionID,TVIssuingDate;

    String prescribedDrugNme,DrugType,DoseUnits,DoseQuantity,DoseTimesOfTheDay,DrugIntakeDuration,DrugInstructions;
    Spinner DrugNmeSpnr,DrugTypeSpnr,DosageUnitsSpnr;
    EditText DoseQuantityET,DoseTimesOfTheDayET,drugIntakeDurationET,DrugInstructionsET;
    Button AddPrescrbdDrugBtn,clearBtn,ExitBtn,DonePrescribngBtn;


    ListView LVaddedmedicine;

    DatabaseReference addmedicineRef;

    List <Medicine>medicineList;

    public  String ToDeleteMEDICINE_ID="";
    public  String ToDeleteMEDICINE_NAME="";
    public  String ToUpdateMEDICINE_ID="";



    //each medicine added is allocated a unique prescription_id
    //(THIS_PRESCRIPTION_ID) is the id of the prescription that contains the medicine we want
    // to delete or update
    public  String THIS_PRESCRIPTION_ID="";




    public void showExitPrompt(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("EXIT PRESCRIPTION");
        builder.setMessage("Are you sure you want to Exit Prescribing? This will take you back to Chats. ");
        builder.setPositiveButton("YES!!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                startActivity(new Intent(AddMedicineActivity.this,AllStudentChatListActivity.class));

                finish();

            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }



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

        Medicine medicine=new Medicine(medicineID,drugName,drugType,drugDoseUnit,drugDosage,drugIntakeDuration,drugInstruction);
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
        setContentView(R.layout.activity_add_medicine);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



            TVpattientName=(TextView)findViewById(R.id.TvPatientNameLabel);
        TVpattientID=(TextView)findViewById(R.id.TvPatientIDLabel);
        TVPrescriptionID=(TextView)findViewById(R.id.TvPrescriptionIDLabel);
        TVIssuingDate=(TextView)findViewById(R.id.TvMedcneIssueDateLabel);

        DrugNmeSpnr=(Spinner)findViewById(R.id.medicineNmSpnr);
        DrugTypeSpnr=(Spinner)findViewById(R.id.DrugtypeSpnr);
        DosageUnitsSpnr=(Spinner)findViewById(R.id.DoseUnitsSpnr);
        DrugNmeSpnr.setPrompt("Select Medicine");
        
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

             if (AllMedicineInputsAreValid()==true) {

                 addMedicine();
             } else {
                 Toast.makeText(getApplicationContext(),"Please Check Empty Medicine Fields",Toast.LENGTH_LONG).show();
             }




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
                    showExitPrompt();
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


        //assign PrescriptionID of the Prescription Containning the medicines being added
        THIS_PRESCRIPTION_ID=prescriptionID;

        String patName="Patient Name: "+patientName;
        String patID="RegNo: "+patientId;
        String prescID="PrescriptionID: "+prescriptionID;
        String prescrpDate="Date: "+PrescriptionDate;



        TVpattientName.setText(patName);
        TVpattientID.setText(patID);
        TVPrescriptionID.setText(prescID);
        TVIssuingDate.setText(prescrpDate);



        addmedicineRef= FirebaseDatabase.getInstance().getReference("medicines").child(prescriptionID);




            registerForContextMenu(LVaddedmedicine);

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
        Medicine medicine= medicineList.get(itemPosition);

        ToDeleteMEDICINE_ID=medicine.getMedicineID();
        ToDeleteMEDICINE_NAME=medicine.getDrugName();
        ToUpdateMEDICINE_ID=medicine.getMedicineID();

        if (item.getTitle()=="Delete"){
            showMedicineDeletePrompt();


        }else if(item.getTitle()=="Update"){
            updateMedicine();
        }


        return super.onContextItemSelected(item);
    }




    public void showMedicineDeletePrompt(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("DELETE MEDICINE");
        builder.setMessage("Are you sure you want to delete the Medicine " +ToDeleteMEDICINE_NAME+ " from this prescription?");
        builder.setPositiveButton("YES!!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteMedicine();

            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    public void deleteMedicine(){
        //retrieve prescrpid from intent

        //use remove to delete  medicine from the prescription with its medID

        DatabaseReference medicinesRef=FirebaseDatabase.getInstance().getReference("medicines").child(THIS_PRESCRIPTION_ID).child(ToDeleteMEDICINE_ID);
        //delete the medicine
        medicinesRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Medicine deleted successfully",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed to delete Medicine from prescription because "+e.getMessage()+"TRY LATER",Toast.LENGTH_LONG).show();

            }
        });




    }

    private void updateMedicine() {
        //todo updatemedicine
    }










}
