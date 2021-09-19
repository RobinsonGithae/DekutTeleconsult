package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dekutteleconsult.Adapter.MedicineListArrayAdapter;
import com.example.dekutteleconsult.DataModel.Medicine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentViewPrescriptionActivity extends AppCompatActivity {

    ListView LVprescribedmedicine;

    DatabaseReference StudprescribedmedicineRef;

    List<Medicine> medicineList;
    Button DownloadPDFBtn;






    public void showPrintingDialog(String Status){
        final ProgressDialog pd=new ProgressDialog(StudentViewPrescriptionActivity.this);
        if (Status.equals("SHOW")){
            //pass SHOW to method for it to display Dialog
            pd.setMessage("Generating PDF...");
            pd.show();
        }else if (Status.equals("CLOSE")){
          //  pd.dismiss();
            pd.cancel();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void convertPrescriptionToPDF(){
        showPrintingDialog("SHOW");
//HERE
        PdfDocument document=new PdfDocument();
       //HERE
        Paint paint=new Paint();

        Paint linePaint=new Paint();
        linePaint.setColor(Color.rgb(0,50,250));





        PrintAttributes printAttributes=new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setMinMargins(new PrintAttributes.Margins(1000,1000,1000,1000))
                .build();


        // open a new document
        Context context=this.getApplicationContext();

//        PrintedPdfDocument document = new PrintedPdfDocument(context,
//                printAttributes);



        // start a page

        //here
        PdfDocument.PageInfo pdfPageInfo=new PdfDocument.PageInfo.Builder(800,1200,1).create();
        PdfDocument.Page  pdfPage=document.startPage(pdfPageInfo);

        //here

        //PdfDocument.Page page = document.startPage(1);

        // draw something on the page
        Canvas canvas=pdfPage.getCanvas();
        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0,50,250));

        canvas.drawText("DEKUT TELECONSULT PRESCRIPTION",20,20, paint);
        paint.setTextSize(8.5f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setPathEffect(new DashPathEffect(new float[]{5,5},0));
        linePaint.setStrokeWidth(2);
        canvas.drawLine(20,65,230,65,linePaint);
        canvas.drawText("Patient Name: patName",20,40,paint);







        int width=canvas.getWidth();
        int height=canvas.getHeight();

        LVprescribedmedicine.measure(width,height);
        LVprescribedmedicine.layout(0,0,width,height);
        LVprescribedmedicine.draw(pdfPage.getCanvas());



        // finish the page
        document.finishPage(pdfPage);



//
//
//
//        //can add more pages
//        PdfDocument.PageInfo pdfPageInfo2=new PdfDocument.PageInfo.Builder(800,1200,2).create();
//        PdfDocument.Page  pdfPage2=document.startPage(pdfPageInfo2);
//
//
//
//        int width=canvas.getWidth();
//        int height=canvas.getHeight();
//
//        LVprescribedmedicine.measure(width,height);
//        LVprescribedmedicine.layout(0,0,width,height);
//        LVprescribedmedicine.draw(pdfPage.getCanvas());
//
//
//
//        // finish the page
//        document.finishPage(pdfPage2);
//
//











        String date=new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());

        try {
            // write the document content

            document.writeTo(new FileOutputStream(context.getExternalFilesDir(null)+"/"+date+"test.pdf"));

            //close dialog with custom defined method by passing any string other string.
            showPrintingDialog("CLOSE");
            Toast.makeText(getApplicationContext(),"PRESCRIPTION GENERATED SUCCESSFULLY",Toast.LENGTH_LONG).show();

        }catch (Exception e){

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }


        //close the document
        document.close();





    }








    @Override
    protected void onStart() {
        super.onStart();

        StudprescribedmedicineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                medicineList.clear();
                for (DataSnapshot medicineSnapshot : datasnapshot.getChildren()) {
                    Medicine medicine = medicineSnapshot.getValue(Medicine.class);


                    medicineList.add(medicine);

                }
                //pass adapter to listview
                MedicineListArrayAdapter medicineListAdapter = new MedicineListArrayAdapter(StudentViewPrescriptionActivity.this, medicineList);
                LVprescribedmedicine.setAdapter(medicineListAdapter);


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
        setContentView(R.layout.activity_student_view_prescription);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        DownloadPDFBtn=(Button)findViewById(R.id.DownloadPrescrptnBtn);
        LVprescribedmedicine=(ListView) findViewById(R.id.StudViewPrescrbdMedicineList);

        medicineList=new ArrayList<>();




        Intent intent=getIntent();
        String patientId=intent.getStringExtra(StudentPrescriptionActivity.PATIENT_ID);
        String patientName=intent.getStringExtra(StudentPrescriptionActivity.PATIENT_NAME);
        String PrescriptionDate=intent.getStringExtra(StudentPrescriptionActivity.PRESCRIPTION_DATE);;
        String prescriptionID=intent.getStringExtra(StudentPrescriptionActivity.PRESCRIPTION_ID);


        String patName="Patient Name: "+patientName;
        String patID="RegNo: "+patientId;
        String prescID="PrescriptionID: "+prescriptionID;
        String prescrpDate="Date: "+PrescriptionDate;


//
//        TVpattientName.setText(patName);
//        TVpattientID.setText(patID);
//        TVPrescriptionID.setText(prescID);
//        TVIssuingDate.setText(prescrpDate);
//


        StudprescribedmedicineRef= FirebaseDatabase.getInstance().getReference("medicines").child(prescriptionID);





        DownloadPDFBtn.setOnClickListener(new View.OnClickListener() {
           // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    convertPrescriptionToPDF();
                    //uncomment above

                   // startActivity(new Intent(StudentViewPrescriptionActivity.this,DownloadPrescriptionPDFActivity.class));




                }
                else {Toast.makeText(getApplicationContext(),"PDF Download is only available for Android 4.4 (KITKAT) DEVICES",Toast.LENGTH_LONG).show(); }
            }
        });


    }
}
