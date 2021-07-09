package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class LoginAsActivity extends AppCompatActivity {
    Button AsDoctorLoginBtn,AsStudentLoginBtn;
    TextView CreateAccTV, LoginTV;

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
        setContentView(R.layout.activity_login_as);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);




LoginTV=findViewById(R.id.LogInAsTv);
        AsDoctorLoginBtn=findViewById(R.id.btnLoginAsDoctor);
        AsStudentLoginBtn=findViewById(R.id.btnLoginAsStudent);
        CreateAccTV=findViewById(R.id.tvCreate);


       LoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAsActivity.this,StudentDrawerActivity.class));

            }

        });







        AsDoctorLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAsActivity.this,DoctorLoginActivity.class));

            }

        });

        AsStudentLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAsActivity.this,LoginWithActivity.class));

            }
        });

        CreateAccTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginAsActivity.this,RegisterAsActivity.class);
                startActivity(i);
            }
        });






    }
}
