package com.example.dekutteleconsult;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class DekutTeleconsult extends Application {
    // A class to handle firebase offline capabilities and disk persistence for this app


    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
