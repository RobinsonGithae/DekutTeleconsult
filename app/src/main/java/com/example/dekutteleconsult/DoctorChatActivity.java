package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dekutteleconsult.Adapter.ChatAdapter;
import com.example.dekutteleconsult.Adapter.DocChatAdapter;
import com.example.dekutteleconsult.DataModel.Chat;
import com.example.dekutteleconsult.DataModel.Doctor;
import com.example.dekutteleconsult.DataModel.User;
import com.example.dekutteleconsult.Notification.Token;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorChatActivity extends AppCompatActivity {

    CircleImageView CIVprofilePic;
    TextView UsernameTV;
    EditText StudChatInputET;
    ImageButton studChatBtnSend,videoCallBtn;
    Intent ChatIntent;


    FirebaseUser fUser;

    FirebaseDatabase fbDatabase;
    DatabaseReference DbReference;

    DocChatAdapter docchatAdapter;
    List<Chat> mchats;
    RecyclerView chatsRecyclrVw;



    ValueEventListener seenListener;
//added
    //String userid;






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.chatmenuitems,menu);

        MenuItem item=menu.findItem(R.id.searchChat);

        SearchView chatSearchView=(SearchView)item.getActionView();
        chatSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchStudentChats(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                searchStudentChats(newText);
                return false;
            }
        });

        return true;
    }


    public void searchStudentChats(String srchTxt){
        final String userid=ChatIntent.getStringExtra("userid");
        fUser= FirebaseAuth.getInstance().getCurrentUser();
        assert fUser != null;
        final String myid=fUser.getUid();

        //testing



        assert userid != null;
        DbReference=FirebaseDatabase.getInstance().getReference("Doctors").child(userid);
        DbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Doctor doctor=snapshot.getValue(Doctor.class);
                assert doctor != null;






                Query chatSrchQuery=FirebaseDatabase.getInstance().getReference("chats").orderByChild("message").startAt(srchTxt).endAt(srchTxt+"\uf8ff");

                chatSrchQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        Doctor doc=snapshot.getValue(Doctor.class);
                        assert doc != null;
                        String imageurl=doc.getImageURL();

                        //clear list if data changes/new chat added
                        mchats.clear();



                        for (DataSnapshot dataSnapshot:snapshot.getChildren() ){
                            //get all chats from databse
                            Chat chat=dataSnapshot.getValue(Chat.class);
                            assert chat != null;
                            if(chat.getReceiver().equals(myid)&& chat.getSender().equals(userid)|| chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){

                                //NOW get only add chat for the specific (receiver) user to the chats list
                                mchats.add(chat);
                            }
                            docchatAdapter=new DocChatAdapter(DoctorChatActivity.this,mchats,imageurl);
                            chatsRecyclrVw.setAdapter(docchatAdapter);

                        }






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }






    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //get option menu items here
        switch (item.getItemId()) {
            case R.id.itemvideocall:
                //do something
                Intent intent=new Intent(DoctorChatActivity.this, StudentVideoCallActivity.class);
                startActivity(intent);


                break;

            case R.id.itemprescribe:
                //do something
                Intent intent2=new Intent(DoctorChatActivity.this, AddPrescriptionActivity.class);
                startActivity(intent2);


                break;




            case R.id.clearChat:
                //clear chat

                break;

        }


        return super.onOptionsItemSelected(item);
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_chat);






        Toolbar studchattoolbar=findViewById(R.id.StudChattoolbar);
        setSupportActionBar(studchattoolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studchattoolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chatsRecyclrVw=findViewById(R.id.StudConversationRecyclerView);
        chatsRecyclrVw.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        chatsRecyclrVw.setLayoutManager(linearLayoutManager);


        CIVprofilePic= findViewById(R.id.profilePic);
        UsernameTV= findViewById(R.id.TVStudChatUsername);
        StudChatInputET= findViewById(R.id.ETStudchatInput);
        studChatBtnSend= findViewById(R.id.btnStudSendChat);
        videoCallBtn=findViewById(R.id.ImageBtnVideoCall);

        videoCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorChatActivity.this,StudentVideoCallActivity.class));
            }
        });

        ChatIntent=getIntent();
//final String userid="2pGndZ9uTNdufdnYcVWoru57yVr2";
        final String userid=ChatIntent.getStringExtra("userid");
        fUser= FirebaseAuth.getInstance().getCurrentUser();



        studChatBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ChatMessage=StudChatInputET.getText().toString();
                //check if there is input in the chat area.
                if (!(ChatMessage.equals(""))){
                    //IF CHAT IS not empty send it.

                    sendChatmessage(fUser.getUid(),userid,ChatMessage);


                } else {

                    //TOAST or lock image btn if no chat
                    Toast.makeText(getApplicationContext(),"Please input  chat message",Toast.LENGTH_LONG).show();
                }

//clear chat input upon send by setting it to ""
                StudChatInputET.setText("");

            }
        });




//String useridd="bAnnIYE5gYX8oANX96Zp8AzeL9K2";
        assert userid != null;
        DbReference=FirebaseDatabase.getInstance().getReference("Doctors").child(userid);

        DbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Doctor doctor=snapshot.getValue(Doctor.class);
                assert doctor != null;
                UsernameTV.setText(doctor.getUsername());



                if (doctor.getImageURL().equals("default")){
                    //set default profile pic if none
                    CIVprofilePic.setImageResource(R.mipmap.ic_launcher_round);

                }
                else {
                    Glide.with(getApplicationContext()).load(doctor.getImageURL()).into(CIVprofilePic);

                }


                readMessagesFromFirebase(fUser.getUid(),userid, doctor.getImageURL());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        seenMessage(userid);

    }

    private void seenMessage(String userid){

        DbReference=FirebaseDatabase.getInstance().getReference("chats");
        seenListener=DbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot datasnapshot:snapshot.getChildren()){

                    Chat chat=datasnapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(fUser.getUid())&& chat.getSender().equals(userid)){
                        HashMap<String,Object>seenhashMap=new HashMap<>();
                        seenhashMap.put("isseen",true);
                        datasnapshot.getRef().updateChildren(seenhashMap);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void sendChatmessage(String sender, String receiver, String message ){

        fbDatabase=FirebaseDatabase.getInstance();

        DatabaseReference DbReference=fbDatabase.getReference();

        String timestamp=String.valueOf(System.currentTimeMillis());

        HashMap <String,Object> ChatHashmap=new HashMap<>();
        ChatHashmap.put("sender",sender);
        ChatHashmap.put("receiver",receiver);
        ChatHashmap.put("message",message);
        ChatHashmap.put("isseen",false);
        ChatHashmap.put("timestamp",timestamp);



        //set values of hashmap to db
        DbReference.child("chats").push().setValue(ChatHashmap).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });





//        final String userid=ChatIntent.getStringExtra("userid");
//        assert userid != null;
//
//
//        final DatabaseReference chatRef=FirebaseDatabase.getInstance().getReference("chatlist")
//        .child(fUser.getUid())
//        .child(userid);
//
//chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//        if (!snapshot.exists()){
//            chatRef.child("id").setValue(userid);
//        }
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//});
//
//







    }

    public void readMessagesFromFirebase(final String myid, final String userid, final String imageurl){
//userid is sender ID
        mchats=new ArrayList<>();
        DbReference=FirebaseDatabase.getInstance().getReference("chats");
        DbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //clear list if data changes/new chat added
                mchats.clear();



                for (DataSnapshot snapshot:datasnapshot.getChildren() ){
                    //get all chats from databse
                    Chat chat=snapshot.getValue(Chat.class);
                    assert chat != null;
                    if(chat.getReceiver().equals(myid)&& chat.getSender().equals(userid)|| chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){

                        //NOW get only add chat for the specific (receiver) user to the chats list
                        mchats.add(chat);
                    }
                    docchatAdapter=new DocChatAdapter(DoctorChatActivity.this,mchats,imageurl);
                    chatsRecyclrVw.setAdapter(docchatAdapter);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void status(String status){
        DbReference=FirebaseDatabase.getInstance().getReference("Doctors").child(fUser.getUid());
        //get online status
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("status",status);

      //  DbReference.updateChildren(hashMap);

    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        DbReference.removeEventListener(seenListener);
        status("offline");
    }
}







