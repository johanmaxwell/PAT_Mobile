package com.example.mmvm2;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class AddNewContactHandler {

    Contacts contact;
    Context context;
    myViewModel myViewModel;

    public AddNewContactHandler(Contacts contact, Context context, com.example.mmvm2.myViewModel myViewModel) {
        this.contact = contact;
        this.context = context;
        this.myViewModel = myViewModel;
    }

    public void onSubmitBtnClicked(View view){
        if (contact.getName() == null || contact.getEmail() == null){
            Toast.makeText(context, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i = new Intent(context, MainActivity.class);
//            i.putExtra("Name", contact.getName());
//            i.putExtra("Email", contact.getEmail());

         Contacts c = new Contacts(
                 contact.getName(),
                 contact.getEmail()
         );

         myViewModel.addNewContact(c);


            context.startActivity(i);
        }
    }

}
