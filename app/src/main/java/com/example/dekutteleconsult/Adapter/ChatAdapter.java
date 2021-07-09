package com.example.dekutteleconsult.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dekutteleconsult.Model.Chat;
import com.example.dekutteleconsult.Model.User;
import com.example.dekutteleconsult.R;
import com.example.dekutteleconsult.StudentChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>  {
    //adapter class

public  static  final int MSG_TYPE_RIGHT=1;
public  static  final int MSG_TYPE_LEFT=0;


    private Context mContext;
    private List<Chat> mChats;
    private String ImageURL;


    FirebaseUser fuser;

    public ChatAdapter(Context mContext, List<Chat> mChats, String ImageURL ) {
        this.mContext = mContext;
        this.mChats = mChats;
        this.ImageURL = ImageURL;

    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==MSG_TYPE_RIGHT){
            //inflate right and left chats respectively
            View view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false);

            return new ChatAdapter.ViewHolder(view);
        }else{
            View view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false);

            return new ChatAdapter.ViewHolder(view);

        }




    }




    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
Chat chat=mChats.get(position);
holder.actualMessage.setText(chat.getMessage());

if (ImageURL.equals("default")){

    //holder.profile_pic.setImageResource(R.mipmap.ic_launcher_round);
}else {
   // Glide.with(mContext).load(ImageURL).into(holder.profile_pic);

}

if (position==mChats.size()-1){
    //check the last chat
  if (chat.isIsseen()){
    holder.seenTv.setText("Seen");
  }else {
    holder.seenTv.setText("Delivered");

  }


} else {
    holder.seenTv.setVisibility(View.GONE);
}



    }

    @Override
    public int getItemCount() {
        //return the size of list of users as the item count
        return mChats.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView actualMessage;
        public CircleImageView profile_pic;
        public TextView seenTv;


        public ViewHolder(@NonNull View itemView) {
            //VIEWHolder constructor
            super(itemView);


            actualMessage= itemView.findViewById(R.id.actualMessageTv);
            profile_pic=  itemView.findViewById(R.id.ItmProfilePic);
            seenTv=  itemView.findViewById(R.id.seenTv);
        }

        public TextView getTextView(){
            return actualMessage;
        }
        public CircleImageView getCircleImageView(){
            return profile_pic;
        }


    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();

        if (!(mChats.isEmpty())) {
//check if chat list is empty

            if (mChats.get(position).getSender().equals(fuser.getUid())){
                return MSG_TYPE_RIGHT;
            } else {

                return MSG_TYPE_LEFT;
            }

        }else {
            Toast.makeText(mContext,"There are no chats added to list",Toast.LENGTH_LONG).show();

        }





               return super.getItemViewType(position);
    }
}
