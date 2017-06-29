/*
 * Created by Roshan Mahes on 23-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class RankingListAdapter extends ArrayAdapter<KeyValuePair>{

    private static final String TAG = "RankingListAdapter";
    private Context mContext;
    private int mResource;

    RankingListAdapter(Context context, int resource, ArrayList<KeyValuePair> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String difference = getItem(position).getId();
        String name = getItem(position).getValue();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView positionTextView = (TextView) convertView.findViewById(R.id.position);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);

        positionTextView.setText(String.valueOf(position+1));
        nameTextView.setText(name);

        ImageView diffImg = (ImageView) convertView.findViewById(R.id.diffImg);
        if (Integer.parseInt(difference) < 0) {
            diffImg.setImageResource(R.drawable.ic_rise);
        } else if (Integer.parseInt(difference) > 0) {
            diffImg.setImageResource(R.drawable.ic_drop);
        } else {
            diffImg.setImageResource(R.drawable.ic_stable);
        }

        return convertView;
    }
}
