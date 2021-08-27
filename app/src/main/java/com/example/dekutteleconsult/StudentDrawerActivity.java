package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.Objects;

public class StudentDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public DrawerLayout mydrawer;
    public ActionBarDrawerToggle mydrawerToggle;
    CardView consultCrd,prescriptionCrd,medcalHstryCrd,medcationPrgrsCrd, rprtEmrgncyCrd,slfAssmntCrd;

    NavigationView navigationView;
//    FirebaseAuth fAuth;
//
//    DatabaseReference dbReference;
//    FirebaseUser fUser;
//    StorageReference storageReference;
//
//
//    private Uri imageUri;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenustudent,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //check for top app bar options
        if (mydrawerToggle.onOptionsItemSelected(item)) {
            //check if toggle is clicked returntrue to close or open drawer appears
            return true;


        }

        //get option menu items here
        switch (item.getItemId()) {
            case R.id.itemhelp:

                Intent intent=new Intent(StudentDrawerActivity.this, HelpActivity.class);
                startActivity(intent);


                break;

            case R.id.studNotifications:
                //do something
//                Intent intent3=new Intent(StudentDrawerActivity.this, AllStudentChatListActivity.class);
//                startActivity(intent3);

                break;




            case R.id.quitapp:

             new AlertDialog.Builder(this)
                     .setIcon(android.R.drawable.ic_dialog_alert)
                     .setTitle("Closing Application")
                     .setMessage("Are you sure you want to close this Application? ")
                     .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             finish();
                             System.exit(0);
                         }
                     })
                     .setNegativeButton("NO",null)
                     .show();



                break;



        }


        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_drawer);

                consultCrd= (CardView) findViewById(R.id.CrdConsult);
                prescriptionCrd=(CardView) findViewById(R.id.CrdPrescrption);
                medcalHstryCrd=(CardView) findViewById(R.id.CrdMedHstry);
                medcationPrgrsCrd=(CardView) findViewById(R.id.CrdMedProgress);
                rprtEmrgncyCrd=(CardView) findViewById(R.id.CrdRprtEmergncy);
                slfAssmntCrd=(CardView) findViewById(R.id.CrdSelfAssmnt);

                consultCrd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(StudentDrawerActivity.this,StudentChatActivity.class));
//                        startActivity(new Intent(StudentDrawerActivity.this,AllUsersActivity.class));
//
                        startActivity(new Intent(StudentDrawerActivity.this,ConsultViaActivity.class));
//
                    }
                });

        prescriptionCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(StudentDrawerActivity.this,StudentVideoCallActivity.class));
                startActivity(new Intent(StudentDrawerActivity.this,StudentPrescriptionActivity.class));


            }
        });


        medcalHstryCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(StudentDrawerActivity.this,AllStudentChatListActivity.class));
                startActivity(new Intent(StudentDrawerActivity.this,AllStudentChatListActivity.class));

            }
        });


        rprtEmrgncyCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // startActivity(new Intent(StudentDrawerActivity.this,ReportEmergencyActivity.class));

                startActivity(new Intent(StudentDrawerActivity.this,VideoCallActivity.class));


            }
        });


        slfAssmntCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


           //     startActivity(new Intent(StudentDrawerActivity.this,StudentChatActivity.class));
                startActivity(new Intent(StudentDrawerActivity.this,AllDoctorsActivity.class));


            }
        });




        mydrawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        mydrawerToggle=new ActionBarDrawerToggle(this,mydrawer,R.string.open,R.string.close);
        mydrawer.addDrawerListener(mydrawerToggle);

        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        View headerview=navigationView.getHeaderView(0);
//        CircleImageView profilePicHdr=(CircleImageView)findViewById(R.id.profileImage);
//
//
//        storageReference= FirebaseStorage.getInstance().getReference("uploads");
//
//        fUser= FirebaseAuth.getInstance().getCurrentUser();
//        dbReference= FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());
//
//
//        dbReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user=snapshot.getValue(User.class);
//                assert user != null;
//
//                if (user.getImageURL().equals("default")){
//
//                    profilePicHdr.setImageResource(R.mipmap.ic_launcher_round);
//                } else {
//
//                    String Url;
//                    Url=user.getImageURL();
//                    if(Url!=null){
//                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profilePicHdr);
//                    }
//                }
//




                mydrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

























}

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }


//
//;
//);
//    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id=item.getItemId();
        if (id==R.id.itmProfile){
            Intent intent =new Intent(StudentDrawerActivity.this, UserprofileActivity.class);
            startActivity(intent);

        }else if (id==R.id.itmSignout){


            //logic to signout student Here
            //first clear sharedpreferences that has login data saved in it

            SharedPreferences sharedprefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor sharedprefsEditor=sharedprefs.edit();
            sharedprefsEditor.clear();
            sharedprefsEditor.apply();

            //ALSO LOGOUT USER FROM DATABASE{FIREBASE)


            //Firebase signout logic here
            FirebaseAuth.getInstance().signOut();



            //TAKE USER TO LOGIN ACTIVITY AUTOMATICALLY AFTER SIGNOUT is complete
            Intent signoutintent=new Intent(StudentDrawerActivity.this, LoginAsActivity.class);
            startActivity(signoutintent);
            //kill current activity(main) after signout using finish()
            finish();
            Toast.makeText(getApplicationContext(),"You were Signed out SUCCESSFULLY!",Toast.LENGTH_LONG).show();



        }else if (id==R.id.profileImage){



        }else if (id==R.id.itmSettings){

            Intent Devintent=new Intent(StudentDrawerActivity.this,PinSettingsActivity.class);
            startActivity(Devintent);

        } else if (id==R.id.itmDevelopers)
        {

            Intent Devintent=new Intent(StudentDrawerActivity.this,DeveloperActivity.class);
            startActivity(Devintent);

        }else if (id==R.id.itmShareApp)
        { //share app to others
            ApplicationInfo api = getApplicationContext().getApplicationInfo();
            String apkPath = api.sourceDir;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
            startActivity(Intent.createChooser(intent, "SHARE USING"));
        }
        else if (id==R.id.itmRate){
            //rate app functionality. Only available if app is launched.
            try{
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="+getPackageName())));
            }catch (ActivityNotFoundException e){
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));
            }
        }


        mydrawer.closeDrawer(GravityCompat.START);


        return false;


    }


}
