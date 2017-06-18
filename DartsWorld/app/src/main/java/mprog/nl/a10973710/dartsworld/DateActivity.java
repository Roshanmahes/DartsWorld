package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DateActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "DateActivity";
    JSONObject data;
    JSONObject sportItem;
    JSONArray tournaments;
    String tournamentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
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

        try {
            data = new JSONObject(extras.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            sportItem = data.getJSONObject("sportItem");
            processData(sportItem);
        } catch (JSONException e) {
            Toast.makeText(this, "no matches today", Toast.LENGTH_SHORT).show();
        }
    }

    private void processData(JSONObject sportItem) {
        try {
            tournaments = sportItem.getJSONArray("tournaments");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView scoreListView = (ListView) findViewById(R.id.scoreList);
        ArrayList<Match> matchArrayList = new ArrayList<>();

        for (int i = 0; i < tournaments.length(); i++) {
            try {
                JSONObject tournamentObj = tournaments.getJSONObject(i);
                tournamentName = tournamentObj.getJSONObject("tournament").getString("name");

                TextView tvTournamentName = (TextView) findViewById(R.id.tvTournamentName);
                tvTournamentName.setText(tournamentName);

                JSONArray events = tournamentObj.getJSONArray("events");
                for (int j = 0; j < events.length(); j++) {
                    JSONObject eventObj = events.getJSONObject(j);

                    String homeScore = eventObj.getJSONObject("homeScore").getString("current");
                    String awayScore = eventObj.getJSONObject("awayScore").getString("current");

                    String homeTeam = eventObj.getJSONObject("homeTeam").getString("name");
                    String awayTeam = eventObj.getJSONObject("awayTeam").getString("name");

                    Log.d(TAG, "Score: " + homeScore + "-" + awayScore + " " + homeTeam + " " + awayTeam);

                    Match match = new Match(homeScore, awayScore, homeTeam, awayTeam);

                    matchArrayList.add(match);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        MatchListAdapter adapter = new MatchListAdapter(this, R.layout.score_item, matchArrayList);
        scoreListView.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.date, menu);
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void playerClick(View view) {
        Log.d(TAG, "Kom ik wel binnen?");

        TextView textView = (TextView) view;
        String playerName = textView.getHint().toString();
        Log.d(TAG, "Dit is playerName: " + playerName);
        playerName = playerName.replace(".","");
        Log.d(TAG, "Dit is playerName: " + playerName);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final String finalPlayerName = playerName;
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("players").hasChild(finalPlayerName)) {
                    startPlayerActivity(finalPlayerName);
                } else {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "No player information available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Something went wrong:", databaseError.toException());
            }
        });
    }

    public void startPlayerActivity(String playerName) {
        Intent intent = new Intent(DateActivity.this, PlayerActivity.class);
        intent.putExtra("playerName", playerName);
        this.startActivity(intent);
    }
}
