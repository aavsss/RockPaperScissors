/**
 * RPSRepository.java
 * @author Aavash Sthapit
 * Repository of the database we want to refer to
 * More specificially, which part of database we want to refer
 * This refers to the entire document of the collection "game" in the form of query.
 * Also can be referred to DAOs and DTOs.
 */
package com.example.rockpaperscissors.Firebase;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RPSRepository {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    //Collection we are referring to.
    public RPSLiveData getFirestoreLiveData(){
        Query query = firebaseFirestore.collection("game");
        //Returns the live data coming from firestore
        return new RPSLiveData(query);
    }
}
