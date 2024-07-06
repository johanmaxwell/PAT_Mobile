package com.example.mmvm2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.mmvm2.databinding.ActivityAddNewContactBinding;

public class AddNewContactActivity extends AppCompatActivity {

    private ActivityAddNewContactBinding binding;
    private AddNewContactHandler handler;
    private Contacts contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        contact = new Contacts();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_contact);

        myViewModel myViewModel = new ViewModelProvider(this).get(com.example.mmvm2.myViewModel.class);


        handler = new AddNewContactHandler(contact, this, myViewModel);

        binding.setContact(contact);
        binding.setClickHandler(handler);
    }
}