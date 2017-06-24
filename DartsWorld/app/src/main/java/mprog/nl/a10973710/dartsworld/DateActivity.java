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
        String date = extras.getString("date");

        try {
            data = new JSONObject(extras.getString("data"));
            tournaments = data.getJSONObject("sportItem").getJSONArray("tournaments");
            processData(tournaments, date);
        } catch (JSONException e) {
            Toast.makeText(this, "There are no matches today.", Toast.LENGTH_SHORT).show();
        }
    }

    private void processData(JSONArray tournaments, String date) {

        ListView scoreList = (ListView) findViewById(R.id.scoreList);
        ArrayList<Match> matchList = getMatches(tournaments, date);
        MatchListAdapter adapter = new MatchListAdapter(this, R.layout.score_item, matchList);
        scoreList.setAdapter(adapter);
    }

    private ArrayList<Match> getMatches(JSONArray tournaments, String date) {

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

                    String homeTeam = eventObj.getJSONObject("homeTeam").getString("name");
                    String awayTeam = eventObj.getJSONObject("awayTeam").getString("name");
                    String startTime = eventObj.getString("startTime");

                    if (!eventObj.getJSONObject("changes").has("changeDate")) {

                        Match match = new Match("-", "-", homeTeam, awayTeam, startTime);
                        matchArrayList.add(match);

                    } else if (eventObj.getJSONObject("changes").getString("changeDate").contains(date)) {

                        String homeScore = eventObj.getJSONObject("homeScore").getString("current");
                        String awayScore = eventObj.getJSONObject("awayScore").getString("current");

                        Match match = new Match(homeScore, awayScore, homeTeam, awayTeam, startTime);
                        matchArrayList.add(match);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return matchArrayList;
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
        } else if (id == R.id.nav_ranking) {
            Intent intent = new Intent(this, RankingActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void playerClick(View view) {

        TextView textView = (TextView) view;
        String playerName = textView.getHint().toString();
        playerName = playerName.replace(".","");

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

    public void tournamentInfoClick(View view) {
        TextView tournamentName = (TextView) findViewById(R.id.tvTournamentName);

        existsTournamentInfo(tournamentName.getText().toString());
    }

    public void existsTournamentInfo(final String tournamentName) {

        DatabaseReference playersRef = FirebaseDatabase.getInstance().getReference().child("tournaments");

        playersRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    if (tournamentName.contains(postSnapshot.getKey())) {
                        tournamentClick(tournamentName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.toString());
            }
        });

    }

    private void tournamentClick(String tournamentName) {
        Intent intent = new Intent(this, TournamentActivity.class);
        intent.putExtra("tournamentName", tournamentName);
        this.startActivity(intent);
    }
}