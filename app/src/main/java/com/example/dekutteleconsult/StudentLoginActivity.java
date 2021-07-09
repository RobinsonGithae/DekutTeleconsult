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
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import okio.Timeout;

public class StudentLoginActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    ProgressBar LoginProgressBar;
    EditText studentEmailET, studentPassET;
    Button LoginStudBtn;
    TextView StudcreateAccTV,StudForgotPsswrdTV;


    public  Boolean StudentLoginValidator(){

        if (TextUtils.isEmpty(studentEmailET.getText().toString())){
            studentEmailET.setError("Enail can`t be empty");
            return false;
        }


        if (studentPassET.getText().toString().trim()==null){
            studentPassET.setError("password can`t be empty");
            return false;
        }


        if ((studentPassET.getText().toString().length()<8)){
            studentPassET.setError("Password must be atleast 8 characters");
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
                final String studEmail=studentEmailET.getText().toString().trim();
                final String studPass=studentPassET.getText().toString().trim();
                //FIREBASE LOGIN LOGIC HERE
                //use firebase signin method and also call the oncomplete listener to listen if login is successful and complete

                fAuth.getInstance().signInWithEmailAndPassword(studEmail,studPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if login success move user to drawer activity
                        //HIDE PROGRESSBAR AFTER SUCCESSFUL LOGIN
                        LoginProgressBar.setVisibility(View.GONE);


                        if(task.isSuccessful()){

                           //tel user
                            Toast.makeText(getApplicationContext(),"Login Successful...WELCOME BACK",Toast.LENGTH_LONG).show();

                            //if login success also save login credentials  to shared preferences class i just created
                            SharedPrefUtils.saveEmail(studEmail,getApplicationContext());
                            SharedPrefUtils.savePassword(studPass,getApplicationContext());




                            Intent intent=new Intent(StudentLoginActivity.this,StudentDrawerActivity.class);
                            //pUt email in intent to be used in the next activity to welcome user with it.
                            intent.putExtra(AppConstants.KEY_EMAIL,studEmail);
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
                Intent intent=new Intent(StudentLoginActivity.this,StudentDrawerActivity.class);
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
//back button
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);






        studentPassET=(EditText)findViewById(R.id.ETStudloginPswd) ;
        studentEmailET=(EditText)findViewById(R.id.ETStudLoginEmail) ;

        StudcreateAccTV=(TextView) findViewById(R.id.StudNotRegTv);

        StudForgotPsswrdTV=(TextView) findViewById(R.id.StudforgotPasswrdTv);

        LoginStudBtn=(Button) findViewById(R.id.btnStdLogin);
        LoginProgressBar =(ProgressBar) findViewById(R.id.StudLoginProgBar);

        LoginStudBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StudentLoginValidator()==true){

                    //checkSavedSharedPreference();
                    LoginUserWithFirebase();
                }


            }
        });



        StudcreateAccTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StudentLoginActivity.this, StudentSignupActivity.class));

            }
        });



        StudForgotPsswrdTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLoginActivity.this, ForgotPasswordActivity.class));


            }
        });





    }
}
