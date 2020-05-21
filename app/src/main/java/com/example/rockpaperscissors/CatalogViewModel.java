package com.example.rockpaperscissors;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rockpaperscissors.data.CustomDatabase;
import com.example.rockpaperscissors.data.CustomEntry;

import java.util.List;

public class CatalogViewModel extends AndroidViewModel {

    private LiveData<List<CustomEntry>> contacts;

    public CatalogViewModel(@NonNull Application application){
        super(application);
        CustomDatabase customDatabase =
                CustomDatabase.getInstance(this.getApplication());
        contacts = customDatabase.customDao().loadAllContacts();
    }

    public LiveData<List<CustomEntry>> getContacts(){
        return contacts;
    }
}
