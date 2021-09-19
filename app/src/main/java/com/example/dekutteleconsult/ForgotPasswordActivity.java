package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    Button SendresetLinkBtn;
    EditText ResetEmailET;
    ProgressBar progBar;


    public boolean ResetEmailInputValidator(){


        if (ResetEmailET.getText().toString().trim().isEmpty()){
            ResetEmailET.setError("Reset Email must not be Empty");
            return false;
        }
        return true;


    }

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
        setContentView(R.layout.activity_forgot_password);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        ResetEmailET=(EditText)findViewById(R.id.ETResetEmail);
        SendresetLinkBtn=(Button)findViewById(R.id.btnSendPassRstLink);
        progBar=(ProgressBar)findViewById(R.id.ProgBarResetPassword);
        progBar.setVisibility(View.INVISIBLE);

        fAuth=FirebaseAuth.getInstance();

        SendresetLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progBar.setVisibility(View.VISIBLE);

                if (ResetEmailInputValidator()==true){
                    //proceed to password reset process
                    fAuth.sendPasswordResetEmail(ResetEmailET.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progBar.setVisibility(View.GONE);


                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"A reset Link has been sent to your Email",Toast.LENGTH_LONG).show();
startActivity(new Intent(ForgotPasswordActivity.this,ForgotPasswordSuccessActivity.class));

                            }else {
                                Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_LONG).show();


                            }
                        }
                    });




                }



            }
        });


    }
}
