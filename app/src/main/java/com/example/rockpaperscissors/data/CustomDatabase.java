/**
 * CustomDatabase.java
 * @author Aavash Sthapit
 * Creating an abstract class the extends RoomDatabase.
 * THis class will tie the entiries and the DAOs together.
 * We list the entities right next to the database annotation,
 * and the DAOs will be listed inside the class.
 */
package com.example.rockpaperscissors.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @Database annotation to indicate that it is a Rooms database
 * and indicating the entity we want to include
 */
@Database(entities = {CustomEntry.class}, version = 1)
public abstract class CustomDatabase extends RoomDatabase {

    /**
     * To avoid making several instances of the database, we need to ensure we are always using the
     * same instance and make sure we are not accidentally creating more than one at the time,
     * for which we use the singleton pattern.
     */
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
