/*
 * Created by Roshan Mahes on 19-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static mprog.nl.a10973710.dartsworld.Helper.displayAlertDialog;
import static mprog.nl.a10973710.dartsworld.Helper.isConnectedToInternet;
import static mprog.nl.a10973710.dartsworld.Helper.navigateTo;

public class TournamentActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        if (isConnectedToInternet(TournamentActivity.this)) {
            setUpBars(TournamentActivity.this, "DartsWorld");
            Bundle extras = getIntent().getExtras();
            getTournamentInfo(extras.getString("tournamentName"));
        } else {
            displayAlertDialog(TournamentActivity.this);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigateTo("TournamentActivity", TournamentActivity.this, id, drawer);
        return true;
    }

    /**
     * Loads tournament info from Firebase.
     */
    public void getTournamentInfo(final String tournamentName) {

        DatabaseReference playersRef = FirebaseDatabase.getInstance().getReference().child("tournaments");
        playersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tournament tournament = dataSnapshot.child(tournamentName).getValue(Tournament.class);
                KeyValuePair tourName = new KeyValuePair("Tournament name", tournamentName);
                setTournamentInfo(tourName, tournament);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * Determines tournament info to be displayed and displays it on screen.
     */
    private void setTournamentInfo(KeyValuePair tournamentName, Tournament tournament) {

        ArrayList<KeyValuePair> propertyList = new ArrayList<>();
        ListView tournamentInfoList = (ListView) findViewById(R.id.tournamentInfoList);

        KeyValuePair sponsor = new KeyValuePair("Sponsor", tournament.getSponsor());
        KeyValuePair venue = new KeyValuePair("Venue", tournament.getVenue());
        KeyValuePair location = new KeyValuePair("Location", tournament.getLocation());
        KeyValuePair country = new KeyValuePair("Country", tournament.getCountry());
        KeyValuePair established = new KeyValuePair("Established",
                String.valueOf(tournament.getEstablished()));
        KeyValuePair defendingChamp = new KeyValuePair("Defending champion",
                tournament.getDefendingChamp());
        KeyValuePair prizeMoney = new KeyValuePair("Prize money", tournament.getPrizeMoney());
        KeyValuePair format = new KeyValuePair("Format", tournament.getFormat());

        HashMap<String, String> hash = tournament.getChamps();

        List<String> sortedKeys = new ArrayList<>(hash.keySet());
        Collections.sort(sortedKeys);

        Map<String, String> treeMap = new TreeMap<>(hash);

        propertyList.add(tournamentName); propertyList.add(sponsor); propertyList.add(venue);
        propertyList.add(location); propertyList.add(country); propertyList.add(established);
        propertyList.add(defendingChamp); propertyList.add(prizeMoney); propertyList.add(format);

        PropertyListAdapter adapter = new PropertyListAdapter(TournamentActivity.this,
                R.layout.adapter_view_player, propertyList);
        tournamentInfoList.setAdapter(adapter);

        setTournamentImage(tournament.getLogo());
        setListener(treeMap);
    }

    private void setTournamentImage(String logoURL) {
        ImageView tournamentLogo = (ImageView) findViewById(R.id.tournamentLogo);
        Picasso.with(TournamentActivity.this).load(logoURL).fit().into(tournamentLogo);
    }

    /*
     * Displays all previous champs of some tournament.
     */
    private void setListener(final Map<String, String> hash) {
        final ListView tournamentInfoList = (ListView) findViewById(R.id.tournamentInfoList);
        tournamentInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int established = 5;
                int defendingChamp = 6;

                if (position == established || position == defendingChamp) {

                    // view all champions
                    ArrayList<KeyValuePair> propertyList = new ArrayList<>();

                    for (Map.Entry<String,String> entry : hash.entrySet()) {
                        KeyValuePair property = new KeyValuePair(entry.getKey(), entry.getValue());
                        propertyList.add(property);

                        PropertyListAdapter adapter = new PropertyListAdapter(TournamentActivity.this,
                                R.layout.adapter_view_player, propertyList);
                        tournamentInfoList.setAdapter(adapter);
                    }
                }
            }
        });
    }
}
