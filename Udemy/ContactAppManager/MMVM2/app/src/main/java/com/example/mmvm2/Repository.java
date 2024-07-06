package com.example.mmvm2;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    //The available Data Sources
    //put all the method here
    //- ROOM DATABASE
    private final ContactDao contactDao;
    ExecutorService executor;
    Handler handler;

    public Repository(Application application) {

        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);
        this.contactDao = contactDatabase.getContactDao();

        executor = Executors.newSingleThreadExecutor();
        //Database operation executed sequencialy in the background, prevent concurrency
        //Used for updating the UI
        handler = new Handler(Looper.getMainLooper());
    }

    //Methods in DAO being executed from repository
    public void addContact(Contacts contact){
        //Runnable : Executing Tasks on Separate Thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.insert(contact); //rum ansynchronously
            }
        });
    }

    public void deleteContact(Contacts contact){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.delete(contact);
            }
        });

    }

    public LiveData<List<Contacts>> getAllContacts(){
        //cannot use run because this method return something & need to publish contacts to the UI - use LiveData
        return contactDao.getAllContacts();
    }
}

