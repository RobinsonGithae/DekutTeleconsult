package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dekutteleconsult.Utils.AppConstants;
import com.example.dekutteleconsult.Utils.SharedPrefUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class DoctorLoginActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    ProgressBar LoginProgressBar;
    EditText DocLgnEmailET, DocLgnPassET;
    Button LoginDocBtn;
    TextView DoccreateAccTV,DocForgotPsswrdTV;



    public  Boolean DoctorLoginValidator(){

        if (TextUtils.isEmpty(DocLgnEmailET.getText().toString())){
            DocLgnEmailET.setError("Enail can`t be empty");
            return false;
        }


        if (DocLgnPassET.getText().toString().trim()==null){
            DocLgnPassET.setError("password can`t be empty");
            return false;
        }


        if ((DocLgnPassET.getText().toString().length()<8)){
            DocLgnPassET.setError("Password must be atleast 8 characters");
            return false;
        }








        return true;
    }




    public void LoginUserWithFirebase(){
        {
            LoginProgressBar.setVisibility(View.VISIBLE);
            try {
                //try getting forms data and also try validating if credentilas are correct
                //get data. Trim removes trailing and ending whitespaces
                final String DocEmail=DocLgnEmailET.getText().toString().trim();
                final String DocPass=DocLgnPassET.getText().toString().trim();
                //FIREBASE LOGIN LOGIC HERE
                //use firebase signin method and also call the oncomplete listener to listen if login is successful and complete

                fAuth.getInstance().signInWithEmailAndPassword(DocEmail,DocPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if login success move user to drawer activity
                        //HIDE PROGRESSBAR AFTER SUCCESSFUL LOGIN
                        LoginProgressBar.setVisibility(View.GONE);


                        if(task.isSuccessful()){

                            //tell user using toast
                            Toast.makeText(getApplicationContext(),"Login Successful...WELCOME BACK",Toast.LENGTH_LONG).show();

                            //if login success also save login credentials  to shared preferences class i just created
                            SharedPrefUtils.saveEmail(DocEmail,getApplicationContext());
                            SharedPrefUtils.savePassword(DocPass,getApplicationContext());




                            Intent intent=new Intent(DoctorLoginActivity.this,DoctorDrawerActivity.class);
                            //pUt email in intent to be used in the next activity to welcome user with it.
                            intent.putExtra(AppConstants.KEY_EMAIL,DocEmail);
                            startActivity(intent);
                            finish();


                        }else {

                            Toast.makeText(getApplicationContext(), "Student Login Failed due to: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            LoginProgressBar.setVisibility(View.GONE);
                        }
                    }
                });





            }catch (Exception e){
//handle exception incase firebase login fails
                Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
            }

        }



    }




    public void checkSavedSharedPreference(){

        try {
            //check if user ever login befored and saved login data to preference
            //also check if fiebase has the user login. if both are true login to studdraweractivity
            if (((SharedPrefUtils.getEmail(getApplicationContext())!=null||!SharedPrefUtils.getEmail(getApplicationContext()).isEmpty())|| (fAuth.getCurrentUser()!=null)))
            {
                //if userdata exists in shared preference the open mainactivity
                Intent intent=new Intent(DoctorLoginActivity.this,DoctorDrawerActivity.class);
                startActivity(intent);
                finish();

            }
            //END OF TRYBLOCK

        } catch (Exception e){
            //TOAST ERROR
            Toast.makeText(getApplicationContext()," "+e.getMessage(),Toast.LENGTH_LONG).show();
            //user hasn't logged on before so continue with process of going to login
            //login user
            LoginUserWithFirebase();

        }

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
        setContentView(R.layout.activity_doctor_login);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        DocLgnPassET=(EditText)findViewById(R.id.ETDocloginPswd) ;
       DocLgnEmailET=(EditText)findViewById(R.id.ETDocLoginEmail) ;

        DoccreateAccTV=(TextView) findViewById(R.id.DocNotRegTv);

        DocForgotPsswrdTV=(TextView) findViewById(R.id.DocforgotPasswrdTv);

        LoginDocBtn=(Button) findViewById(R.id.btnDocLogin);
        LoginProgressBar =(ProgressBar) findViewById(R.id.DocLoginProgBar);

        LoginDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DoctorLoginValidator()==true){

                    checkSavedSharedPreference();
                }


            }
        });



        DoccreateAccTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DoctorLoginActivity.this, DoctorSignupActivity.class));

            }
        });



        DocForgotPsswrdTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorLoginActivity.this, ForgotPasswordActivity.class));


            }
        });










    }
}
