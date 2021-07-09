package com.example.dekutteleconsult.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dekutteleconsult.Model.User;
import com.example.dekutteleconsult.R;
import com.example.dekutteleconsult.StudentChatActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>  {
//adapter class
private Context mContext;
private List<User>mUsersList;
private boolean ischat;

    public UserAdapter(Context mContext, List<User> mUsersList, boolean isChat) {
        this.mContext = mContext;
        this.mUsersList = mUsersList;
        this.ischat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.useritem,parent,false);

        return new UserAdapter.ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user=mUsersList.get(position);

    holder.username.setText(user.getUsername());


        //if (user.getImageURL().equals("default")){
//holder.profile_pic.setImageResource(R.mipmap.ic_launcher_round);
     //  }
      // else {
//
//
//Glide.with(mContext).load(user.getImageURL()).into(holder.profile_pic);
       // }





//
//        if (ischat){
//            if (user.getStatus().equals("online")){
//                holder.img_on.setVisibility(View.VISIBLE);
//                holder.img_off.setVisibility(View.GONE);
//
//            }else {
//                holder.img_on.setVisibility(View.GONE);
//                holder.img_off.setVisibility(View.VISIBLE);
//
//            }
//        }else {
//            holder.img_on.setVisibility(View.GONE);
//            holder.img_off.setVisibility(View.GONE);
//
//        }








        holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(mContext, StudentChatActivity.class);
        intent.putExtra("userid",user.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);



    }
});

    }


    @Override
    public int getItemCount() {
        //return the size of list of users as the item count

        return mUsersList.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        private CircleImageView profile_pic;
        private CircleImageView img_on;
        private CircleImageView img_off;


        public ViewHolder(@NonNull View itemView) {
            //VIEWHolder constructor
            super(itemView);


            username= itemView.findViewById(R.id.UserItemUsername);
            profile_pic= itemView.findViewById(R.id.ItmProfilePic);
            img_on= itemView.findViewById(R.id.img_on);
            img_off= itemView.findViewById(R.id.img_off);

        }

        public TextView getTextView(){
            return username;
        }
        public CircleImageView getCircleImageView(){
            return profile_pic;
        }
        public CircleImageView getCircleImageViewOn(){
            return img_on;
        }
        public CircleImageView getCircleImageViewOff(){
            return img_off;
        }




    }


}
