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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class StudentSignupActivity extends AppCompatActivity {

    Button signupBtn;
    EditText usernameET,emailET,passwrdET,confrmPasswrdET,fullnameET,regnoET,yearofstudyET,phonenoET;


  FirebaseAuth fAuth;
  DatabaseReference reference;
  FirebaseFirestore fStore;
  String studusermame,studpasswrd,studcnfrmpasswrd,studemail,studfullname,studregno,studyearofstudy,studphoneno;


  DatabaseReference studentsRef;


  public void StudentSignUpInputs(){
      //method to get all inputs
      studusermame= usernameET.getText().toString().trim();
      studemail=emailET.getText().toString().trim();
      studpasswrd= passwrdET.getText().toString().trim();
      studcnfrmpasswrd= confrmPasswrdET.getText().toString().trim();

      studfullname= fullnameET.getText().toString().trim();
      studregno=regnoET.getText().toString().trim();
      studyearofstudy=yearofstudyET.getText().toString().trim();
      studphoneno=phonenoET.getText().toString().trim();

  }


    public void FirebaseRegisterUser(){
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        StudentSignUpInputs();
        fAuth.createUserWithEmailAndPassword(studemail, studpasswrd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //check and toast if user created successfully
                Toast.makeText(getApplicationContext(),"Account created successfully",Toast.LENGTH_LONG).show();
               //GET user info of currently registred
                FirebaseUser fuser=fAuth.getCurrentUser();
                //create a users collection document reference in firestore to store all users details if it is not present.
                // If collection reference exists Also assign the new User`sID generated by firebase during authentication(registration) to the reference.
                DocumentReference df=fStore.collection("Users").document(fuser.getUid());

               // Also store user info in database
                reference= FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                studentsRef= FirebaseDatabase.getInstance().getReference("Students").child(fuser.getUid());


                //now store user data as key value pairs in a hashmap.
                Map<String,Object> UserInfoMap=new HashMap<>();
                Map<String,Object> StudentInfoMap=new HashMap<>();

                String userid=fuser.getUid();
                UserInfoMap.put("id",userid);

                UserInfoMap.put("Username",studusermame);
                //Specify if user is student or doctor here. If student give 0 if doctor give 1
                UserInfoMap.put("isDoctor","0");
                UserInfoMap.put("imageURL","default");
                //
                UserInfoMap.put("email",studemail);



                StudentInfoMap.put("fullName",studfullname);
                StudentInfoMap.put("regNo",studregno);
                StudentInfoMap.put("yearOfStudy",studyearofstudy);
                StudentInfoMap.put("gender","default");
                StudentInfoMap.put("DOBirth","default");
                StudentInfoMap.put("phoneNo",studphoneno);

                //save/write new user info Map data to firestore Users collection.
                df.set(UserInfoMap);

//also set user values to database
                reference.setValue(UserInfoMap);
                //set student values to database. A student is a user.
                studentsRef.setValue(StudentInfoMap);


                Intent intent =new Intent(StudentSignupActivity.this,StudentDrawerActivity.class);
                startActivity(intent);
                //Use finish 2 Prevent user from going back to signup after creating account
                finish();




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String SignupErr=e.getMessage();

                Toast.makeText(getApplicationContext(),"Account creation Failed because "+SignupErr+" Please try again Later",Toast.LENGTH_LONG).show();

            }
        });






    }



    public Boolean CustomInputValidator(){
      //a custom input validation method
        if (TextUtils.isEmpty(usernameET.getText().toString())){
            usernameET.setError("Username can`t be empty");
         return false;
        }
        if (TextUtils.isEmpty(emailET.getText().toString())){
            emailET.setError("Email can`t be empty");
            return false;
        }


        if (passwrdET.getText().toString().trim()==null){
            passwrdET.setError("password can`t be empty");
        return false;
        }

        if (confrmPasswrdET.getText().toString().trim()==null){
            confrmPasswrdET.setError("Confirm password can`t be empty");
        return false;
        }


        if (passwrdET.getText().toString().trim().length() < 8) {

            passwrdET.setError("Password should be atleast 8 characters");
return false;
        }
        if (confrmPasswrdET.getText().toString().trim().length() < 8) {

            confrmPasswrdET.setError("Password should be atleast 8 characters");
return false;
        }


        if (!((passwrdET.getText().toString().trim()).equals(confrmPasswrdET.getText().toString().trim())) ){
            confrmPasswrdET.setError("Initial password & confirm must be same");
return false;

        }

        if (TextUtils.isEmpty(fullnameET.getText().toString())){
            fullnameET.setError("Fullname can`t be empty");
            return false;
        }


        if (TextUtils.isEmpty(regnoET.getText().toString())){
            regnoET.setError("RegNo can`t be empty");
            return false;
        }

        if (TextUtils.isEmpty(yearofstudyET.getText().toString())){
            yearofstudyET.setError("YearOfStudy can`t be empty");
            return false;
        }

        if (TextUtils.isEmpty(phonenoET.getText().toString())){
            phonenoET.setError("phoneNo can`t be empty");
            return false;
        }

        else{
            return true;
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
        setContentView(R.layout.activity_student_signup);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        signupBtn=(Button)findViewById(R.id.btnStudSignup);
        usernameET=(EditText) findViewById(R.id.ETsignupUsrnm);
        passwrdET=(EditText)findViewById(R.id.ETStudsgnupPswd);
        confrmPasswrdET=(EditText)findViewById(R.id.ETStudSignupconfirmPswrd);
        emailET=(EditText)findViewById(R.id.ETStudsignupEmail);


        fullnameET=(EditText) findViewById(R.id.ETsignupFullnm);
        regnoET=(EditText) findViewById(R.id.ETsignupRegno);
        yearofstudyET=(EditText) findViewById(R.id.ETsignupYearOfStudy);
        phonenoET=(EditText) findViewById(R.id.ETsignupPhoneNo);





        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // CustomInputValidator();
                if (CustomInputValidator()==true){
                    // Call CustomInputValidator to check if inputs are valid;
                    //if inputs valid then REGISTER USER using FirebaseRegisterUser method
                    FirebaseRegisterUser();

                }


            }
        });






    }
}
