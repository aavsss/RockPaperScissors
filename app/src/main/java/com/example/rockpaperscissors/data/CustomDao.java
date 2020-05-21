package com.example.rockpaperscissors.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CustomDao {

    @Insert
    long insertContact(CustomEntry customEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateContact(CustomEntry customEntry);

    @Query("DELETE FROM contacts WHERE id = :id")
    int deleteContact(int id);

    @Query("SELECT * FROM contacts")
    LiveData<List<CustomEntry>> loadAllContacts();

    @Query("SELECT * FROM contacts WHERE id = :id")
    LiveData<CustomEntry> loadContactByUd(int id);

    @Query("DELETE FROM contacts")
    void deleteAllContacts();
}
