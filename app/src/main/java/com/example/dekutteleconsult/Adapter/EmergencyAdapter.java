package com.example.dekutteleconsult.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dekutteleconsult.EmergencyMapTrackActivity;
import com.example.dekutteleconsult.DataModel.Emergency;
import com.example.dekutteleconsult.R;

import java.util.List;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ViewHolder>  {
    //adapter class
    private Context mContext;
    private List<Emergency> mEmergencyList;
    //private boolean isActive;

    public EmergencyAdapter(Context mContext, List<Emergency>mEmergencyList) {
        this.mContext = mContext;
        this.mEmergencyList=mEmergencyList;
        //this.isActive = isActive;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.emergency_item,parent,false);

        return new EmergencyAdapter.ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


       Emergency emergency=mEmergencyList.get(position);

       //if (emergency.getLongitudeAddress()!=null && emergency.getLatitudeAddress()!=null && emergency.getEmergencyStatus()!=null){
String status="Status: "+emergency.getEmergencystatus();

        holder.Emgncystatus.setText(status);

        Double LatAddress=emergency.getLatitude();
        Double LongAddress=emergency.getLongitude();

        String LatA ="Lat: "+ LatAddress;
        String LongA =" Long: "+ LongAddress;
        String fullAddress=LatA+" "+LongA;
        holder.LATLONG.setText(fullAddress);

        String emergencyID="ID: "+emergency.getEmergencyID();

        holder.emergencyID.setText(emergencyID);
        holder.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, EmergencyMapTrackActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Toast.makeText(mContext,"clicked"+emergency.getEmergencystatus(),Toast.LENGTH_LONG).show();
                Bundle b=new Bundle();


                b.putDouble("LatValue",emergency.getLatitude());
                b.putDouble("LongValue",emergency.getLongitude());

                intent.putExtras(b);


                mContext.startActivity(intent);

            }
        });


       }

    //}


    @Override
    public int getItemCount() {
        //return the size of list of users as the item count
        return mEmergencyList.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView emergencyID;
        public TextView LATLONG;
        private Button track;
        private TextView Emgncystatus;


        public ViewHolder(@NonNull View itemView) {
            //VIEWHolder constructor
            super(itemView);


            emergencyID= itemView.findViewById(R.id.emergencyID);
            LATLONG= itemView.findViewById(R.id.emergencyLatLong);
            track= itemView.findViewById(R.id.btnTrackEmergency);
            Emgncystatus= itemView.findViewById(R.id.emergencyStatus);

        }

        public TextView getTextViewEmergencyID(){
            return emergencyID;
        }
        public TextView getTextViewLATLONG(){
            return LATLONG;
        }
        public TextView getTextViewEmergencyStatus(){
            return Emgncystatus;
        }
        public Button getButtonTrack(){
            return track;
        }




    }


}
