/**
 * RPSLiveData.java
 * @author Aavash Sthapit
 * Extending LiveData<List<RPS>> class to configure the received data.
 *  Refers to the kind of item that will be observed in the app, we have a list of RPS,
 *  but we want the list to come from Firestore, so by extendind it, you're able to modify
 *  the default implementation to one of your own.
 * Implementing EventListener<QuerySnapshot> to listen to changes in the query
 *  Allows the app to observe firebase and see if a particular document changes.
 *  A snapshot is a fixed image of the data - when the snapshot changes an onEvent will be trigerred.
 */
package com.example.rockpaperscissors.Firebase;

import android.net.sip.SipSession;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rockpaperscissors.Firebase.RPS;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class RPSLiveData extends LiveData<List<RPS>> implements EventListener<QuerySnapshot> {

    private Query query;
    //Temp list to add the values
    private List<RPS> rpsTemp = new ArrayList<>();
    //After getting all the items in the temp list, we will be adding them all to the
    //MutableLiveData variable to notify the View and make the necessary updates.
    public MutableLiveData<List<RPS>> rpsList = new MutableLiveData<>();
    //To attach and detach listener
    private ListenerRegistration listenerRegistration = () -> {};

    //Constructor which takes on the query sent by RPSRepository
    public RPSLiveData(Query query){
        this.query = query;
    }

    @Override
    protected void onActive(){
        listenerRegistration = query.addSnapshotListener(this);
        super.onActive();
    }

    @Override
    protected void onInactive(){
        listenerRegistration.remove();
        super.onInactive();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e){

        if (e != null){
            Log.w("RPSLiveData", "Listen failed.", e);
            return;
        }

        //Looking for only DocumentChange so that we only get the documents that have been changed
        //i.e. added, deleted, updated.
        for(DocumentChange dc: querySnapshot.getDocumentChanges()){
            //Retrieving document snapshot similar to map
            DocumentSnapshot doc = dc.getDocument();
            if(doc.get("user_play") != null){
                RPS itemToAdd = new RPS();
                itemToAdd.setName(doc.get("user_play").toString());
                itemToAdd.setJob(doc.get("random_play").toString());
                itemToAdd.setResult(doc.get("result").toString());
                rpsTemp.add(itemToAdd);
            }
        }
        /*
        This step is key for triggering LiveData. For the app to know that LiveData changed,
        we need to use the method setValue(). We're setting the temporary list here. To avoid
        refreshing the screen a lot, we declare this method outside of the loop.
         */
        rpsList.setValue(rpsTemp);

    }
}
