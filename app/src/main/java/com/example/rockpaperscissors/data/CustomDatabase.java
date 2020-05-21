package com.example.rockpaperscissors.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CustomEntry.class}, version = 1)
public abstract class CustomDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "custom_contacts";
    private static CustomDatabase INSTANCE;

    public static CustomDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (LOCK){
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        CustomDatabase.class,
                        CustomDatabase.DATABASE_NAME)
                        .build();
            }
        }

        return INSTANCE;
    }

    public abstract CustomDao customDao();
}
