package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Roshan Mahes on 12-6-2017.
 * Source: https://www.youtube.com/watch?v=E6vE8fqQPTE.
 */

public class PropertyListAdapter extends ArrayAdapter<PlayerProperty> {

    private Context mContext;
    int mResource;

    public PropertyListAdapter(Context context, int resource, ArrayList<PlayerProperty> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the property info
        String id = getItem(position).getId();
        String value = getItem(position).getValue();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView propertyId = (TextView) convertView.findViewById(R.id.player_property_id);
        TextView propertyValue = (TextView) convertView.findViewById(R.id.player_property_value);

        propertyValue.setText(value);
        propertyId.setText(id);


        return convertView;
    }
}
