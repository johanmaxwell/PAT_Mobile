package com.example.mmvm2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//AndroidViewModel Class is a subclass of view model and similar to them,
//they are designed to store and manage UI-related data are responsible to
//preparer and provide data for UI and automatically
//allow data to survive configuration change
public class myViewModel extends AndroidViewModel {
    //If you need to use context inside your View model, you should use AndroidViewModel(AVM),
    //because it contains the application context.
    private Repository myRepository;
    private LiveData<List<Contacts>> allContacts;

    public myViewModel(@NonNull Application application) {
        super(application);
        this.myRepository = new Repository(application);
    }

    public LiveData<List<Contacts>> getAllContacts() {
        allContacts = myRepository.getAllContacts();
        return allContacts;
    }

    public void addNewContact(Contacts contact){
        myRepository.addContact(contact);
    }

    public void deleteContact(Contacts contact){
        myRepository.deleteContact(contact);
    }


}
