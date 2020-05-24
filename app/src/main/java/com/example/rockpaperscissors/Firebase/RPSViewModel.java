/**
 * RPSViewModel.java
 * @author Aavash Sthapit
 * ViewModel is called in RPSMainActivity and RPSRepository is declared here.
 * And RPSLiveData is called in RPSRepository.
 */
package com.example.rockpaperscissors.Firebase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class RPSViewModel extends ViewModel {

    private RPSRepository repository = new RPSRepository();
    public MutableLiveData<List<RPS>> rps = new MutableLiveData<>();
    RPSLiveData liveData = null;

    public LiveData<List<RPS>> getRPSLiveData(){
        liveData = repository.getFirestoreLiveData();
        return liveData;
    }

    public LiveData<List<RPS>> getRPS(){
        return liveData.rpsList;
    }

}
