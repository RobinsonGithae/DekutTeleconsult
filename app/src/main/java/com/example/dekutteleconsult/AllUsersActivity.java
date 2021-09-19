package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.dekutteleconsult.Adapter.UserAdapter;
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

public class AllUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser fUser;

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
        setContentView(R.layout.activity_all_users);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.UsersRecyclervw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mUsers=new ArrayList<>();

        readUsers();





    }

    private void readUsers(){
       // mUsers=new ArrayList<>();
       // FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        fUser= FirebaseAuth.getInstance().getCurrentUser();
        assert fUser != null;
        final String myid=fUser.getUid();


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
               mUsers.clear();


               for (DataSnapshot snapshot:datasnapshot.getChildren()){

                   User user=snapshot.getValue(User.class);


                   assert user!=null;


                  if (!(user.getId().equals(myid))){
                       mUsers.add(user);
                   }

               }
//remember to set ischat to false
               userAdapter=new UserAdapter(getApplicationContext(),mUsers,true);
               recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
