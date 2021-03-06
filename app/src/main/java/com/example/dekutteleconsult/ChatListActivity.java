package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.dekutteleconsult.Adapter.UserAdapter;
import com.example.dekutteleconsult.DataModel.Chatlist;
import com.example.dekutteleconsult.DataModel.User;
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

public class ChatListActivity extends AppCompatActivity {
    private RecyclerView ChatListRcycler;
    private UserAdapter userAdapter;
    private List<User> mUsers;
    private List<Chatlist> userslist;
    FirebaseUser fUser;
    DatabaseReference reference;

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
        setContentView(R.layout.activity_chat_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ChatListRcycler = (RecyclerView) findViewById(R.id.ChatListRecyclervw);
        ChatListRcycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        ChatListRcycler.setLayoutManager(llm);

        fUser= FirebaseAuth.getInstance().getCurrentUser();

       userslist = new ArrayList<>();

        reference= FirebaseDatabase.getInstance().getReference("chatlist").child(fUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userslist.clear();

                for (DataSnapshot dtasnapshot : snapshot.getChildren()) {
                    Chatlist chatlist = dtasnapshot.getValue(Chatlist.class);
                    userslist.add(chatlist);

                }
                chatList();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void chatList(){
        mUsers=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Users");
reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
        mUsers.clear();

        for (DataSnapshot snapshot:datasnapshot.getChildren()){
            User user=snapshot.getValue(User.class);

            for (Chatlist chatlist:userslist){

                assert user != null;
                if (user.getId().equals(chatlist.getId())){
                    mUsers.add(user);

                }




            }


        }



userAdapter=new UserAdapter(getApplicationContext(),mUsers,true);
   ChatListRcycler.setAdapter(userAdapter);


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


    }


}
