package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.dekutteleconsult.Adapter.UserAdapter;
import com.example.dekutteleconsult.Model.Chat;
import com.example.dekutteleconsult.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllStudentChatListActivity extends AppCompatActivity {
    private RecyclerView studChatListRcycler;
    private UserAdapter userAdapter;
    private List<User> mUsers;

//    FirebaseUser fbaseUser;
//    DatabaseReference reference;

  //  private List<String> usersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student_chat_list);

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
           userAdapter = new UserAdapter(getApplicationContext(), mUsers, false);
            studChatListRcycler.setAdapter(userAdapter);
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