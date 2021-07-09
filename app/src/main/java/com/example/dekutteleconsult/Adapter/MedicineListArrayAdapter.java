package com.example.dekutteleconsult.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dekutteleconsult.Model.Medicine;
import com.example.dekutteleconsult.Model.Prescription;
import com.example.dekutteleconsult.R;

import java.util.List;

public class MedicineListArrayAdapter extends ArrayAdapter<Medicine> {
    private Activity context;
    private List<Medicine> medicineList;



    public MedicineListArrayAdapter( Activity context, List<Medicine> medicineList) {
        super(context, R.layout.doctor_added_medicine_list_item,medicineList);
        this.context = context;
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem=inflater.inflate(R.layout.doctor_added_medicine_list_item,null,true);


        TextView tvDrugName=(TextView)listViewItem.findViewById(R.id.listitmMedicineNameTV);
        TextView tvDosage=(TextView)listViewItem.findViewById(R.id.medicineDosageTV);
        TextView tvDrugType=(TextView)listViewItem.findViewById(R.id.DrugTypeTVLstItm);
        TextView tvDrugIntakeDuration=(TextView)listViewItem.findViewById(R.id.DrugIntakeDurationTVLstItm);
        TextView tvDosingUnit=(TextView)listViewItem.findViewById(R.id.DosingUnitTVLstItm);
        TextView tvDrugInstruction=(TextView)listViewItem.findViewById(R.id.DrugInstructionTVLstItm);

        Medicine medicine=medicineList.get(position);



        String DrugNm="Drug Name: "+medicine.getDrugName();
        String Dosge="Dose: "+medicine.getDrugDosage();
        String DrugType="Drug Type: "+medicine.getDrugType();
        String DrugIntkDuration="Intake Duration(Days): "+medicine.getDrugIntakeDuration();
        String DosingUnit="Dosing unit: "+medicine.getDrugDoseUnit();
        String DrugInstrctn="Drug Instruction: "+medicine.getDrugInstruction();


        tvDrugName.setText(DrugNm);
        tvDosage.setText(Dosge);
        tvDrugType.setText(DrugType);
        tvDrugIntakeDuration.setText(DrugIntkDuration);
        tvDosingUnit.setText(DosingUnit);
        tvDrugInstruction.setText(DrugInstrctn);

        return listViewItem;


    }




}
