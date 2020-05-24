/**
 * RPSAdapter.java
 * @author Aavash Sthapit
 * Adapter class between the list and database
 * Populates the list
 * View gets populated because the adapter is called when livedata detects changes in database.
 * ViewModel observes if any changes are made and if livedata indicates that changes were made then
 * the view is refreshed.
 */
package com.example.rockpaperscissors.Firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rockpaperscissors.R;
import com.example.rockpaperscissors.data.CustomEntry;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;

public class RPSAdapter extends ArrayAdapter<RPS> {


    /**
     * Constructor
     * @param context of the activity for which data is populated
     * @param items data to be populated is supplied through here.
     */
public RPSAdapter(@NonNull Context context, ArrayList<RPS> items) {
        super(context, 0,  items);
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    Existing view, returned earlier by newView() method
     * @param convertView app context
     * @param parent  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        RPS item = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.name);
        TextView jobTextView = listItemView.findViewById(R.id.job);
        TextView resultTextView = listItemView.findViewById(R.id.result);

        String contactName = item.getName();
        String contactJob = item.getJob();
        String contactResult = item.getResult();

        nameTextView.setText(contactName);
        jobTextView.setText(contactJob);
        resultTextView.setText(contactResult);

        return listItemView;

    }
}
