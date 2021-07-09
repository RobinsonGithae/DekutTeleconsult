package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
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

public class DoctorDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout mydrawer;
    public ActionBarDrawerToggle mydrawerToggle;
    CardView AllconsultionsCrd,AllprescriptionsCrd, AllreportedEmrgncyCrd,DocNotificationsCrd,DocPrescribeCrd;

    NavigationView navigationView;




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
            case R.id.itemDochelp:
                //do something

                Intent intnt=new Intent(DoctorDrawerActivity.this, HelpActivity.class);
                startActivity(intnt);


                break;
            case R.id.quitapp:
           //     Intent myintent2=new Intent(DoctorDrawerActivity.this, PinLoginActivity.class);
           //     startActivity(myintent2);
//                finish();




                break;



        }


        return super.onOptionsItemSelected(item);
    }









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_drawer);

        AllconsultionsCrd= (CardView) findViewById(R.id.CrdConsultionsFromStudnt);
        AllprescriptionsCrd=(CardView) findViewById(R.id.CrdAllPrescriptions);
        AllreportedEmrgncyCrd=(CardView) findViewById(R.id.CrdReportedEmergencys);
        DocNotificationsCrd=(CardView) findViewById(R.id.CrdDocNotifications);
        DocPrescribeCrd=(CardView) findViewById(R.id.prescribeCrd);

        AllconsultionsCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(StudentDrawerActivity.this,StudentChatActivity.class));
                startActivity(new Intent(DoctorDrawerActivity.this,AllUsersActivity.class));
            }
        });


        AllprescriptionsCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(StudentDrawerActivity.this,StudentChatActivity.class));
                startActivity(new Intent(DoctorDrawerActivity.this,AddPrescriptionActivity.class));
            }
        });




        AllreportedEmrgncyCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDrawerActivity.this,DoctorgetAllEmergenciesActivity.class));
            }
        });


        DocNotificationsCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // startActivity(new Intent(DoctorDrawerActivity.this,AllStudentChatListActivity.class));

            }
        });



        DocPrescribeCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DoctorDrawerActivity.this,AddPrescriptionActivity.class));

            }
        });







        mydrawer=(DrawerLayout)findViewById(R.id.docdrawerLayout);
        mydrawerToggle=new ActionBarDrawerToggle(this,mydrawer,R.string.open,R.string.close);
        mydrawer.addDrawerListener(mydrawerToggle);

        navigationView=(NavigationView)findViewById(R.id.docnav_view);
        navigationView.setNavigationItemSelectedListener(this);



        mydrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id=item.getItemId();
        if (id==R.id.itmProfile){
            Intent intent =new Intent(DoctorDrawerActivity.this, UserprofileActivity.class);
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
            Intent signoutintent=new Intent(DoctorDrawerActivity.this, LoginAsActivity.class);
            startActivity(signoutintent);
            //kill current activity(main) after signout using finish()
            finish();
            Toast.makeText(getApplicationContext(),"You were Signed out SUCCESSFULLY!",Toast.LENGTH_LONG).show();



        }else if (id==R.id.profileImage){



        }else if (id==R.id.itmDevelopers)
        {

            Intent Devintent=new Intent(DoctorDrawerActivity.this,DeveloperActivity.class);
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

