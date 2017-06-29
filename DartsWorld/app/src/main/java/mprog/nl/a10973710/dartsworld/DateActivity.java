/*
 * Created by Roshan Mahes on 8-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static mprog.nl.a10973710.dartsworld.Helper.displayAlertDialog;
import static mprog.nl.a10973710.dartsworld.Helper.existsTournamentInfo;
import static mprog.nl.a10973710.dartsworld.Helper.isConnectedToInternet;
import static mprog.nl.a10973710.dartsworld.Helper.loadPlayerInfo;
import static mprog.nl.a10973710.dartsworld.Helper.navigateTo;

/**
 * Shows all matches of a given date.
 */

public class DateActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    JSONObject data;
    JSONArray tournaments;
    String tournamentName;
    TextView tvTournamentName = (TextView) findViewById(R.id.tvTournamentName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        if (isConnectedToInternet(DateActivity.this)) {

            Bundle extras = getIntent().getExtras();
            String date = extras.getString("date");
            String formatedDate = extras.getString("formatedDate");

            setUpBars(DateActivity.this, formatedDate);

            try {
                data = new JSONObject(extras.getString("data"));
                tournaments = data.getJSONObject("sportItem").getJSONArray("tournaments");
                processData(tournaments, date, formatedDate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            displayAlertDialog(DateActivity.this);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigateTo("DateActivity", DateActivity.this, id, drawer);
        return true;
    }

    /**
     * Processes match data and puts it on screen.
     */
    private void processData(JSONArray tournaments, String date, String formatedDate) {

        ListView scoreList = (ListView) findViewById(R.id.scoreList);
        ArrayList<Match> matchList = getMatches(tournaments, date, formatedDate);
        MatchListAdapter adapter = new MatchListAdapter(this, R.layout.score_item, matchList);
        scoreList.setAdapter(adapter);
    }

    /**
     * Shows all matches of a given date.
     */
    private ArrayList<Match> getMatches(JSONArray tournaments, String date, String formatedDate) {

        ArrayList<Match> matchArrayList = new ArrayList<>();

        for (int i = 0; i < tournaments.length(); i++) {
            try {
                JSONObject tournamentObj = tournaments.getJSONObject(i);
                tournamentName = tournamentObj.getJSONObject("tournament").getString("name");

                JSONArray events = tournamentObj.getJSONArray("events");
                for (int j = 0; j < events.length(); j++) {

                    JSONObject eventObj = events.getJSONObject(j);

                    if (eventObj.has("formatedDate")) {
                        if (eventObj.get("formatedDate").toString().contains(formatedDate)) {
                            matchArrayList = setMatch(eventObj, matchArrayList, date);
                            tvTournamentName.setText(tournamentName);
                        }
                    } else if (eventObj.has("formatedStartDate")) {
                        if (eventObj.get("formatedStartDate").toString().contains(formatedDate)) {
                            matchArrayList = setMatch(eventObj, matchArrayList, date);
                            tvTournamentName.setText(tournamentName);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return matchArrayList;
    }

    /**
     * Determines how match data should be displayed on screen.
     */
    private ArrayList<Match> setMatch(JSONObject eventObj, ArrayList<Match> matchArrayList, String date) {
        try {
            String homeTeam = eventObj.getJSONObject("homeTeam").getString("name");
            String awayTeam = eventObj.getJSONObject("awayTeam").getString("name");

            if (!eventObj.getJSONObject("changes").has("changeDate")) {
                Match match = new Match("-", "-", homeTeam, awayTeam);
                matchArrayList.add(match);
            } else if (eventObj.getJSONObject("changes").getString("changeDate").contains(date)) {

                String homeScore = eventObj.getJSONObject("homeScore").getString("current");
                String awayScore = eventObj.getJSONObject("awayScore").getString("current");

                Match match = new Match(homeScore, awayScore, homeTeam, awayTeam);
                matchArrayList.add(match);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return matchArrayList;
    }

    /*
     * Converts the given playername to a Firebase-friendly name.
     */
    public void retrievePlayerInfo(View view) {

        TextView textView = (TextView) view;
        String playerName = textView.getHint().toString();
        playerName = playerName.replace(".","");

        loadPlayerInfo(DateActivity.this, playerName);
    }

    public void tournamentInfoClick(View view) {
        TextView tournamentName = (TextView) findViewById(R.id.tvTournamentName);
        existsTournamentInfo(tournamentName.getText().toString(), DateActivity.this);
    }
}