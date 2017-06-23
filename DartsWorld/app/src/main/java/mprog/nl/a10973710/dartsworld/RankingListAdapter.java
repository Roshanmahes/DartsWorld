package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gebruiker on 23-6-2017.
 */

public class RankingListAdapter extends ArrayAdapter<PlayerProperty>{

    private static final String TAG = "RankingListAdapter";
    private Context mContext;
    int mResource;

    public RankingListAdapter(Context context, int resource, ArrayList<PlayerProperty> objects) {
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

        TextView diffTextView = (TextView) convertView.findViewById(R.id.position);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);

        diffTextView.setText(String.valueOf(position+1));
        nameTextView.setText(name);

        ImageView diffImg = (ImageView) convertView.findViewById(R.id.diffImg);
        diffImg.setImageResource(R.drawable.ic_rise);

        Log.d(TAG, "Wat doe ik hier?");
        return convertView;
    }
}
