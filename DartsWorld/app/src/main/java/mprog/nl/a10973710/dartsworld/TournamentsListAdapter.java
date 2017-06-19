package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roshan Mahes on 19-6-2017.
 */

public class TournamentsListAdapter extends ArrayAdapter<PlayerProperty> {

    private static final String TAG = "TournamentsListAdapter";
    private Context mContext;
    int mResource;

    public TournamentsListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PlayerProperty> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sponsor = getItem(position).getId();
        String tournamentName = getItem(position).getValue();

        PlayerProperty property = new PlayerProperty(sponsor, tournamentName);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tournament = (TextView) convertView.findViewById(R.id.tournamentName);
        ImageView sponsorLogo = (ImageView) convertView.findViewById(R.id.sponsorLogo);

        String sponsorLink = "https://firebasestorage.googleapis.com/v0/b/dartsworld-e9f85.appspot.com/o/sponsors%2F" + sponsor + ".png?alt=media";
        Picasso.with(mContext).load(sponsorLink).fit().into(sponsorLogo);

        tournament.setText(tournamentName);

        return convertView;
    }
}
