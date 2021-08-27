package com.example.dekutteleconsult.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dekutteleconsult.DataModel.Chat;
import com.example.dekutteleconsult.R;
import com.example.dekutteleconsult.StudentChatActivity;
import com.example.dekutteleconsult.ViewModel.ChatViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>  {
    //adapter class

public  static  final int MSG_TYPE_RIGHT=1;
public  static  final int MSG_TYPE_LEFT=0;


    private Context mContext;
    private List<Chat> mChats;
    private String ImageURL;

    FirebaseUser fuser;

    //selecting chat
    boolean isEnabled=false;
    boolean isSelectAll=false;

    boolean itemselected=false;

    ChatViewModel chatViewModel;

    ArrayList<Chat> selectList=new ArrayList<>();
    Activity activity;


    public ChatAdapter(Context mContext, List<Chat> mChats, String ImageURL ) {
        this.mContext = mContext;
        this.mChats = mChats;
        this.ImageURL = ImageURL;


        if (mContext.getClass().equals(StudentChatActivity.class)){
            activity=((StudentChatActivity)mContext);
        }

    }

    public ChatAdapter(Activity activity,Context mContext, List<Chat> mChats, String ImageURL ) {
        //constructor to help in deletion of chat with action mode
        this.activity=activity;
        this.mContext = mContext;
        this.mChats = mChats;
        this.ImageURL = ImageURL;

    }



    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        //INITIALIZE view model to perform chat selection
        chatViewModel= ViewModelProviders.of((FragmentActivity) activity).get(ChatViewModel.class);










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

        String  rawtime=chat.getTimestamp();
//convert timestamp to dd/mm/yyyy hh:mm am/pm
        Calendar cal=Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(rawtime));
      String  dateTime= DateFormat.format("dd/MM/yyyy hh:mm aa",cal).toString();
        holder.timeTv.setText(dateTime);






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





//listen chat is longclicked
       holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
             //check if already clicked
             if (!isEnabled){
                 //when action mode is not enabled
                 //initialize action mode to replace UI for action
                 ActionMode.Callback callback = new ActionMode.Callback() {
                     @Override
                     public boolean onCreateActionMode(ActionMode actionmode, Menu menu) {
                       //INITIALIZE MENU INFATER here
                         MenuInflater menuInflater=actionmode.getMenuInflater();
                         //inflate menu
                         menuInflater.inflate(R.menu.selectedchatmenu,menu);


                         return true;
                     }

                     @Override
                     public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        //when action mode is prepared set isEnable true
                         isEnabled=true;
                         //create method to pass viewholder
                         clickItem(holder);

//observer method on get text set below

                              //=new ChatViewModel();
                      chatViewModel.getMutableLiveData().observe((LifecycleOwner) activity, new Observer<String>() {
                          @Override
                          public void onChanged(String s) {
                             //CHANGE TEXT ON ACTION MODE WHEN IT CHANGES
//SET TEXT ON ACTION MODE TITLE
                      mode.setTitle(String.format("%s chats selected",s));


                          }
                      });


                         return true;
                     }

                     @Override
                     public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                         //when action mode item is clicked
                         //get item id of clicked menu item
                         int id=item.getItemId();

                         switch (id){
                             case R.id.menu_delete:
                                 //use for each loop to delete all selected chats
                               showChatDeletionDialog();

                                 for (Chat s: selectList){
                              deleteChatFromFirebase(s);
                                     //firebase functions to delete chats here
                                 }

                                 mode.finish();

                                 break;


                         }



                         return true;
                     }

                     @Override
                     public void onDestroyActionMode(ActionMode mode) {

                         //set isEnable false
                         isEnabled=false;
                         //set ISselect all
                         isSelectAll=false;

                         selectList.clear();

                         notifyDataSetChanged();
                     }
                 };

//start action mode here
                 ((AppCompatActivity) v.getContext()).startActionMode(callback);

             }
             else {
                 //when action mode is already enabled
            //call this method below
                 clickItem(holder);

             }

               return true;
           }
       });



holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //check action mode
        if (isEnabled){
            //if action mode is enabled
            //call method

            clickItem(holder);


        }else {
            //if action mode is not enabled
//display toast
            Toast.makeText(mContext,"You clicked this chat. You can longpress it to delete",Toast.LENGTH_LONG).show();


        }


    }
});











    }

    private void deleteChatFromFirebase(Chat s) {
        showChatDeletionDialog();

    }

    private void showChatDeletionDialog() {
        MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(mContext);
        builder.setTitle("Delete Chats");
        builder.setMessage("Are you sure you want to delete selected chats ?");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // deleteFromDB(s);
            }
        });


    }

    private void deleteFromDB(Chat s) {
    }

    private void clickItem(ViewHolder holder) {
        //get and highlight the selected item value
        //String s=selectList.get(holder.getAdapterPosition());

       Chat s=mChats.get(holder.getAdapterPosition());

if (holder.selectedCheckImage.getVisibility()==View.GONE){

       holder.selectedCheckImage.setVisibility(View.VISIBLE);
        holder.itemView.setBackgroundColor(Color.LTGRAY);
selectList.add(s);
}else {

    //if item is selected
    //HIDE IMAGE AND UNSELECT
    holder.selectedCheckImage.setVisibility(View.GONE);

    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
selectList.remove(s);
}

chatViewModel.setText(String.valueOf(selectList.size()));


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
        public TextView timeTv;

        public ImageView selectedCheckImage;


        public ViewHolder(@NonNull View itemView) {
            //VIEWHolder constructor
            super(itemView);


            actualMessage= itemView.findViewById(R.id.actualMessageTv);
            profile_pic=  itemView.findViewById(R.id.ItmProfilePic);
            seenTv=  itemView.findViewById(R.id.seenTv);
            timeTv=  itemView.findViewById(R.id.timeTv);

            selectedCheckImage=itemView.findViewById(R.id.selectedChatCheckImgVw);

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
