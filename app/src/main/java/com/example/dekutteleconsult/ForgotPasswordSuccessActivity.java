package com.example.dekutteleconsult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ForgotPasswordSuccessActivity extends AppCompatActivity {

ImageView IconReset;
Button bg;
   public void OpenDefaultEmailAppInbox(){
try {
    Intent intent =new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
    startActivity(intent);
}
catch (Exception e){
    e.printStackTrace();
}




   }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_success);


        IconReset=(ImageView)findViewById(R.id.ResetEmailIcon);
       IconReset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               OpenDefaultEmailAppInbox();

           }
       });
    }
}
