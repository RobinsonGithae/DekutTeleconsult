package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chaos.view.PinView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class PinLoginActivity extends AppCompatActivity {
    ImageButton lockImgBtn;
    Button BtnPinLogin;
    Intent intent;
    PinView LgnPinView;
    String EnteredPin,storedPin;
    String fileName="pinFile";


    public void ValidatePinThenLogin(){

        if (EnteredPinIsValid()){
            LoginWithPin();
        }

    }






    public void LoginWithPin(){

       EnteredPin= LgnPinView.getText().toString();

       if(EnteredPin.equals(readPinFromFile())){
           // String LoginAS=getIntent().getStringExtra("LoginAs");


           startActivity(new Intent(PinLoginActivity.this, StudentDrawerActivity.class));

       }
       else {
           Toast.makeText(this,"PIN Is incorrect or does not exist",Toast.LENGTH_LONG).show();
       }




    }








public String readPinFromFile(){


        try {
            FileInputStream fin=openFileInput(fileName);
            InputStreamReader inputStreamReader=new InputStreamReader(fin);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder=new StringBuilder();
            String line=null;

            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            fin.close();
            inputStreamReader.close();

           // Toast.makeText(this,"PIN READ AS"+stringBuilder.toString(),Toast.LENGTH_LONG).show();
           storedPin=stringBuilder.toString();
            return storedPin;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


     return storedPin;
}





    public  Boolean EnteredPinIsValid(){

        if (TextUtils.isEmpty(LgnPinView.getText().toString())){
            LgnPinView.setError("PIN can`t be empty");
            return false;
        }


        if ((LgnPinView.getText().toString().length()<4)){
            LgnPinView.setError("PIN must be  4 Digits");
            return false;
        }



        return true;
    }


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
        setContentView(R.layout.activity_pin_login);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);





        lockImgBtn=(ImageButton)findViewById(R.id.padlockImgBtn);
        BtnPinLogin=(Button)findViewById(R.id.pinLgnBtn);
        LgnPinView=(PinView) findViewById(R.id.firstPinView);


        lockImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PinLoginActivity.this, PinSettingsActivity.class));
            }
        });


        BtnPinLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           ValidatePinThenLogin();



            }
        });



    }
}
