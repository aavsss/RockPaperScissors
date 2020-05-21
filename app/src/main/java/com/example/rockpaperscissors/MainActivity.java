package com.example.rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rockpaperscissors.data.CustomDatabase;
import com.example.rockpaperscissors.data.CustomEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private CustomAdapter mAdapter;
    private CustomDatabase mDb;
    private ListView customListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button to play
        FloatingActionButton fabButton = findViewById(R.id.fab);
        fabButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(MainActivity.this,v);
            popupMenu.setOnMenuItemClickListener(MainActivity.this);
            popupMenu.inflate(R.menu.menu_option);
            popupMenu.show();
        });

        //Find the ListView which will be populated with <custom> data
        customListView = findViewById(R.id.list);

        //Find and set empty list view on the ListView, so that it only shows when the list has 0 items
        View emptyView = findViewById(R.id.empty_view);
        customListView.setEmptyView(emptyView);

        //Setup an Adapter to create a list item for each row of <custom> data in the Cursor.
        //There is no <custom> data yet(until the loader finishes) so pass in null for the Cursor
        mAdapter = new CustomAdapter(this, new ArrayList<CustomEntry>());
        customListView.setAdapter(mAdapter);

        mDb = CustomDatabase.getInstance(getApplicationContext());
        setUpViewModel();

    }

    private void setUpViewModel(){
        CatalogViewModel viewModel = ViewModelProviders.of(this).get(CatalogViewModel.class);
        viewModel.getContacts().observe(this, new Observer<List<CustomEntry>>() {
            @Override
            public void onChanged(List<CustomEntry> customEntries) {
                mAdapter = new CustomAdapter(getApplicationContext(), customEntries);
                customListView.setAdapter(mAdapter);
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.rock:
                String responseRock = getRandomlyGeneratedResponse();
                String resultRock = getResult("Rock", responseRock);
                CustomEntry rockEntry = new CustomEntry("Rock", responseRock, resultRock);
                new PlayCustomGame(getApplicationContext(), this).execute(rockEntry);
                return true;
            case R.id.paper:
                String responsePaper = getRandomlyGeneratedResponse();
                String resultPaper = getResult("Paper", responsePaper);
                CustomEntry paperEntry = new CustomEntry("Paper", responsePaper, resultPaper);
                new PlayCustomGame(getApplicationContext(), this).execute(paperEntry);
                return true;
            case R.id.scissors:
                String responseScissors = getRandomlyGeneratedResponse();
                String resultScissors = getResult("Scissors", responseScissors);
                CustomEntry scissorsEntry = new CustomEntry("Scissors", responseScissors, resultScissors);
                new PlayCustomGame(getApplicationContext(), this).execute(scissorsEntry);
                return true;
            case R.id.lizard:
                String responseLizard = getRandomlyGeneratedResponse();
                String resultLizard = getResult("Lizard", responseLizard);
                CustomEntry lizardEntry = new CustomEntry("Lizard", responseLizard, resultLizard);
                new PlayCustomGame(getApplicationContext(), this).execute(lizardEntry);
                return true;
            case R.id.spock:
                String responseSpock = getRandomlyGeneratedResponse();
                String resultSpock = getResult("Spock", responseSpock);
                CustomEntry spockEntry = new CustomEntry("Spock", responseSpock, resultSpock);
                new PlayCustomGame(getApplicationContext(), this).execute(spockEntry);
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
                insertDummyContact();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                deleteAllContacts();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertDummyContact(){
        new InsertCustomTask(getApplicationContext()).execute();
    }

    //Helper method to delete all data in the database
    private void deleteAllContacts() {
        new DeleteAllContacts(getApplicationContext()).execute();
    }

    private static class InsertCustomTask extends AsyncTask<Void, Void, Void>{

        final CustomEntry customEntry = new CustomEntry("Aavash", "Sthapit", "Won");
        private final WeakReference<Context> weakReference;

        InsertCustomTask(Context appContext){
            this.weakReference = new WeakReference<>(appContext);
        }

        @Override
        protected Void doInBackground(Void... voids){
            CustomDatabase customDatabase = CustomDatabase.getInstance(weakReference.get());
            customDatabase.customDao().insertContact(customEntry);
            return null;
        }
    }

    private static class DeleteAllContacts extends AsyncTask<Void, Void, Void>{
        private final WeakReference<Context> weakReference;

        DeleteAllContacts(Context appContext){
            this.weakReference = new WeakReference<>(appContext);
        }

        @Override
        protected Void doInBackground(Void... voids){
            CustomDatabase customDatabase = CustomDatabase.getInstance(weakReference.get());
            customDatabase.customDao().deleteAllContacts();
            return null;
        }
    }

    private static class PlayCustomGame extends AsyncTask<CustomEntry, Void, Long>{
        private final WeakReference<Context> weakReference;
        private Activity activity;

        PlayCustomGame(Context appContext, Activity activity){
            this.weakReference = new WeakReference<>(appContext);
            this.activity = activity;
        }

        @Override
        protected Long doInBackground(CustomEntry... customEntries){
            CustomDatabase customDatabase = CustomDatabase.getInstance(weakReference.get());
            return customDatabase.customDao().insertContact(customEntries[0]);
        }

        @Override
        protected void onPostExecute(Long result){
            if(result ==(long) 1){
//                TextView resultTextView = activity.findViewById()
            }
        }
    }

//    //Getting data from firebase
//    private Task<String> getRandomStringFromCloud(){
//        Map<String, Object> data = new HashMap<>();
//
//        return firebaseFunctions
//                .getHttpsCallable("randomResponse")
//                .call(data)
//                .continueWith(task -> {
//                    Map<String, Object> result = (Map<String, Object>)task.getResult().getData();
//                    return (String) result.get("result");
//                });
//    }

    private String getRandomlyGeneratedResponse(){
        String[] randomReponse = {"Rock", "Paper","Scissors","Lizard","Spock"};
        Random random = new Random();
        int index = random.nextInt(4);
        return randomReponse[index];
    }

}
