package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dekutteleconsult.Model.User;
import com.example.dekutteleconsult.R;
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
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserprofileActivity extends AppCompatActivity {


CircleImageView imageProfile;
TextView username;

DatabaseReference dbReference;
FirebaseUser fUser;
StorageReference storageReference;

private static  final int IMAGE_REQUEST=1;
private Uri imageUri;
private StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

   imageProfile=(CircleImageView)findViewById(R.id.profileImage);
   username=(TextView)findViewById(R.id.usernameTV);

   storageReference= FirebaseStorage.getInstance().getReference("uploads");

   fUser= FirebaseAuth.getInstance().getCurrentUser();
   dbReference= FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());


   dbReference.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
           User user=snapshot.getValue(User.class);
           assert user != null;
           username.setText(user.getUsername());
           
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
