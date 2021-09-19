package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dekutteleconsult.DataModel.Medicine;
import com.example.dekutteleconsult.DataModel.PrescriptionPDFConstants;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DownloadPrescriptionPDFActivity extends AppCompatActivity {
//here
List<Medicine> medicineList;

    public String selectedprescriptionID="-McrcFZgINHf5mJAp9XU";

   // private DatabaseReference userRef;
    private DatabaseReference prescriptionRef;
//todo let prescription ref be medicine ref

    //creating a list of objects constants
    List<PrescriptionPDFConstants> prescriptionMedicinesList;
    //List all permission required
    public static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static int PERMISSION_ALL = 12;


    public static File pFile;
    private File prescriptionfile;
    private PDFView pdfView;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_prescription_p_d_f);

        medicineList=new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");

//        mAuth = FirebaseAuth.getInstance();
//        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        prescriptionRef = FirebaseDatabase.getInstance().getReference("medicines").child(selectedprescriptionID);
        pdfView = findViewById(R.id.prescription_pdf_viewer);


        Button reportButton = findViewById(R.id.button_disable_report);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previewDisabledMedicinesReport();

            }
        });

        //
        prescriptionMedicinesList = new ArrayList<>();

       // document.writeTo(new FileOutputStream(context.getExternalFilesDir(null)+"/"+date+"test.pdf"));

        //create files in DekutTeleconsult folder
        prescriptionfile = new File("/storage/emulated/0/Report/");



        //check if they exist, if not create them(directory)
        if ( !prescriptionfile.exists()) {
            prescriptionfile.mkdirs();
        }
        pFile = new File(prescriptionfile, "PrescriptionMedicines.pdf");

        //fetch prescription and disabled prescription details;
        fetchPrescriptionMedicines();
    }




    //method to fetch prescription data from the database
    private void fetchPrescriptionMedicines()
    {

        prescriptionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicineList.clear();
                for (DataSnapshot medicineSnapshot : dataSnapshot.getChildren()) {
                    Medicine medicine = medicineSnapshot.getValue(Medicine.class);


                    medicineList.add(medicine);




       //creating an object and setting to displlay
                    PrescriptionPDFConstants presriptions = new PrescriptionPDFConstants();
                    assert medicine != null;
                    presriptions.setMedicine_Name(medicine.getDrugName());
                    presriptions.setDose(medicine.getDrugDosage());
                    presriptions.setIntakeDuration(medicine.getDrugIntakeDuration());






//
//       //creating an object and setting to displlay
//                    PrescriptionPDFConstants presriptions = new PrescriptionPDFConstants();
//                    presriptions.setMedicine_Name(snapshot..child("FirstName").getValue().toString());
//                    presriptions.setDose(snapshot..child("OtherName").getValue().toString());
//                    presriptions.setIntakeDuration(snapshot..child("Phone").getValue().toString());
//
//


                    //this just log details fetched from db(you can use Timber for logging
                    Log.d("Prescription", "medicineName: " + presriptions.getMedicine_Name());
                    Log.d("Prescription", "dose: " + presriptions.getDose());
                    Log.d("Prescription", "intakeDuration: " + presriptions.getIntakeDuration());

                    /* The error before was cause by giving incorrect data type
                    You were adding an object of type PaymentUsers yet the arraylist expects obejct of type DisabledUsers
                     */
                    prescriptionMedicinesList.add(presriptions);


                }
                //create a pdf file and catch exception because file may not be created
                try {
                    createPrescriptionReport(prescriptionMedicinesList);
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"failed to create pdf because"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void createPrescriptionReport(  List<PrescriptionPDFConstants> prescriptionMedicinesList) throws DocumentException, FileNotFoundException{
        BaseColor colorWhite = WebColors.getRGBColor("#ffffff");
        BaseColor colorBlue = WebColors.getRGBColor("#056FAA");
        BaseColor grayColor = WebColors.getRGBColor("#425066");



        Font white = new Font(Font.FontFamily.HELVETICA, 15.0f, Font.BOLD, colorWhite);
        FileOutputStream output = new FileOutputStream(pFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{6, 25, 20, 20});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        Chunk noText = new Chunk("No.", white);
        PdfPCell noCell = new PdfPCell(new Phrase(noText));
        noCell.setFixedHeight(50);
        noCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        noCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk medicineNameText = new Chunk("Medicine Name", white);
        PdfPCell medicineNameCell = new PdfPCell(new Phrase(medicineNameText));
        medicineNameCell.setFixedHeight(50);
        medicineNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        medicineNameCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk doseText = new Chunk("Dosage", white);
        PdfPCell dosageCell = new PdfPCell(new Phrase(doseText));
        dosageCell.setFixedHeight(50);
        dosageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        dosageCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk durationText = new Chunk("Intake Duration(DAYS)", white);
        PdfPCell IntakeDurationCell = new PdfPCell(new Phrase(durationText));
        IntakeDurationCell.setFixedHeight(50);
        IntakeDurationCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        IntakeDurationCell.setVerticalAlignment(Element.ALIGN_CENTER);


        Chunk footerText = new Chunk("DEKUT MEDICAL CENTRE - Copyright @ 2021");
        PdfPCell footCell = new PdfPCell(new Phrase(footerText));
        footCell.setFixedHeight(70);
        footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footCell.setVerticalAlignment(Element.ALIGN_CENTER);
        footCell.setColspan(4);


        table.addCell(noCell);
        table.addCell(medicineNameCell);
        table.addCell(dosageCell);
        table.addCell(IntakeDurationCell);
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();


        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(grayColor);
        }
        for (int i = 0; i < prescriptionMedicinesList.size(); i++) {
            PrescriptionPDFConstants prescription = prescriptionMedicinesList.get(i);

            String id = String.valueOf(i + 1);
            String medName = prescription.getMedicine_Name();
            String medDose = prescription.getDose();
            String medDuration = prescription.getIntakeDuration();


            table.addCell(id + ". ");
            table.addCell(medName);
            table.addCell(medDose);
            table.addCell(medDuration);

        }

        PdfPTable footTable = new PdfPTable(new float[]{6, 25, 20, 20});
        footTable.setTotalWidth(PageSize.A4.getWidth());
        footTable.setWidthPercentage(100);
        footTable.addCell(footCell);

        PdfWriter.getInstance(document, output);
        document.open();
        Font g = new Font(Font.FontFamily.HELVETICA, 25.0f, Font.NORMAL, grayColor);
        document.add(new Paragraph(" System generated prescription report for Student\n\n", g));
        document.add(table);
        document.add(footTable);

        document.close();
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    //onstart method used to check if the user is registered or not
    @Override
    protected void onStart()
    {
        super.onStart();

//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser ==null){
//            SendUserToLoginActivity();
//        }
//        else{
//            //checking if the user exists in the firebase database
//            CheckUserExistence();
//        }
    }

//    private void CheckUserExistence()
//    {
//        //get the user id
//        final String currentUserId = mAuth.getCurrentUser().getUid();
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if (!dataSnapshot.hasChild(currentUserId)){
//                    //user is authenticated but but his record is not present in real time firebase database
//                    SendUserToStepTwoAuthentication();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

//    private void SendUserToStepTwoAuthentication()
//    {
//        Intent steptwoIntent = new Intent(MainActivity.this, StepTwoAuthentication.class);
//        steptwoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(steptwoIntent);
//        finish();
//    }

//    private void SendUserToLoginActivity()
//    {
//        Intent loginIntent = new Intent(MainActivity.this, Login.class);
//        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(loginIntent);
//        finish();
//    }

    public void previewDisabledMedicinesReport()
    {
        if (hasPermissions(this, PERMISSIONS)) {
            DisplayReport();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private void DisplayReport() {
        pdfView.fromFile(pFile)
                .pages(0, 2, 1, 3, 3, 3)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();
    }











}
