package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.util.logging.Handler;

import static android.content.ContentValues.TAG;
import static mprog.nl.a10973710.dartsworld.Helper.existsTournamentInfo;
import static mprog.nl.a10973710.dartsworld.Helper.navigateTo;
import static mprog.nl.a10973710.dartsworld.R.id.liveTournamentName;
import static mprog.nl.a10973710.dartsworld.R.id.start;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    int refreshTime = 1000;

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        if (isConnectedToInternet()) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            final LiveScoreAsyncTask asyncTask = new LiveScoreAsyncTask(this);
            asyncTask.execute("");

        Thread t = new Thread() {

            @Override
            public void run() {

                try {
                    while (!isInterrupted()) {
                        Thread.sleep(refreshTime);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LiveScoreAsyncTask asyncTask = new LiveScoreAsyncTask(MainActivity.this);
                                asyncTask.execute("");

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

//        finish();
//        startActivity(getIntent());

//        } else {
//            /// Als internet niet aan staat
//            Log.d(TAG, "Je bent niet verbonden!! :S");
//            finish();
//        }
    }


    public void fetchLiveScore(JSONObject liveScoreObj) {
        Log.d(TAG, "Livescore wordt nu opgehaald");
        Log.d(TAG, liveScoreObj.toString());

        TextView liveTournamentName = (TextView) findViewById(R.id.liveTournamentName);
        ListView liveScoreList = (ListView) findViewById(R.id.liveScoreList);

        try {
            JSONObject sportItem = liveScoreObj.getJSONObject("sportItem");
            JSONArray tournaments = sportItem.getJSONArray("tournaments");
            ArrayList<Match> matchArrayList = new ArrayList<>();

            for (int i = 0; i < tournaments.length(); i++) {
                try {
                    JSONObject tournamentObj = tournaments.getJSONObject(i);
                    String tournamentName = tournamentObj.getJSONObject("tournament").getString("name");

                    liveTournamentName.setText("Live: " + tournamentName);

                    JSONArray events = tournamentObj.getJSONArray("events");
                    for (int j = 0; j < events.length(); j++) {
                        JSONObject eventObj = events.getJSONObject(j);

                        String homeScore = eventObj.getJSONObject("homeScore").getString("current");
                        String awayScore = eventObj.getJSONObject("awayScore").getString("current");

                        String homeTeam = eventObj.getJSONObject("homeTeam").getString("name");
                        String awayTeam = eventObj.getJSONObject("awayTeam").getString("name");

                        Log.d(TAG, "Score: " + homeScore + "-" + awayScore + " " + homeTeam + " " + awayTeam);

                        String startTime = eventObj.getString("startTime");

                        Match match = new Match(homeScore, awayScore, homeTeam, awayTeam, startTime);

                        matchArrayList.add(match);
                        }
                    } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

            MatchListAdapter adapter = new MatchListAdapter(this, R.layout.score_item, matchArrayList);
            liveScoreList.setAdapter(adapter);

        } catch (JSONException e) {
            liveTournamentName.setText("No live matches");
        }
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
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("playerName", playerName);
        this.startActivity(intent);
    }

    public void tournamentInfoClick(View view) {
        TextView tournamentName = (TextView) view;
        existsTournamentInfo(tournamentName.getText().toString(), MainActivity.this);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigateTo(MainActivity.this, id, drawer);
        return true;
    }

    public void switchDataSaver(MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            refreshTime = 10000;
            item.setChecked(true);
        }
    }
}
