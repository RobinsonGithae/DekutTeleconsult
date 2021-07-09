package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class ConsultViaActivity extends AppCompatActivity {
    Button consultViaChat,consultViaVideoCall;

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
        setContentView(R.layout.activity_consult_via);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



        consultViaChat=(Button) findViewById(R.id.btnConsultWithChat);
        consultViaVideoCall=(Button) findViewById(R.id.btnConsultWithVideoCall);

        consultViaChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(ConsultViaActivity.this,StudentChatActivity.class));
                startActivity(new Intent(ConsultViaActivity.this,AllUsersActivity.class));

            }
        });




        consultViaVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultViaActivity.this,StudentVideoCallActivity.class));


            }
        });




    }
}
