
package com.example.rockpaperscissors.Firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.rockpaperscissors.CatalogViewModel;
import com.example.rockpaperscissors.CustomAdapter;
import com.example.rockpaperscissors.R;
import com.example.rockpaperscissors.data.CustomDatabase;
import com.example.rockpaperscissors.data.CustomEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.j2objc.annotations.Weak;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class RPSMainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ListView customListView;
    private RPSViewModel model;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button to play
        FloatingActionButton fabButton = findViewById(R.id.fab);
        fabButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(RPSMainActivity.this,v);
            popupMenu.setOnMenuItemClickListener(RPSMainActivity.this);
            popupMenu.inflate(R.menu.menu_option);
            popupMenu.show();
        });

        //Find the ListView which will be populated with <custom> data
        customListView = findViewById(R.id.list);

        //Find and set empty list view on the ListView, so that it only shows when the list has 0 items
        View emptyView = findViewById(R.id.empty_view);
        customListView.setEmptyView(emptyView);

        firebaseFirestore = FirebaseFirestore.getInstance();
        model = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(RPSViewModel.class);

        RPSAdapter rpsAdapter = new RPSAdapter(this, new ArrayList<RPS>());

        model.getRPSLiveData().observe(this, Observable -> {});

        model.getRPS().observe(this, rps -> {
            if(rps != null){
                rpsAdapter.clear();
                rpsAdapter.addAll(rps);
                customListView.setAdapter(rpsAdapter);
            }else{
                Log.d("TAG", "Awaiting for info");
            }
        });


    }

    /**
     * Used to be onCreateLoader() and its following method. Replaced by this.
     * Method reponsible for using the ViewModel and LiveData to keep the view updated
     * and for handling the configuration changes.
     */
    private void setUpViewModel(){

//        RPSViewModel viewModel = ViewModelProviders.of(this).get(RPSViewModel.class);
//        viewModel.getRPSLiveData().observe(this, Observable -> {});
//        viewModel.getRPS().observe(this, new Observer<List<RPS>>() {
//            @Override
//            public void onChanged(List<RPS> customEntries) {
//                rpsAdapter = new RPSAdapter(getApplicationContext(), (ArrayList<RPS>) customEntries);
//                customListView.setAdapter(rpsAdapter);
//            }
//        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.rock:
                String responseRock = getRandomlyGeneratedResponse();
                String resultRock = getResult("Rock", responseRock);
                RPS rockPlay = new RPS("Rock", responseRock, resultRock);
                new PlayCustomFirebaseGame(getApplicationContext()).execute(rockPlay);
                return true;
            case R.id.paper:
                String responsePaper = getRandomlyGeneratedResponse();
                String resultPaper = getResult("Paper", responsePaper);
                RPS paperPlay = new RPS("Paper", responsePaper, resultPaper);
                new PlayCustomFirebaseGame(getApplicationContext()).execute(paperPlay);
                return true;
            case R.id.scissors:
                String responseScissors = getRandomlyGeneratedResponse();
                String resultScissors = getResult("Scissors", responseScissors);
                RPS scissorsPlay = new RPS("Scissors", responseScissors, resultScissors);
                new PlayCustomFirebaseGame(getApplicationContext()).execute(scissorsPlay);
                return true;
            case R.id.lizard:
                String responseLizard = getRandomlyGeneratedResponse();
                String resultLizard = getResult("Lizard", responseLizard);
                RPS lizardPlay = new RPS("Lizard", responseLizard, resultLizard);
                new PlayCustomFirebaseGame(getApplicationContext()).execute(lizardPlay);
                return true;
            case R.id.spock:
                String responseSpock = getRandomlyGeneratedResponse();
                String resultSpock = getResult("Spock", responseSpock);
                RPS spockPlay = new RPS("Spock", responseSpock, resultSpock);
                new PlayCustomFirebaseGame(getApplicationContext()).execute(spockPlay);
                return true;
            default:
                return false;
        }
    }

    private String getResult(String userInput, String random){
        String result = "";
        switch (userInput){
            case "Rock":
                switch (random){
                    case "Rock":
                        result = "Draw";
                        break;
                    case "Paper":
                    case "Spock":
                        result = "Loss";
                        break;
                    case "Scissors":
                    case "Lizard":
                        result = "Won";
                        break;
                }
                break;
            case "Paper":
                switch (random){
                    case "Rock":
                    case "Spock":
                        result = "Won";
                        break;
                    case "Paper":
                        result = "Draw";
                        break;
                    case "Scissors":
                    case "Lizard":
                        result = "Loss";
                        break;
                }
                break;
            case "Scissors":
                switch (random){
                    case "Rock":
                    case "Spock":
                        result = "Loss";
                        break;
                    case "Paper":
                    case "Lizard":
                        result = "Won";
                        break;
                    case "Scissors":
                        result = "Draw";
                        break;
                }
                break;
            case "Lizard":
                switch (random){
                    case "Rock":
                    case "Scissors":
                        result = "Loss";
                        break;
                    case "Paper":
                    case "Spock":
                        result = "Won";
                        break;
                    case "Lizard":
                        result = "Draw";
                        break;
                }
                break;
            case "Spock":
                switch (random){
                    case "Rock":
                    case "Scissors":
                        result = "Won";
                        break;
                    case "Paper":
                    case "Lizard":
                        result = "Loss";
                        break;
                    case "Spock":
                        result = "Draw";
                        break;
                }
                break;
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
//                insertDummyContact();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
//                deleteAllContacts();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class PlayCustomFirebaseGame extends AsyncTask<RPS, Void, Void> {

        private final WeakReference<Context> weakReference;

        PlayCustomFirebaseGame(Context appContext){
            this.weakReference = new WeakReference<>(appContext);
        }

        @Override
        protected Void doInBackground(RPS... rps){
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("game")
                    .add(RPS.getMappedData(rps[0].getName(), rps[0].getJob(), rps[0].getResult()))
                    .addOnSuccessListener(documentReference -> Log.d("RPSMAIN", "Document written with ID: " +  documentReference))
                    .addOnFailureListener(e -> Log.w("RPSMAIN", "Error adding document", e));
            return null;
        }

    }

    //Pre-condition: ID Field is created and is unique for each document.
    private static class DeleteSelectedGame extends AsyncTask<String, Void, Void>{
        private final WeakReference<Context> weakReference;

        DeleteSelectedGame(Context appContext){
            this.weakReference = new WeakReference<>(appContext);
        }

        @Override
        protected Void doInBackground(String... ids){
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("game")
                    .document(ids[0])
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Log.d("RPSMAIN", "DocumentSnapshot successfully deleted!");
                    })
                    .addOnFailureListener(e -> {
                        Log.w("RPSMAIN", "Error deleting document", e);
                    });
            return null;
        }
    }


    private String getRandomlyGeneratedResponse(){
        String[] randomReponse = {"Rock", "Paper","Scissors","Lizard","Spock"};
        Random random = new Random();
        int index = random.nextInt(4);
        return randomReponse[index];
    }

}
