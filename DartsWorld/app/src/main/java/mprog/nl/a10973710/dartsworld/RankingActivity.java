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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "RankingActivity";
    ArrayList<String> PlayerList = new ArrayList<String>();
    ArrayList<String> PlayerKeyList = new ArrayList<String>();
    ArrayList<Integer> DifferenceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("PDC Order of Merit");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getPlayerInfo();
    }

    public void getPlayerInfo() {

        DatabaseReference playersDatabase = FirebaseDatabase.getInstance().getReference().child("players");

        playersDatabase.orderByChild("currPos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PlayerProperty> pList = new ArrayList<>();

                try {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Player post = postSnapshot.getValue(Player.class);

                        PlayerKeyList.add(postSnapshot.getKey());
                        PlayerList.add(String.valueOf(post.getFullName()));
                        DifferenceList.add(post.getCurrPos() - post.getPrevPos());
                        int difference = post.getCurrPos() - post.getPrevPos();
                        PlayerProperty player = new PlayerProperty(String.valueOf(difference), post.getFullName());
                        pList.add(player);
                    }

                    RankingListAdapter adapter = new RankingListAdapter(RankingActivity.this, R.layout.adapter_view_ranking, pList);
                    ListView rankingList = (ListView) findViewById(R.id.rankingList);
                    rankingList.setAdapter(adapter);


                } catch (Exception e) {
                    Log.e(TAG, "Exception e = " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.toString());
            }
        });
    }

//    private void setPlayerInfo(ArrayList<String> playerList, ArrayList<Integer> differenceList) {
//        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,
//                R.layout.adapter_view_ranking, playerList);
//
//        ListView playerListView = (ListView) findViewById(R.id.rankingListView);
//
//        playerListView.setAdapter(mArrayAdapter);
//    }

//    playersDatabase = (DatabaseReference) playersDatabase.orderByChild("currPos");
//        Log.d(TAG, "my databse: " + playersDatabase.orderByChild("currPos").toString());

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
        } else if (id == R.id.nav_ranking) {
            Intent intent = new Intent(this, RankingActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
