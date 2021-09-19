package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.dekutteleconsult.Adapter.RecentUserChatsAdapter;
import com.example.dekutteleconsult.Adapter.UserAdapter;
import com.example.dekutteleconsult.DataModel.User;
import com.example.dekutteleconsult.Notification.MyFirebaseIdService;
import com.example.dekutteleconsult.Notification.Token;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllStudentChatListActivity extends AppCompatActivity {

    FloatingActionButton addUser2ChatFAB;


    private RecyclerView studChatListRcycler;
    private RecentUserChatsAdapter recentUserChatsAdapter;
    private List<User> mUsers;

//    FirebaseUser fbaseUser;
//    DatabaseReference reference;

  //  private List<String> usersList;




    public void updateToken(String token){
        //update notification token here
        //reference is tokenreference
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1=new Token(token);
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
        setContentView(R.layout.activity_all_student_chat_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        addUser2ChatFAB=findViewById(R.id.ChooseUserToChatFAB);
        addUser2ChatFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllStudentChatListActivity.this,AllUsersActivity.class));

            }
        });







        studChatListRcycler = (RecyclerView) findViewById(R.id.studentChatListRecycler);
        studChatListRcycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        studChatListRcycler.setLayoutManager(llm);

        // fbaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        //HERE
//studChatListRcycler.setLayoutManager(new LinearLayoutManager(this));
        //     studChatListRcycler.setAdapter(userAdapter);
//        //HERE

        mUsers = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference("chats");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                usersList.clear();
//
//                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
//                    Chat chat = snapshot.getValue(Chat.class);
//
//                    if (chat.getSender().equals(fbaseUser.getUid())) {
//                        usersList.add(chat.getReceiver());
//                    }
//
//                    if (chat.getReceiver().equals(fbaseUser.getUid())) {
//                        usersList.add(chat.getSender());
//                    }
//
//                }
//
        readChatUsersFromFirebase();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//          readChatUsersFromFirebase();
//    }
//
////HERE
//
//     FirebaseUser fbaseUser= FirebaseAuth.getInstance().getCurrentUser();
//    DatabaseReference reference=FirebaseDatabase.getInstance().
//
//    getReference("Users");
//        reference.addValueEventListener(new
//
//    ValueEventListener() {
//        @Override
//        public void onDataChange (@NonNull DataSnapshot snapshot){
//            mUserlist.clear();
//
//            for (DataSnapshot dtasnapshot : snapshot.getChildren()) {
//                User user = dtasnapshot.getValue(User.class);
//
//                assert user != null;
//                assert fbaseUser != null;
//
//                if (!user.getId().equals(fbaseUser.getUid())) {
//
//                    mUserlist.add(user);
//
//                }
//            }
//
//            userAdapter = new UserAdapter(getApplicationContext(), mUserlist, false);
//            studChatListRcycler.setAdapter(userAdapter);
//
//
//        }
//
//        @Override
//        public void onCancelled (@NonNull DatabaseError error){
//
//        }
//    });
//
//}
//
////here


        //update notification token here
       // updateToken(FirebaseInstanceId.getInstance().getToken());

    }

     private void readChatUsersFromFirebase() {

        mUsers=new ArrayList<>();
       FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
       DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                mUsers.clear();

                for (DataSnapshot snapshot:datasnapshot.getChildren()){

                    User user=snapshot.getValue(User.class);


                    assert user != null;
              assert firebaseUser != null;
//
               if (!user.getId().equals(firebaseUser.getUid())) {
//
                   mUsers.add(user);
//
               }
            }
//
           recentUserChatsAdapter = new RecentUserChatsAdapter(getApplicationContext(), mUsers, true );
            studChatListRcycler.setAdapter(recentUserChatsAdapter);
//
//
       }
//






//display one user
//                    for (String id: usersList){
//                        assert user != null;
//                        if (user.getId().equals(id)){
//                            if (mUsers.size()!=0){
//
//                            for (User user1:mUsers){
//                                if (!user.getId().equals(user1.getId())){
//
//                                    mUsers.add(user);
//                                }
//
//                            }
//                            } else {
//                                mUsers.add(user);
//                            }
//                        }
//                    }




             //   }


            //    userAdapter=new UserAdapter(getApplicationContext(),mUsers,true);
            //    studChatListRcycler.setAdapter(userAdapter);


         //   }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




     }
}
