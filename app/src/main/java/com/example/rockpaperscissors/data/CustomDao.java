/**
 * CustomDao.java
 * @author Aavash Sthapit
 * Creating Data Access Objects (DAO)
 * Logic of CRUD operations are listed here.
 * @Dao annotation helps us to indicate that this should be referenced for CRUD operations.
 * @annotations prior to the method indicate what the method will do.
 * Reduces boilerplate template and delegates effectively.
 * Concept of LiveData is applied here.
 * LiveData - It is an observable class sitting between our database and our UI that is able to
 *            monitor changes in the database and notify the observers (THe UI controllers or activities)
 *            when the data changes, and it runs by default in the background thread.
 *            Replacement of Loaders.
 * In other words, we make changes to the database, liveData monitors this change and updates the UI.
 * Whenever changes are made, this is triggered.
 */
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

    /**
     * Inserts object into the database.
     * @param customEntry object to be entered.
     * @return the id of the object.
     */
    @Insert
    long insertContact(CustomEntry customEntry);

    /**
     * Updates object in the database.
     * @param customEntry object to be updated.
     * @return the number of objects updated.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateContact(CustomEntry customEntry);

    /**
     * Deletes the object in the database by specifying the id.
     * Querying is required whenever there is a case involving ID.
     * @param id of the object to be deleted.
     * @return the number of objects deleted.
     */
    @Query("DELETE FROM contacts WHERE id = :id")
    int deleteContact(int id);

    /**
     * Returns all the objects in the table in the form of LiveData
     * @return List containing all the objects in the table in the form of LiveData.
     */
    @Query("SELECT * FROM contacts")
    LiveData<List<CustomEntry>> loadAllContacts();

    /**
     * Returns an object of CustomEntry in the form of LiveData by querying the ID
     * @param id of the object to be loaded
     * @return the object wrapped around LiveData
     */
    @Query("SELECT * FROM contacts WHERE id = :id")
    LiveData<CustomEntry> loadContactByUd(int id);

    /**
     * Delets all the objects in the table.
     */
    @Query("DELETE FROM contacts")
    void deleteAllContacts();
}
