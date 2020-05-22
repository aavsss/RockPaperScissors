package com.example.rockpaperscissors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rockpaperscissors.data.CustomEntry;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<CustomEntry> {

    /**
     * Constructor
     * @param context of the activity for which data is populated
     * @param customEntries data to be populated is supplied through here.
     */
    public CustomAdapter(@NonNull Context context, List<CustomEntry> customEntries) {
        super(context, 0,  customEntries);
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

        CustomEntry customEntry = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.name);
        TextView jobTextView = listItemView.findViewById(R.id.job);
        TextView resultTextView = listItemView.findViewById(R.id.result);

        String contactName = customEntry.getName();
        String contactJob = customEntry.getJob();
        String contactResult = customEntry.getResult();

        nameTextView.setText(contactName);
        jobTextView.setText(contactJob);
        resultTextView.setText(contactResult);

        return listItemView;

    }
}
