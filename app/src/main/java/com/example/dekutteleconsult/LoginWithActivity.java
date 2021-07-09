package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class LoginWithActivity extends AppCompatActivity {
    Button lgnWithPasswordBtn,lgnWithPinBtn;
    Intent LoginwithIntent;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);





        lgnWithPasswordBtn=(Button)findViewById(R.id.withPasswordBtn);
        lgnWithPinBtn=(Button)findViewById(R.id.withPinBtn);

        lgnWithPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String role=getIntent().getStringExtra("UserRole");
//                assert role != null;
//                if (role.equals("Doctor")){
//                    startActivity(new Intent(LoginWithActivity.this, DoctorLoginActivity.class));
//
//                } else {
//                    startActivity(new Intent(LoginWithActivity.this, StudentLoginActivity.class));
//
//
//                }



                startActivity(new Intent(LoginWithActivity.this, StudentLoginActivity.class));





            }
        });




        lgnWithPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(LoginWithActivity.this, PinLoginActivity.class));

            }
        });



    }
}
