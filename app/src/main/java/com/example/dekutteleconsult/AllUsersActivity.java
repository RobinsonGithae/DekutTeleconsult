package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.dekutteleconsult.Adapter.UserAdapter;
import com.example.dekutteleconsult.Model.Chatlist;
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

public class AllUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        recyclerView = (RecyclerView) findViewById(R.id.UsersRecyclervw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mUsers=new ArrayList<>();

        readUsers();





    }

    private void readUsers(){
       // mUsers=new ArrayList<>();
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
               mUsers.clear();


               for (DataSnapshot snapshot:datasnapshot.getChildren()){

                   User user=snapshot.getValue(User.class);


                   assert user!=null;
                   assert firebaseUser!=null;
             //      if (!user.getId().equals(firebaseUser.getUid())){
                       mUsers.add(user);
                 //  }

               }

               userAdapter=new UserAdapter(getApplicationContext(),mUsers,true);
               recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
