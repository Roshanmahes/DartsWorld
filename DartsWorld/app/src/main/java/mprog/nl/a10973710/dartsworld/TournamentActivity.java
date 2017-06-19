package mprog.nl.a10973710.dartsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TournamentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "TournamentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        String tournamentName = extras.getString("tournamentName");

        getTournamentInfo(tournamentName);
    }

    private void getTournamentInfo(final String tournamentName) {

        DatabaseReference playersDatabase = FirebaseDatabase.getInstance().getReference().child("tournaments");

        playersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    if (tournamentName.contains(postSnapshot.getKey())) {

                        Tournament tournament = postSnapshot.getValue(Tournament.class);

                        ListView tournamentInfoList = (ListView) findViewById(R.id.tournamentInfoList);

                        PlayerProperty sponsor = new PlayerProperty("sponsor", tournament.getSponsor());
                        PlayerProperty venue = new PlayerProperty("venue", tournament.getVenue());
                        PlayerProperty location = new PlayerProperty("location", tournament.getLocation());
                        PlayerProperty country = new PlayerProperty("country", tournament.getCountry());
                        PlayerProperty established = new PlayerProperty("established", String.valueOf(tournament.getEstablished()));
                        PlayerProperty defendingChamp = new PlayerProperty("defending champion", tournament.getDefendingChamp());
                        PlayerProperty prizeMoney = new PlayerProperty("Prize money", tournament.getPrizeMoney());
                        PlayerProperty format = new PlayerProperty("format", tournament.getFormat());

                        ArrayList<PlayerProperty> propertyList = new ArrayList<>();

                        propertyList.add(sponsor); propertyList.add(venue); propertyList.add(location);
                        propertyList.add(country); propertyList.add(established); propertyList.add(defendingChamp);
                        propertyList.add(prizeMoney); propertyList.add(format);

                        PropertyListAdapter adapter = new PropertyListAdapter(TournamentActivity.this, R.layout.adapter_view_player, propertyList);
                        tournamentInfoList.setAdapter(adapter);

                        ImageView tournamentLogo = (ImageView) findViewById(R.id.tournamentLogo);
                        String logoURL = tournament.getLogo();
                        Picasso.with(TournamentActivity.this).load(logoURL).fit().into(tournamentLogo);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.toString());
            }
        });





//        playersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                try {
//
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        Player post = postSnapshot.getValue(Player.class);
//
//                        PlayerKeyList.add(postSnapshot.getKey());
//                        PlayerList.add(String.valueOf(post.getFullName()));
//                    }
//
//                    setPlayerInfo(PlayerList);
//
//                } catch (Exception e) {
//                    Log.e(TAG, "Exception e = " + e.getLocalizedMessage());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, databaseError.toString());
//            }
//        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tournament, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_calendar) {
            Intent intent = new Intent(this, CalendarActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_players) {
            Intent intent = new Intent(this, PlayersActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_info) {
            Intent intent = new Intent(this, InfoActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_tournaments) {
            Intent intent = new Intent(this, TournamentsActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
