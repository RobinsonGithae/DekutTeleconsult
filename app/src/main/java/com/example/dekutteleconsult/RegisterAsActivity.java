package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class RegisterAsActivity extends AppCompatActivity {


    Button RegisterAsStudentBtn,RegisterAsDoctorBtn;


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
        setContentView(R.layout.activity_register_as);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);




        RegisterAsStudentBtn=(Button) findViewById(R.id.BtnAsStudentSignup);
        RegisterAsDoctorBtn=(Button) findViewById(R.id.BtnAsDoctorSignup);


        RegisterAsDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterAsActivity.this,DoctorSignupActivity.class);
                startActivity(intent);
            }
        });

        RegisterAsStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterAsActivity.this,StudentSignupActivity.class);
                startActivity(intent);
            }
        });



    }
}
