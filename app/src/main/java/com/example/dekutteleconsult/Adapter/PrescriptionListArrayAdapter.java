package com.example.dekutteleconsult.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dekutteleconsult.Model.Prescription;
import com.example.dekutteleconsult.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrescriptionListArrayAdapter extends ArrayAdapter<Prescription> {

    private Activity context;
    private List<Prescription>prescriptionList;



    public PrescriptionListArrayAdapter( Activity context, List<Prescription> prescriptionList) {
        super(context, R.layout.doctor_added_prescription_list_item,prescriptionList);
        this.context = context;
        this.prescriptionList = prescriptionList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       LayoutInflater inflater=context.getLayoutInflater();

       View listViewItem=inflater.inflate(R.layout.doctor_added_prescription_list_item,null,true);


        TextView tvRegNo=(TextView)listViewItem.findViewById(R.id.patientRegNo);
        TextView tvPresID=(TextView)listViewItem.findViewById(R.id.prescptnIdTV);
        TextView tvPatientName=(TextView)listViewItem.findViewById(R.id.patientNameTV);
        TextView tvDate=(TextView)listViewItem.findViewById(R.id.prescrpnDateTV);

        Prescription prescription=prescriptionList.get(position);

        String regNo="RegNo: "+prescription.getPatientID();
        String prescId="PrescriptionID: "+prescription.getPrescriptionID();
        String patntName="Patient Name: "+prescription.getPatientName();
        String PresDate= "Date: "+prescription.getIssuingDate();


        tvRegNo.setText(regNo);
        tvPresID.setText(prescId);
        tvPatientName.setText(patntName);
        tvDate.setText(PresDate);

return listViewItem;


    }
}
