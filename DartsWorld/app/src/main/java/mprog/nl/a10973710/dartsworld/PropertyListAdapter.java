/*
 * Created by Roshan Mahes on 12-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * Displays an item consisting of two elements nicely on the screen.
 */

class PropertyListAdapter extends ArrayAdapter<KeyValuePair> {

    private Context mContext;
    private int mResource;

    PropertyListAdapter(Context context, int resource, ArrayList<KeyValuePair> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get the property info
        String id = getItem(position).getId();
        String value = getItem(position).getValue();

        convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);

        TextView propertyId = (TextView) convertView.findViewById(R.id.player_property_id);
        TextView propertyValue = (TextView) convertView.findViewById(R.id.player_property_value);

        propertyValue.setText(value);
        propertyId.setText(id);

        return convertView;
    }
}
