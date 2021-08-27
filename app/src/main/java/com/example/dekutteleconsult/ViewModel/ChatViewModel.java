package com.example.dekutteleconsult.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {
//    androidx.lifecycle.ViewModel is a class that is responsible for preparing and managing the data for an Activity or a Fragment. It also handles the communication of the Activity / Fragment with the rest of the application (e.g. calling the business logic classes).
//    A ViewModel is always created in association with a scope (a fragment or an activity) and will be retained as long as the scope is alive. E.g. if it is an Activity, until it is finished.
//    In other words, this means that a ViewModel will not be destroyed if its owner is destroyed for a configuration change (e.g. rotation). The new owner instance just re-connects to the existing model.
//    The purpose of the ViewModel is to acquire and keep the information that is necessary for an Activity or a Fragment. The Activity or the Fragment should be able to observe changes in the ViewModel. ViewModels usually expose this information via LiveData or Android Data Binding. You can also use any observability construct from you favorite framework.
//            ViewModel's only responsibility is to manage the data for the UI.

//initialize variable here
    MutableLiveData<String>mutableLiveData=new MutableLiveData<>();


    public ChatViewModel(){

    }


    //CREATE a method to set text
    public  void  setText(String s){
       //setter method
        //set the value to mutable data.
        mutableLiveData.setValue(s);

    }

    //create get text method


    public MutableLiveData<String> getMutableLiveData() {
        return mutableLiveData;
    }
}
