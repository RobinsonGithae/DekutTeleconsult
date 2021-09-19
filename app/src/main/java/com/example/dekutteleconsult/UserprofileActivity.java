package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dekutteleconsult.DataModel.Student;
import com.example.dekutteleconsult.DataModel.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserprofileActivity extends AppCompatActivity {


CircleImageView imageProfile;
TextView username;
EditText usernameET, emailET,DOBirthET,FullNameET,YearOfStudyET,RegNoET;

Button updateProfileBtn;

DatabaseReference dbReference,studentRef;
FirebaseAuth fAuth;

FirebaseUser fUser;
StorageReference storageReference;

private static  final int IMAGE_REQUEST=1;
private Uri imageUri;
private StorageTask uploadTask;

String UsernameUpdate,EmailUpdate,DOBUpdate,FullnameUpdate,YearOfStudyUpdate,RegNoUpdate;



    public void getAndStoreProfileUpdateInputs(){
        if (updateDetailsValid()) {
            //method to get all inputs
            UsernameUpdate = usernameET.getText().toString().trim();
            EmailUpdate = emailET.getText().toString().trim();
            DOBUpdate = DOBirthET.getText().toString().trim();
            FullnameUpdate = FullNameET.getText().toString().trim();
            YearOfStudyUpdate = YearOfStudyET.getText().toString().trim();
            RegNoUpdate = RegNoET.getText().toString().trim();

            fAuth=FirebaseAuth.getInstance();
            //GET user info of currently logged user
             fUser=fAuth.getCurrentUser();

            studentRef= FirebaseDatabase.getInstance().getReference("Students").child(fUser.getUid());


            //nb hashmap keys must be the same as those used during signup
            //now store user data as key value pairs in a hashmap.
            Map<String,Object> UserInfoUdateMap=new HashMap<>();
            Map<String,Object> StudentInfoUpdateMap=new HashMap<>();

            StudentInfoUpdateMap.put("fullName",FullnameUpdate);
            StudentInfoUpdateMap.put("regNo",RegNoUpdate);
            StudentInfoUpdateMap.put("yearOfStudy",YearOfStudyUpdate);
            StudentInfoUpdateMap.put("gender","default");
            StudentInfoUpdateMap.put("DOBirth",DOBUpdate);

            StudentInfoUpdateMap.put("phoneNo","default");

            studentRef.setValue( StudentInfoUpdateMap);

        }else { Toast.makeText(getApplicationContext(),"Check  empty update fields ",Toast.LENGTH_LONG).show();}

    }

    private boolean updateDetailsValid() {

        //a custom update inputs validation method
        if (TextUtils.isEmpty(usernameET.getText().toString())){
            usernameET.setError("Username can`t be empty");
            return false;
        }
        if (TextUtils.isEmpty(emailET.getText().toString())){
            emailET.setError("Email can`t be empty");
            return false;
        }

        if (TextUtils.isEmpty(DOBirthET.getText().toString())){
            DOBirthET.setError("Date of Birth can`t be empty");
            return false;
        }

        if (TextUtils.isEmpty(FullNameET.getText().toString())){
            FullNameET.setError("FullName can`t be empty");
            return false;
        }

        if (TextUtils.isEmpty(YearOfStudyET.getText().toString())){
            YearOfStudyET.setError("Year of Study can`t be empty");
            return false;
        }

        if (TextUtils.isEmpty(RegNoET.getText().toString())){
            RegNoET.setError("RegNo can`t be empty");
            return false;
        } else {
            return true;
        }






    }


    public void updateProfile(){
     getAndStoreProfileUpdateInputs();
    //update userdetals in firebase
    Toast.makeText(getApplicationContext(),"PROFILE UPDATED SUCCESSFULLY",Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_userprofile);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        imageProfile=(CircleImageView)findViewById(R.id.profileImage);
   username=(TextView)findViewById(R.id.usernameTV);
        usernameET=(EditText) findViewById(R.id.profileUsernameET);
        emailET=(EditText)findViewById(R.id.profileEmailET);
        DOBirthET=(EditText)findViewById(R.id.profileDateOfBirthET);
        FullNameET=(EditText)findViewById(R.id.fullnmET);
        YearOfStudyET=(EditText)findViewById(R.id.YearOfStudyET);
        RegNoET=(EditText)findViewById(R.id.RegNoET);
        updateProfileBtn=(Button)findViewById(R.id.btnUpdateProfile);



   storageReference= FirebaseStorage.getInstance().getReference("uploads");

   fUser= FirebaseAuth.getInstance().getCurrentUser();
   dbReference= FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());
        studentRef=FirebaseDatabase.getInstance().getReference("Students").child(fUser.getUid());;

   dbReference.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
           User user=snapshot.getValue(User.class);
          // Student student=snapshot.getValue(Student.class);
           assert user != null;
           username.setText(user.getUsername());
           usernameET.setText(user.getUsername());
           emailET.setText(user.getEmail());


//           assert student != null;
//           DOBirthET.setText(student.getDOBirth());
//           FullNameET.setText(student.getFullName());
//           YearOfStudyET.setText(student.getYearOfStudy());
//           RegNoET.setText(student.getRegNo());

           if (user.getImageURL().equals("default")){
               
               imageProfile.setImageResource(R.mipmap.ic_launcher_round);
           } else {
               Glide.with(getApplicationContext()).load(user.getImageURL()).into(imageProfile);
           }
           
           
           
           
           
           
       }

       @Override
       public void onCancelled(@NonNull DatabaseError error) {

       }
   });


   
imageProfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      openImage(); 
    }
});








        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Student student=snapshot.getValue(Student.class);
                assert student != null;
                DOBirthET.setText(student.getDOBirth());
                FullNameET.setText(student.getFullName());
                YearOfStudyET.setText(student.getYearOfStudy());
                RegNoET.setText(student.getRegNo());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });


    }

    private void openImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void uploadImage(){
        final ProgressDialog pd=new ProgressDialog(UserprofileActivity.this);
        pd.setMessage("Uploading image");
        pd.show();

        if (imageUri!=null){
            final StorageReference fileReference=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

            uploadTask=fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();


                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()){
                        Uri downloadUri=task.getResult();
                        String mUri=downloadUri.toString();


                        dbReference=FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());

                        HashMap<String,Object>map=new HashMap<>();
                        map.put("imageURL",mUri);
                        dbReference.updateChildren(map);


                        pd.dismiss();

                    } else {
                        Toast.makeText(getApplicationContext(),"Upload Failed due to ",Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            });



        }else {
            Toast.makeText(getApplicationContext(),"No image Selected yet",Toast.LENGTH_LONG).show();

        }













    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null){
            imageUri=data.getData();

            if (uploadTask!=null && uploadTask.isInProgress()){
                Toast.makeText(getApplicationContext(),"Image upload in Progress", Toast.LENGTH_LONG).show();
            }else {
                uploadImage();
            }



        }

    }





}
