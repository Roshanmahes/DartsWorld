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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static mprog.nl.a10973710.dartsworld.Helper.navigateTo;

/**
 * Created by Roshan Mahes on 17-6-2017.
 */

public class RankingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "RankingActivity";
    ArrayList<String> PlayerList = new ArrayList<String>();
    ArrayList<String> playerKeyList = new ArrayList<String>();
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

        setListener(playerKeyList);
    }

    public void getPlayerInfo() {

        DatabaseReference playersDatabase = FirebaseDatabase.getInstance().getReference().child("players");

        playersDatabase.orderByChild("currPos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<KeyValuePair> pList = new ArrayList<>();

                try {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Player post = postSnapshot.getValue(Player.class);

                        playerKeyList.add(postSnapshot.getKey());
                        PlayerList.add(String.valueOf(post.getFullName()));
                        DifferenceList.add(post.getCurrPos() - post.getPrevPos());
                        int difference = post.getCurrPos() - post.getPrevPos();
                        KeyValuePair player = new KeyValuePair(String.valueOf(difference), post.getFullName());
                        pList.add(player);
                    }

                    RankingListAdapter adapter = new RankingListAdapter(RankingActivity.this, R.layout.adapter_view_ranking, pList);
                    ListView rankingList = (ListView) findViewById(R.id.rankingList);
                    rankingList.setAdapter(adapter);

                } catch (Exception ignored) {}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void setListener(final ArrayList<String> playerKeyList) {
        ListView rankingList = (ListView) findViewById(R.id.rankingList);
        rankingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String playerName =  playerKeyList.get(position);
                startPlayerActivity(playerName);
            }

        });
    }

    public void startPlayerActivity(String playerName) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("playerName", playerName);
        this.startActivity(intent);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigateTo(RankingActivity.this, id, drawer);
        return true;
    }
}
