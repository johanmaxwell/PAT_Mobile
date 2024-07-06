package com.example.mmvm2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contacts.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    public abstract ContactDao getContactDao();

    //Singleton pattern (provide 1 instance of the db during lifecycle)
    private static ContactDatabase dbInstance;

    public static synchronized ContactDatabase getInstance(Context context){

        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class, "contact_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return dbInstance;

    }


}
