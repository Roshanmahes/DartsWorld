package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Roshan Mahes on 15-6-2017.
 */

class MatchListAdapter extends ArrayAdapter<Match> {

    private Context mContext;
    private int mResource;

    MatchListAdapter(Context context, int resource, ArrayList<Match> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String homeScore = getItem(position).getHomeScore();
        String awayScore = getItem(position).getAwayScore();
        String homeTeam = getItem(position).getHomeTeam();
        String awayTeam = getItem(position).getAwayTeam();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvHomeScore = (TextView) convertView.findViewById(R.id.tvHomeScore);
        TextView tvAwayScore = (TextView) convertView.findViewById(R.id.tvAwayScore);
        TextView tvHomeTeam = (TextView) convertView.findViewById(R.id.tvHomeTeam);
        TextView tvAwayTeam = (TextView) convertView.findViewById(R.id.tvAwayTeam);

        tvHomeScore.setHint(homeTeam);
        tvAwayScore.setHint(awayTeam);
        tvHomeTeam.setHint(homeTeam);
        tvAwayTeam.setHint(awayTeam);

        tvHomeScore.setText(homeScore);
        tvAwayScore.setText(awayScore);
        tvHomeTeam.setText(homeTeam);
        tvAwayTeam.setText(awayTeam);

        if (homeScore != "-" && awayScore != "-") {
            if (Integer.parseInt(homeScore) < Integer.parseInt(awayScore)) {
                tvHomeScore.setBackgroundResource(R.color.colorPrimary);
                tvHomeTeam.setBackgroundResource(R.color.colorPrimary);
                tvAwayScore.setBackgroundResource(R.color.colorAccent);
                tvAwayTeam.setBackgroundResource(R.color.colorAccent);
            } else if (Integer.parseInt(homeScore) > Integer.parseInt(awayScore)) {
                tvHomeScore.setBackgroundResource(R.color.colorAccent);
                tvHomeTeam.setBackgroundResource(R.color.colorAccent);
                tvAwayScore.setBackgroundResource(R.color.colorPrimary);
                tvAwayTeam.setBackgroundResource(R.color.colorPrimary);
            }
        }

        return convertView;
    }
}
