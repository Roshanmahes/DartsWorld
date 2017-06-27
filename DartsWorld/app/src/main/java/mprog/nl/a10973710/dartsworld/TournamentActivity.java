package mprog.nl.a10973710.dartsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import java.util.HashMap;
import java.util.Map;

import static mprog.nl.a10973710.dartsworld.Helper.navigateTo;

public class TournamentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "TournamentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.tournament);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        getTournamentInfo(extras.getString("tournamentName"));
    }

    public void getTournamentInfo(final String tournamentName) {

        DatabaseReference playersRef = FirebaseDatabase.getInstance().getReference().child("tournaments");

        playersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    if (tournamentName.contains(postSnapshot.getKey())) {

                        Tournament tournament = postSnapshot.getValue(Tournament.class);
                        PlayerProperty tournamentName = new PlayerProperty("Tournament name",
                                postSnapshot.getKey());

                        setTournamentInfo(tournamentName, tournament);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.toString());
            }
        });
    }

    private void setTournamentInfo(PlayerProperty tournamentName, Tournament tournament) {

        ListView tournamentInfoList = (ListView) findViewById(R.id.tournamentInfoList);

        PlayerProperty sponsor = new PlayerProperty("Sponsor", tournament.getSponsor());
        PlayerProperty venue = new PlayerProperty("Venue", tournament.getVenue());
        PlayerProperty location = new PlayerProperty("Location", tournament.getLocation());
        PlayerProperty country = new PlayerProperty("Country", tournament.getCountry());
        PlayerProperty established = new PlayerProperty("Established", String.valueOf(tournament.getEstablished()));
        PlayerProperty defendingChamp = new PlayerProperty("Defending champion", tournament.getDefendingChamp());
        PlayerProperty prizeMoney = new PlayerProperty("Prize money", tournament.getPrizeMoney());
        PlayerProperty format = new PlayerProperty("Format", tournament.getFormat());

        HashMap<String, String> hash = tournament.getChamps();

        ArrayList<PlayerProperty> propertyList = new ArrayList<>();

        propertyList.add(tournamentName); propertyList.add(sponsor); propertyList.add(venue);
        propertyList.add(location); propertyList.add(country); propertyList.add(established);
        propertyList.add(defendingChamp); propertyList.add(prizeMoney); propertyList.add(format);

        PropertyListAdapter adapter = new PropertyListAdapter(TournamentActivity.this, R.layout.adapter_view_player, propertyList);
        tournamentInfoList.setAdapter(adapter);

        ImageView tournamentLogo = (ImageView) findViewById(R.id.tournamentLogo);
        String logoURL = tournament.getLogo();
        Picasso.with(TournamentActivity.this).load(logoURL).fit().into(tournamentLogo);

        setListener(hash);
    }

    private void setListener(final HashMap<String, String> hash) {
        final ListView tournamentInfoList = (ListView) findViewById(R.id.tournamentInfoList);
        tournamentInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // to view all champions
                if (position == 5 || position == 6) {
                    ArrayList<PlayerProperty> propertyList = new ArrayList<>();

                    for (Map.Entry<String,String> entry : hash.entrySet()) {
                        PlayerProperty property = new PlayerProperty(entry.getKey(), entry.getValue());
                        propertyList.add(property);

                        PropertyListAdapter adapter = new PropertyListAdapter(TournamentActivity.this,
                                R.layout.adapter_view_player, propertyList);
                        tournamentInfoList.setAdapter(adapter);

//                        playerListener();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigateTo(TournamentActivity.this, id, drawer);
        return true;
    }
}
