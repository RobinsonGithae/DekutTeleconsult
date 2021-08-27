package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.dekutteleconsult.DataModel.Pin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class PinSettingsActivity extends AppCompatActivity {
    String fileName="pinFile";
    Pin userpin;
    Button createpinBtn,DeletePinBtn,ResetPinBtn;
    String pinValueInput;
    PinView settingsPinview;
    Context myContext;

    private void readPinFile(){
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
            userpin.setPinValue(stringBuilder.toString());
            Toast.makeText(this,"PIN READ AS"+stringBuilder.toString(),Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }





    private void SavePinFile(){
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            pinValueInput=settingsPinview.getText().toString();
            fos.write(pinValueInput.getBytes());
            fos.close();
            userpin.setPinValue(pinValueInput);
            Toast.makeText(this, "PIN SAVED AS" + pinValueInput, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    private void clearPinFile(){
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            pinValueInput=" ";
            fos.write(pinValueInput.getBytes());
            fos.close();
            userpin.setPinValue(pinValueInput);
            Toast.makeText(this, "PIN CLEARED TO" + pinValueInput, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }


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
        setContentView(R.layout.activity_pin_settings);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);




        userpin=new Pin();

        createpinBtn=(Button)findViewById(R.id.saveNewPinBtn);
        DeletePinBtn=(Button)findViewById(R.id.deletePinBtn);
        ResetPinBtn=(Button)findViewById(R.id.ResetPinBtn);
        settingsPinview =(PinView)findViewById(R.id.secondPinView);
        pinValueInput= Objects.requireNonNull(settingsPinview.getText()).toString();



        createpinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              SavePinFile();




            }
        });



        DeletePinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPinFile();

            }
        });


        ResetPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
readPinFile();
            }
        });





    }
}
