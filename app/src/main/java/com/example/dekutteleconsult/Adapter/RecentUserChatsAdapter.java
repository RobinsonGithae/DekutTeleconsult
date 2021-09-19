package com.example.dekutteleconsult.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dekutteleconsult.DataModel.Chat;
import com.example.dekutteleconsult.DataModel.User;
import com.example.dekutteleconsult.R;
import com.example.dekutteleconsult.StudentChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecentUserChatsAdapter extends RecyclerView.Adapter<RecentUserChatsAdapter.ViewHolder>  {
//adapter class
private Context mContext;
private List<User>mUsersList;
private boolean ischat;

String lastMessage,dateTime;

    public RecentUserChatsAdapter(Context mContext, List<User> mUsersList, boolean isChat) {
        this.mContext = mContext;
        this.mUsersList = mUsersList;
        this.ischat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.recentuserchatitem,parent,false);

        return new RecentUserChatsAdapter.ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       final User user=mUsersList.get(position);

    holder.username.setText(user.getUsername());




//here
        if (user.getImageURL().equals("default")){
//holder.profile_pic.setImageResource(R.mipmap.ic_launcher_round);
            holder.profile_pic.setImageResource(R.drawable.graduate);

       }

       else {
Glide.with(mContext).load(user.getImageURL()).into(holder.profile_pic);
        }

//here

          if (ischat){
              //get current userid and tv from viewholder and pass it to the method if they have chatted
CheckForLatestMessage(user.getId(),holder.recentchatTV,holder.recentchatTimeTV);

                   }
          else {
              //if they have not chatted make recent chat tv invisibible.
              holder.recentchatTV.setVisibility(View.GONE);
              holder.recentchatTimeTV.setVisibility(View.GONE);
          }


        if (ischat){
            if (user.getStatus().equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);

            }else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);

            }
        }else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);

        }








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
        private TextView recentchatTV;
        private   TextView recentchatTimeTV;

        public ViewHolder(@NonNull View itemView) {
            //VIEWHolder constructor
            super(itemView);


            username= itemView.findViewById(R.id.UserItemUsername);
            profile_pic= itemView.findViewById(R.id.ItmProfilePic);
            img_on= itemView.findViewById(R.id.img_on);
            img_off= itemView.findViewById(R.id.img_off);
            recentchatTV=itemView.findViewById(R.id.UserRecentMessageTV);;
            recentchatTimeTV=itemView.findViewById(R.id.UserRecentMessageTimeTV);



        }

        //viewholder getters
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
        public TextView getTextViewRecentChat(){
            return recentchatTV;
        }
        public TextView getTextViewRecentTimestamp(){
            return recentchatTimeTV;
        }
    }


    public void CheckForLatestMessage(String userid, TextView LatestchatTV,TextView LatestChatTimeTV){
lastMessage="default";
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference chatReference= FirebaseDatabase.getInstance().getReference("chats");
        //sync data with firebase
        chatReference.keepSynced(true);

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chat chat=dataSnapshot.getValue(Chat.class);

                    assert chat != null;
                    assert firebaseUser != null;
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) || chat.getReceiver().equals(userid)&& chat.getSender().equals(firebaseUser.getUid())){

                    lastMessage=chat.getMessage();


                  String  rawtime=chat.getTimestamp();
//convert timestamp to dd/mm/yyyy hh:mm am/pm
                        Calendar cal=Calendar.getInstance(Locale.ENGLISH);
                        cal.setTimeInMillis(Long.parseLong(rawtime));
                         dateTime= DateFormat.format("dd/MM/yyyy hh:mm aa",cal).toString();


                    }

                }


                switch (lastMessage){
                    case "default":
                        LatestchatTV.setText(" ");

                        break;

                    default:
                        LatestchatTV.setText(lastMessage);

                        LatestChatTimeTV.setText(dateTime);


                        break;
                }

                lastMessage="default";

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }



}
