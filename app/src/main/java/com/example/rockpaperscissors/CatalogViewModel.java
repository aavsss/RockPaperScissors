/**
 * CatalogViewModel.java
 * @author Aavash Sthapit
 * Concept of ViewModel: The viewModel class is designed to store and manage UI-related data in a
 *          lifecycle-conscious way. The ViewModel class allows data to survive configuration changes
 *          such as screen rotations.
 */
package com.example.rockpaperscissors;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rockpaperscissors.data.CustomDatabase;
import com.example.rockpaperscissors.data.CustomEntry;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

/**
 * AndroidViewModel is used to gain access to the application context to get the database instance
 * inside the constructor to initialize our list of entries.
 */
public class CatalogViewModel extends AndroidViewModel {

    //All the entries in the table wrapped by LiveData
    private LiveData<List<CustomEntry>> contacts;

    //Constructor with Application context to initialize database
    public CatalogViewModel(@NonNull Application application){
        super(application);

        CustomDatabase customDatabase =
                CustomDatabase.getInstance(this.getApplication());
        contacts = customDatabase.customDao().loadAllContacts();
    }

    //Returns all the entries in the table wrapped by LiveData
    public LiveData<List<CustomEntry>> getContacts(){
        return contacts;
    }
}
